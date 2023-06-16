package io.github.lq.fun.stuff.lang.model.types;

import jakarta.enterprise.lang.model.types.ClassType;
import jakarta.enterprise.lang.model.types.ParameterizedType;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.List;

public class ParameterizedTypeImpl extends TypeImpl implements ParameterizedType {
    private final java.lang.reflect.ParameterizedType parameterizedType;
    private final AnnotatedParameterizedType annotatedParameterizedType;

    public ParameterizedTypeImpl(AnnotatedParameterizedType annotatedParameterizedType) {
        super(annotatedParameterizedType);
        if (annotatedParameterizedType.getType() instanceof java.lang.reflect.ParameterizedType parameterizedType) {
            this.parameterizedType = parameterizedType;
            this.annotatedParameterizedType = annotatedParameterizedType;
        } else {
            throw new IllegalArgumentException("annotatedParameterizedType's type is not ParameterizedType");
        }

    }

    @Override
    public ClassType genericClass() {
        return new ClassTypeImpl(new AnnotatedClassTypeDecorator(parameterizedType));
    }

    @Override
    public List<Type> typeArguments() {
        return Arrays.stream(annotatedParameterizedType.getAnnotatedActualTypeArguments())
                .map(annotatedType ->  createType(annotatedType))
                .toList();
    }

    // FIXME: should move
    private static class AnnotatedClassTypeDecorator implements AnnotatedType {
        private final Class<?> clazz;

        public AnnotatedClassTypeDecorator(java.lang.reflect.ParameterizedType parameterizedType) {
            this.clazz = (Class<?>) parameterizedType.getRawType();
        }

        @Override
        public java.lang.reflect.Type getType() {
            return clazz;
        }

        @Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            return clazz.getAnnotation(annotationClass);
        }

        @Override
        public Annotation[] getAnnotations() {
            return clazz.getAnnotations();
        }

        @Override
        public Annotation[] getDeclaredAnnotations() {
            return clazz.getDeclaredAnnotations();
        }
    }
}
