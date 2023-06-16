package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.declarations.FieldInfo;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Objects;

public class RecordComponentFieldInfoImpl extends DeclarationInfoImpl implements FieldInfo {
    private final RecordComponent recordComponent;
    private final String name;

    public RecordComponentFieldInfoImpl(RecordComponent recordComponent) {
        super(recordComponent);
        this.recordComponent = recordComponent;
        this.name = recordComponent.getName();
    }

    @Override
    public String name() {
        return recordComponent.getName();
    }

    @Override
    public Type type() {
        return TypeImpl.createType(recordComponent.getAnnotatedType());
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
        return Modifier.FINAL | Modifier.PRIVATE;
    }

    @Override
    public ClassInfo declaringClass() {
        return new ClassInfoImpl(recordComponent.getDeclaringRecord());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordComponentFieldInfoImpl that = (RecordComponentFieldInfoImpl) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
