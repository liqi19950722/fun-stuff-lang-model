package io.github.lq.fun.stuff.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public class AnnotatedClassTypeDecorator implements AnnotatedType {
    private final Class<?> clazz;

    public AnnotatedClassTypeDecorator(Class<?> primitiveClass) {
        this.clazz = primitiveClass;
    }

    @Override
    public Type getType() {
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
