package org.springframework.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Label;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.asm.Type;
import org.springframework.util.ClassUtils;

public class LocalVariableTableParameterNameDiscoverer implements ParameterNameDiscoverer {

	private static Log logger = LogFactory.getLog(LocalVariableTableParameterNameDiscoverer.class);

	private static final Map<Member, String[]> NO_DEBUG_INFO_MAP = Collections.emptyMap();

	private final Map<Class<?>, Map<Member, String[]>> parameterNamesCache =
			new ConcurrentHashMap<Class<?>, Map<Member, String[]>>(32);

	/**
	 * 获取方法参数名集合
	 * @param method
	 * @return
	 */
	@Override
	public String[] getParameterNames(Method method) {
		Method originalMethod = BridgeMethodResolver.findBridgedMethod(method);
		Class<?> declaringClass = originalMethod.getDeclaringClass();
		Map<Member, String[]> map = this.parameterNamesCache.get(declaringClass);
		if (map == null) {
			map = inspectClass(declaringClass);
			this.parameterNamesCache.put(declaringClass, map);
		}
		if (map != NO_DEBUG_INFO_MAP) {
			return map.get(originalMethod);
		}
		return null;
	}

	@Override
	public String[] getParameterNames(Constructor<?> ctor) {
		Class<?> declaringClass = ctor.getDeclaringClass();
		Map<Member, String[]> map = this.parameterNamesCache.get(declaringClass);
		if (map == null) {
			map = inspectClass(declaringClass);
			this.parameterNamesCache.put(declaringClass, map);
		}
		if (map != NO_DEBUG_INFO_MAP) {
			return map.get(ctor);
		}
		return null;
	}

	private Map<Member, String[]> inspectClass(Class<?> clazz) {
		InputStream is = clazz.getResourceAsStream(ClassUtils.getClassFileName(clazz));
		if (is == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Cannot find '.class' file for class [" + clazz
						+ "] - unable to determine constructors/methods parameter names");
			}
			return NO_DEBUG_INFO_MAP;
		}
		try {
			ClassReader classReader = new ClassReader(is);
			Map<Member, String[]> map = new ConcurrentHashMap<Member, String[]>(32);
			classReader.accept(new ParameterNameDiscoveringVisitor(clazz, map), 0);
			return map;
		}
		catch (IOException ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("Exception thrown while reading '.class' file for class [" + clazz +
						"] - unable to determine constructors/methods parameter names", ex);
			}
		}
		catch (IllegalArgumentException ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("ASM ClassReader failed to parse class file [" + clazz +
						"], probably due to a new Java class file version that isn't supported yet " +
						"- unable to determine constructors/methods parameter names", ex);
			}
		}
		finally {
			try {
				is.close();
			}
			catch (IOException ex) {
				// ignore
			}
		}
		return NO_DEBUG_INFO_MAP;
	}

	private static class ParameterNameDiscoveringVisitor extends ClassVisitor {

		private static final String STATIC_CLASS_INIT = "<clinit>";

		private final Class<?> clazz;
		private final Map<Member, String[]> memberMap;

		public ParameterNameDiscoveringVisitor(Class<?> clazz, Map<Member, String[]> memberMap) {
			super(SpringAsmInfo.ASM_VERSION);
			this.clazz = clazz;
			this.memberMap = memberMap;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (!isSyntheticOrBridged(access) && !STATIC_CLASS_INIT.equals(name)) {
				return new LocalVariableTableVisitor(clazz, memberMap, name, desc, isStatic(access));
			}
			return null;
		}

		private static boolean isSyntheticOrBridged(int access) {
			return (((access & Opcodes.ACC_SYNTHETIC) | (access & Opcodes.ACC_BRIDGE)) > 0);
		}

		private static boolean isStatic(int access) {
			return ((access & Opcodes.ACC_STATIC) > 0);
		}
	}


	private static class LocalVariableTableVisitor extends MethodVisitor {

		private static final String CONSTRUCTOR = "<init>";

		private final Class<?> clazz;
		private final Map<Member, String[]> memberMap;
		private final String name;
		private final Type[] args;
		private final boolean isStatic;

		private String[] parameterNames;
		private boolean hasLvtInfo = false;

		private final int[] lvtSlotIndex;

		public LocalVariableTableVisitor(Class<?> clazz, Map<Member, String[]> map, String name, String desc,
				boolean isStatic) {
			super(SpringAsmInfo.ASM_VERSION);
			this.clazz = clazz;
			this.memberMap = map;
			this.name = name;
			// determine args
			args = Type.getArgumentTypes(desc);
			this.parameterNames = new String[args.length];
			this.isStatic = isStatic;
			this.lvtSlotIndex = computeLvtSlotIndices(isStatic, args);
		}

		@Override
		public void visitLocalVariable(String name, String description, String signature, Label start, Label end,
				int index) {
			this.hasLvtInfo = true;
			for (int i = 0; i < lvtSlotIndex.length; i++) {
				if (lvtSlotIndex[i] == index) {
					this.parameterNames[i] = name;
				}
			}
		}

		@Override
		public void visitEnd() {
			if (this.hasLvtInfo || (this.isStatic && this.parameterNames.length == 0)) {
				memberMap.put(resolveMember(), parameterNames);
			}
		}

		private Member resolveMember() {
			ClassLoader loader = clazz.getClassLoader();
			Class<?>[] classes = new Class<?>[args.length];

			// resolve args
			for (int i = 0; i < args.length; i++) {
				classes[i] = ClassUtils.resolveClassName(args[i].getClassName(), loader);
			}
			try {
				if (CONSTRUCTOR.equals(name)) {
					return clazz.getDeclaredConstructor(classes);
				}

				return clazz.getDeclaredMethod(name, classes);
			} catch (NoSuchMethodException ex) {
				throw new IllegalStateException("Method [" + name
						+ "] was discovered in the .class file but cannot be resolved in the class object", ex);
			}
		}

		private static int[] computeLvtSlotIndices(boolean isStatic, Type[] paramTypes) {
			int[] lvtIndex = new int[paramTypes.length];
			int nextIndex = (isStatic ? 0 : 1);
			for (int i = 0; i < paramTypes.length; i++) {
				lvtIndex[i] = nextIndex;
				if (isWideType(paramTypes[i])) {
					nextIndex += 2;
				} else {
					nextIndex++;
				}
			}
			return lvtIndex;
		}

		private static boolean isWideType(Type aType) {
			// float is not a wide type
			return (aType == Type.LONG_TYPE || aType == Type.DOUBLE_TYPE);
		}
	}

}
