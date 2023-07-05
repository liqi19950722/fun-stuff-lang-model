package io.github.lq.fun.stuff.lang.model.types;

import jakarta.enterprise.lang.model.types.ArrayType;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;

public class ArrayTypeImpl extends TypeImpl implements ArrayType {

    private final AnnotatedType componentType;

    public ArrayTypeImpl(AnnotatedArrayType annotatedArrayType) {
        super(annotatedArrayType);

        componentType = annotatedArrayType.getAnnotatedGenericComponentType();
    }

    @Override
    public Type componentType() {
        return createType(componentType);
    }

}
