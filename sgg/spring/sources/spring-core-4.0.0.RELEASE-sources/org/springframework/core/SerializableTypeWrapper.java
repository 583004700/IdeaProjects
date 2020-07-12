package org.springframework.core;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;

abstract class SerializableTypeWrapper {

	private static final Class<?>[] SUPPORTED_SERIALAZABLE_TYPES = { GenericArrayType.class,
		ParameterizedType.class, TypeVariable.class, WildcardType.class };

	/**
	 * 返回field.getGenericType() 的代理对象
	 * @param field
	 * @return
	 */
	public static Type forField(Field field) {
		Assert.notNull(field, "Field must not be null");
		return forTypeProvider(new FieldTypeProvider(field));
	}

	/**
	 * 得到方法参数的methodParameter.getGenericParameterType() 代理对象
	 * @param methodParameter
	 * @return
	 */
	public static Type forMethodParameter(MethodParameter methodParameter) {
		return forTypeProvider(new MethodParameterTypeProvider(methodParameter));
	}

	/**
	 * 返回type类型的GenericSuperclass
	 * @param type
	 * @return
	 */
	public static Type forGenericSuperclass(final Class<?> type) {
		return forTypeProvider(new DefaultTypeProvider() {

			private static final long serialVersionUID = 1L;

			@Override
			public Type getType() {
				return type.getGenericSuperclass();
			}
		});
	}

	/**
	 * 返回type类型的 GenericInterfaces
	 * @param type
	 * @return
	 */
	public static Type[] forGenericInterfaces(final Class<?> type) {
		Type[] result = new Type[type.getGenericInterfaces().length];
		for (int i = 0; i < result.length; i++) {
			final int index = i;
			result[i] = forTypeProvider(new DefaultTypeProvider() {

				private static final long serialVersionUID = 1L;


				@Override
				public Type getType() {
					return type.getGenericInterfaces()[index];
				}
			});
		}
		return result;
	}

	/**
	 * 获取type类型的TypeParameters
	 * @param type
	 * @return
	 */
	public static Type[] forTypeParameters(final Class<?> type) {
		Type[] result = new Type[type.getTypeParameters().length];
		for (int i = 0; i < result.length; i++) {
			final int index = i;
			result[i] = forTypeProvider(new DefaultTypeProvider() {

				private static final long serialVersionUID = 1L;


				@Override
				public Type getType() {
					return type.getTypeParameters()[index];
				}
			});
		}
		return result;
	}

	static Type forTypeProvider(final TypeProvider provider) {
		Assert.notNull(provider, "Provider must not be null");
		if (provider.getType() instanceof Serializable || provider.getType() == null) {
			return provider.getType();
		}
		for (Class<?> type : SUPPORTED_SERIALAZABLE_TYPES) {
			if (type.isAssignableFrom(provider.getType().getClass())) {
				ClassLoader classLoader = provider.getClass().getClassLoader();
				Class<?>[] interfaces = new Class<?>[] { type, Serializable.class };
				InvocationHandler handler = new TypeProxyInvocationHandler(provider);
				return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
			}
		}
		throw new IllegalArgumentException("Unsupported Type class "
				+ provider.getType().getClass().getName());
	}

	static interface TypeProvider extends Serializable {

		Type getType();

		Object getSource();

	}

	static abstract class DefaultTypeProvider implements TypeProvider {

		private static final long serialVersionUID = 1L;

		@Override
		public Object getSource() {
			return null;
		}

	}

