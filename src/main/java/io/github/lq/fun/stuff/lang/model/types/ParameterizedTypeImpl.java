package io.github.lq.fun.stuff.lang.model.types;

import io.github.lq.fun.stuff.reflect.AnnotatedClassTypeDecorator;
import jakarta.enterprise.lang.model.types.ClassType;
import jakarta.enterprise.lang.model.types.ParameterizedType;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.AnnotatedParameterizedType;
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
        return new ClassTypeImpl(new AnnotatedClassTypeDecorator((Class<?>)parameterizedType.getRawType()));
    }

    @Override
    public List<Type> typeArguments() {
        return Arrays.stream(annotatedParameterizedType.getAnnotatedActualTypeArguments())
                .map(annotatedType ->  createType(annotatedType))
                .toList();
    }

}
