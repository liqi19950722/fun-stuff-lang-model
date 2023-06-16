package io.github.lq.fun.stuff.lang.model.types;

import jakarta.enterprise.lang.model.types.Type;
import jakarta.enterprise.lang.model.types.TypeVariable;

import java.lang.reflect.AnnotatedTypeVariable;
import java.util.Arrays;
import java.util.List;

public class TypeVariableImpl extends TypeImpl implements TypeVariable {
    private final AnnotatedTypeVariable annotatedTypeVariable;

    public TypeVariableImpl(AnnotatedTypeVariable annotatedTypeVariable) {
        super(annotatedTypeVariable);

        this.annotatedTypeVariable = annotatedTypeVariable;
    }

    @Override
    public String name() {
        return annotatedTypeVariable.getType().getTypeName();
    }

    @Override
    public List<Type> bounds() {
        return Arrays.stream(annotatedTypeVariable.getAnnotatedBounds())
                .map(TypeImpl::createType)
                .toList();
    }

    @Override
    public boolean isTypeVariable() {
        return super.isTypeVariable();
    }
}
