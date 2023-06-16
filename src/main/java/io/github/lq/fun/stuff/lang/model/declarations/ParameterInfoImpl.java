package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import jakarta.enterprise.lang.model.declarations.MethodInfo;
import jakarta.enterprise.lang.model.declarations.ParameterInfo;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.Parameter;
import java.util.Objects;

public class ParameterInfoImpl extends DeclarationInfoImpl implements ParameterInfo {
    private final Parameter parameter;


    private final MethodInfoImpl methodInfo;
    private final String parameterName;
    public ParameterInfoImpl(Parameter parameter, MethodInfoImpl methodInfo) {
        super(parameter);

        this.parameter = parameter;

        this.methodInfo = methodInfo;
        this.parameterName = parameter.getName();

    }

    @Override
    public String name() {
        return parameter.getName();
    }

    @Override
    public Type type() {
        return TypeImpl.createType(parameter.getAnnotatedType());
    }

    @Override
    public MethodInfo declaringMethod() {
        return new MethodInfoImpl(parameter.getDeclaringExecutable());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterInfoImpl that = (ParameterInfoImpl) o;
        return Objects.equals(methodInfo, that.methodInfo) && Objects.equals(parameterName, that.parameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodInfo, parameterName);
    }
}
