package org.springframework.core.type.classreading;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Type;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

	protected final ClassLoader classLoader;

	protected final Set<String> annotationSet = new LinkedHashSet<String>();

	protected final Map<String, Set<String>> metaAnnotationMap = new LinkedHashMap<String, Set<String>>(4);

	protected final MultiValueMap<String, AnnotationAttributes> attributeMap = new LinkedMultiValueMap<String, AnnotationAttributes>(4);

	protected final MultiValueMap<String, MethodMetadata> methodMetadataMap = new LinkedMultiValueMap<String, MethodMetadata>();

	public AnnotationMetadataReadingVisitor(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		return new MethodMetadataReadingVisitor(name, access, this.getClassName(), this.classLoader, this.methodMetadataMap);
	}

	@Override
	public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
		String className = Type.getType(desc).getClassName();
		this.annotationSet.add(className);
		return new AnnotationAttributesReadingVisitor(className, this.attributeMap, this.metaAnnotationMap, this.classLoader);
	}

	@Override
	public Set<String> getAnnotationTypes() {
		return this.annotationSet;
	}

	@Override
	public Set<String> getMetaAnnotationTypes(String annotationType) {
		return this.metaAnnotationMap.get(annotationType);
	}

	/**
	 * 判断是否存在annotationType注解
	 * @param annotationType
	 * @return
	 */
	@Override
	public boolean hasAnnotation(String annotationType) {
		return this.annotationSet.contains(annotationType);
	}

	/**
	 * 判断注解上存在metaAnnotationType注解		比如 Controller 上面存在 Component 则返回true
	 * @param metaAnnotationType
	 * @return
	 */
	@Override
	public boolean hasMetaAnnotation(String metaAnnotationType) {
		Collection<Set<String>> allMetaTypes = this.metaAnnotationMap.values();
		for (Set<String> metaTypes : allMetaTypes) {
			if (metaTypes.contains(metaAnnotationType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAnnotated(String annotationType) {
		return this.attributeMap.containsKey(annotationType);
	}

	@Override
	public AnnotationAttributes getAnnotationAttributes(String annotationType) {
		return getAnnotationAttributes(annotationType, false);
	}

	@Override
	public AnnotationAttributes getAnnotationAttributes(String annotationType, boolean classValuesAsString) {
		List<AnnotationAttributes> attributes = this.attributeMap.get(annotationType);
		AnnotationAttributes raw = (attributes == null ? null : attributes.get(0));
		return AnnotationReadingVisitorUtils.convertClassValues(this.classLoader, raw, classValuesAsString);
	}

	@Override
	public MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationType) {
		return getAllAnnotationAttributes(annotationType, false);
	}

	@Override
	public MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationType, boolean classValuesAsString) {
		MultiValueMap<String, Object> allAttributes = new LinkedMultiValueMap<String, Object>();
		List<AnnotationAttributes> attributes = this.attributeMap.get(annotationType);
		if (attributes == null) {
			return null;
		}
		for (AnnotationAttributes raw : attributes) {
			for (Map.Entry<String, Object> entry :
					AnnotationReadingVisitorUtils.convertClassValues(this.classLoader, raw, classValuesAsString).entrySet()) {
				allAttributes.add(entry.getKey(), entry.getValue());
			}
		}
		return allAttributes;
	}

	@Override
	public boolean hasAnnotatedMethods(String annotationType) {
		return this.methodMetadataMap.containsKey(annotationType);
	}

	@Override
	public Set<MethodMetadata> getAnnotatedMethods(String annotationType) {
		List<MethodMetadata> list = this.methodMetadataMap.get(annotationType);
		if (CollectionUtils.isEmpty(list)) {
			return new LinkedHashSet<MethodMetadata>(0);
		}
		Set<MethodMetadata> annotatedMethods = new LinkedHashSet<MethodMetadata>(list.size());
		annotatedMethods.addAll(list);
		return annotatedMethods;
	}

}
