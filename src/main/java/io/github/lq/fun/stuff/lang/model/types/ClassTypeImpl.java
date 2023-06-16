package io.github.lq.fun.stuff.lang.model.types;

import io.github.lq.fun.stuff.lang.model.declarations.ClassInfoImpl;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.types.ClassType;

import java.lang.reflect.AnnotatedType;

public class ClassTypeImpl extends TypeImpl implements ClassType {

    private final Class<?> clazz;

    public ClassTypeImpl(AnnotatedType annotatedType) {
        super(annotatedType);
        if (annotatedType.getType() instanceof Class<?> clazz) {
            this.clazz = clazz;
        } else {
            throw new IllegalArgumentException("AnnotatedType's Type is not a Class");
        }

    }

    @Override
    public ClassInfo declaration() {
        return new ClassInfoImpl(clazz);
    }

}
