package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;

public class EnumConstructorParameterInfoImpl extends ParameterInfoImpl {


    private final AnnotatedType annotatedType;

    public EnumConstructorParameterInfoImpl(Parameter parameter, MethodInfoImpl methodInfo) {
        super(parameter, methodInfo);
        int posIndex = Integer.parseInt(parameter.getName().substring(3)) - 2;
        this.annotatedType = parameter.getDeclaringExecutable().getAnnotatedParameterTypes()[posIndex];
    }

    @Override
    public Type type() {
        var type = (TypeImpl) super.type();
        type.setAnnotatedTypeSupplier(() -> annotatedType);
        return type;
    }

}