	private static class TypeProxyInvocationHandler implements InvocationHandler,
			Serializable {

		private static final long serialVersionUID = 1L;

		private final TypeProvider provider;

		/**
		 *
		 * @param provider 比如 FieldTypeProvider 等
		 */
		public TypeProxyInvocationHandler(TypeProvider provider) {
			this.provider = provider;
		}

		/**
		 * 执行 如ParameterizedType 接口中的方法时，会执行这个方法 比如 getRawType
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (Type.class.equals(method.getReturnType()) && args == null) {
				return forTypeProvider(new MethodInvokeTypeProvider(this.provider, method, -1));
			}
			if (Type[].class.equals(method.getReturnType()) && args == null) {
				Type[] result = new Type[((Type[]) method.invoke(this.provider.getType(), args)).length];
				for (int i = 0; i < result.length; i++) {
					result[i] = forTypeProvider(new MethodInvokeTypeProvider(this.provider, method, i));
				}
				return result;
			}
			return method.invoke(this.provider.getType(), args);
		}
	}

	static class FieldTypeProvider implements TypeProvider {

		private static final long serialVersionUID = 1L;

		private final String fieldName;

		private final Class<?> declaringClass;

		private transient Field field;

		public FieldTypeProvider(Field field) {
			this.fieldName = field.getName();
			this.declaringClass = field.getDeclaringClass();
			this.field = field;
		}
		/**
		 * 获取字段的 GenericType
		 * @return
		 */
		@Override
		public Type getType() {
			return this.field.getGenericType();
		}

		@Override
		public Object getSource() {
			return this.field;
		}

		private void readObject(ObjectInputStream inputStream) throws IOException,
				ClassNotFoundException {
			inputStream.defaultReadObject();
			try {
				this.field = this.declaringClass.getDeclaredField(this.fieldName);
			}
			catch (Throwable ex) {
				throw new IllegalStateException(
						"Could not find original class structure", ex);
			}
		}

	}

	static class MethodParameterTypeProvider implements TypeProvider {

		private static final long serialVersionUID = 1L;

		private final String methodName;

		private final Class<?>[] parameterTypes;

		private final Class<?> declaringClass;

		private final int parameterIndex;

		private transient MethodParameter methodParameter;

		public MethodParameterTypeProvider(MethodParameter methodParameter) {
			if (methodParameter.getMethod() != null) {
				this.methodName = methodParameter.getMethod().getName();
				this.parameterTypes = methodParameter.getMethod().getParameterTypes();
			}
			else {
				this.methodName = null;
				this.parameterTypes = methodParameter.getConstructor().getParameterTypes();
			}
			this.declaringClass = methodParameter.getDeclaringClass();
			this.parameterIndex = methodParameter.getParameterIndex();
			this.methodParameter = methodParameter;
		}

		/**
		 * 获取方法参数的GenericParameterType
		 * @return
		 */
		@Override
		public Type getType() {
			return this.methodParameter.getGenericParameterType();
		}

		@Override
		public Object getSource() {
			return this.methodParameter;
		}

		private void readObject(ObjectInputStream inputStream) throws IOException,
				ClassNotFoundException {
			inputStream.defaultReadObject();
			try {
				if (this.methodName != null) {
					this.methodParameter = new MethodParameter(
							this.declaringClass.getDeclaredMethod(this.methodName,
									this.parameterTypes), this.parameterIndex);
				}
				else {
					this.methodParameter = new MethodParameter(
							this.declaringClass.getDeclaredConstructor(this.parameterTypes),
							this.parameterIndex);
				}
			}
			catch (Throwable ex) {
				throw new IllegalStateException(
						"Could not find original class structure", ex);
			}
		}

	}

	static class MethodInvokeTypeProvider implements TypeProvider {

		private static final long serialVersionUID = 1L;

		private final TypeProvider provider;

		private final String methodName;

		private final int index;
		//method 在 provider.getType()上的执行结果
		private transient Object result;

		/**
		 *
		 * @param provider	比如 FieldTypeProvider 等
		 * @param method	比如 ParameterizedType.getRawType()
		 * @param index		下标
		 */
		public MethodInvokeTypeProvider(TypeProvider provider, Method method, int index) {
			this.provider = provider;
			this.methodName = method.getName();
			this.index = index;
			this.result = ReflectionUtils.invokeMethod(method, provider.getType());
		}

		/**
		 * 如果result 是type类型，则返回result,否则返回result[index]
		 * @return
		 */
		@Override
		public Type getType() {
			if (this.result instanceof Type || this.result == null) {
				return (Type) this.result;
			}
			return ((Type[])this.result)[this.index];
		}

		@Override
		public Object getSource() {
			return null;
		}

		private void readObject(ObjectInputStream inputStream) throws IOException,
				ClassNotFoundException {
			inputStream.defaultReadObject();
			Method method = ReflectionUtils.findMethod(
					this.provider.getType().getClass(), this.methodName);
			this.result = ReflectionUtils.invokeMethod(method, this.provider.getType());
		}
	}
}
