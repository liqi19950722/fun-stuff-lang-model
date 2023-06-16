package io.github.lq.fun.stuff.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class AnnotatedTypeVariableDecorator implements AnnotatedTypeVariable {

    private final TypeVariable<?> typeVariable;

    public AnnotatedTypeVariableDecorator(TypeVariable<?> typeVariable) {
        this.typeVariable = typeVariable;
    }
    @Override
    public AnnotatedType[] getAnnotatedBounds() {
        return typeVariable.getAnnotatedBounds();
    }

    @Override
    public AnnotatedType getAnnotatedOwnerType() {
        return null;
    }

    @Override
    public Type getType() {
        return typeVariable;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return typeVariable.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return typeVariable.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return typeVariable.getDeclaredAnnotations();
    }
}
