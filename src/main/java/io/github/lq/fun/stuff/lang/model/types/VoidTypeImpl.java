package io.github.lq.fun.stuff.lang.model.types;

import io.github.lq.fun.stuff.reflect.AnnotatedClassTypeDecorator;
import jakarta.enterprise.lang.model.types.VoidType;

public class VoidTypeImpl extends TypeImpl implements VoidType {
    private final Class<Void> clazz;

    public VoidTypeImpl() {
        super(new AnnotatedClassTypeDecorator(void.class));
        this.clazz = void.class;
    }

    @Override
    public String name() {
        return clazz.getTypeName();
    }

}
