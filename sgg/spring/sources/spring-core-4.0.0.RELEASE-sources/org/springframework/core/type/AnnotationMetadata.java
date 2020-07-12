package org.springframework.core.type;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {
	Set<String> getAnnotationTypes();

	Set<String> getMetaAnnotationTypes(String annotationType);

	boolean hasAnnotation(String annotationType);

	boolean hasMetaAnnotation(String metaAnnotationType);

	boolean hasAnnotatedMethods(String annotationType);

	Set<MethodMetadata> getAnnotatedMethods(String annotationType);
}
