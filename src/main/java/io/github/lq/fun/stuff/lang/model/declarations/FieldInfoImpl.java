package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.declarations.FieldInfo;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class FieldInfoImpl extends DeclarationInfoImpl implements FieldInfo {
    private final Field field;

    private final String fieldName;
    private final String className;
    public FieldInfoImpl(Field field) {
        super(field);

        this.field = field;
        this.className = field.getDeclaringClass().getName();
        this.fieldName = field.getName();
    }

    @Override
    public String name() {
        return field.getName();
    }

    @Override
    public Type type() {
        return TypeImpl.createType(field.getAnnotatedType());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(modifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(modifiers());
    }

    @Override
    public int modifiers() {
        return field.getModifiers();
    }

    @Override
    public ClassInfo declaringClass() {
        return new ClassInfoImpl(field.getDeclaringClass());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldInfoImpl fieldInfo = (FieldInfoImpl) o;
        return Objects.equals(fieldName, fieldInfo.fieldName) && Objects.equals(className, fieldInfo.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, className);
    }
}
