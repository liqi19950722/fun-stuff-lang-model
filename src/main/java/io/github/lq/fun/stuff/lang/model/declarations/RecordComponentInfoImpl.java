package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.declarations.FieldInfo;
import jakarta.enterprise.lang.model.declarations.MethodInfo;
import jakarta.enterprise.lang.model.declarations.RecordComponentInfo;

import java.lang.reflect.RecordComponent;
import java.util.Objects;

public class RecordComponentInfoImpl extends DeclarationInfoImpl implements RecordComponentInfo {
    private final RecordComponent recordComponent;
    private final String name;

    public RecordComponentInfoImpl(RecordComponent recordComponent) {
        super(recordComponent);

        this.recordComponent = recordComponent;
        this.name = recordComponent.getName();
    }

    @Override
    public String name() {
        return recordComponent.getName();
    }

    @Override
    public jakarta.enterprise.lang.model.types.Type type() {
        return TypeImpl.createType(recordComponent.getAnnotatedType());
    }

    @Override
    public FieldInfo field() {
        return new RecordComponentFieldInfoImpl(recordComponent);
    }

    @Override
    public MethodInfo accessor() {
        return new MethodInfoImpl(recordComponent.getAccessor());
    }

    @Override
    public ClassInfo declaringRecord() {
        return new ClassInfoImpl(recordComponent.getDeclaringRecord());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordComponentInfoImpl that = (RecordComponentInfoImpl) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
