package io.github.lq.fun.stuff.lang.model.types;

import io.github.lq.fun.stuff.reflect.AnnotatedClassTypeDecorator;
import jakarta.enterprise.lang.model.types.PrimitiveType;

import java.lang.reflect.AnnotatedType;
import java.util.Map;

public class PrimitiveTypeImpl extends TypeImpl implements PrimitiveType {
    private final Class<?> primitiveType;

    public PrimitiveTypeImpl(AnnotatedType annotatedType) {
        super(annotatedType);
        this.primitiveType = (Class<?>) annotatedType.getType();
    }

    @Override
    public String name() {
        return primitiveType.getTypeName();
    }

    @Override
    public PrimitiveKind primitiveKind() {
        return primitiveTypes.get(primitiveType);

    }

    static final Map<Class<?>, PrimitiveKind> primitiveTypes = Map.of(
            boolean.class, PrimitiveKind.BOOLEAN,
            byte.class, PrimitiveKind.BYTE,
            short.class, PrimitiveKind.SHORT,
            int.class, PrimitiveKind.INT,
            long.class, PrimitiveKind.LONG,
            float.class, PrimitiveKind.FLOAT,
            double.class, PrimitiveKind.DOUBLE,
            char.class, PrimitiveKind.CHAR
    );
}
