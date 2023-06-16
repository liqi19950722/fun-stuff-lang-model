package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.ClassTypeImpl;
import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import io.github.lq.fun.stuff.lang.model.types.TypeVariableImpl;
import io.github.lq.fun.stuff.reflect.AnnotatedTypeVariableDecorator;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.declarations.MethodInfo;
import jakarta.enterprise.lang.model.declarations.ParameterInfo;
import jakarta.enterprise.lang.model.types.Type;
import jakarta.enterprise.lang.model.types.TypeVariable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MethodInfoImpl extends DeclarationInfoImpl implements MethodInfo {
    private final Executable executable;

    private final String className;
    private final String methodName;
    public MethodInfoImpl(Executable executable) {
        super(executable);
        this.executable = executable;
        this.className = executable.getDeclaringClass().getName();
        this.methodName = executable.getName();
    }

    @Override
    public String name() {
        if (isConstructor()) {
            return executable.getDeclaringClass().getName();
        }
        return executable.getName();
    }

    @Override
    public List<ParameterInfo> parameters() {
        var parameters = executable.getParameters();

        if (isEnumConstructor(parameters)) {
            Parameter[] processedParameters = Arrays.copyOfRange(parameters, 2, parameters.length);

            return Arrays.stream(processedParameters)
                    .filter(parameter -> !parameter.isSynthetic())
                    .map(parameter -> (ParameterInfo) new EnumConstructorParameterInfoImpl(parameter, this))
                    .toList();
        } else {
            return Arrays.stream(parameters)
                    .filter(parameter -> !parameter.isSynthetic())
                    .map(parameter -> (ParameterInfo) new ParameterInfoImpl(parameter, this))
                    .toList();
        }
    }

    private boolean isEnumConstructor(Parameter[] parameters) {
        return isConstructor()
                && executable.getDeclaringClass().isEnum()
                && executable.getGenericParameterTypes().length != parameters.length
                && parameters.length >= 2
                && parameters[0].getType().equals(String.class)
                && parameters[1].getType().equals(int.class);
    }

    @Override
    public Type returnType() {
        if (isConstructor()) {
            return new ClassTypeImpl(executable.getAnnotatedReturnType());
        }

        return TypeImpl.createType(executable.getAnnotatedReturnType());
    }

    @Override
    public Type receiverType() {
        var receiverType = executable.getAnnotatedReceiverType();
        if (receiverType == null) {
            return null;
        }
        return TypeImpl.createType(receiverType);
    }

    @Override
    public List<Type> throwsTypes() {
        return Arrays.stream(executable.getAnnotatedExceptionTypes())
                .map(TypeImpl::createType)
                .toList();
    }

    @Override
    public List<TypeVariable> typeParameters() {
        return Arrays.stream(executable.getTypeParameters())
                .map(AnnotatedTypeVariableDecorator::new)
                .map(annotatedTypeVariable -> (TypeVariable) new TypeVariableImpl(annotatedTypeVariable))
                .toList();
    }

    @Override
    public boolean isConstructor() {

        return executable instanceof Constructor;
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(modifiers());
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(modifiers());
    }

    @Override
    public int modifiers() {
        return executable.getModifiers();
    }

    @Override
    public ClassInfo declaringClass() {
        return new ClassInfoImpl(executable.getDeclaringClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodInfoImpl that = (MethodInfoImpl) o;
        return Objects.equals(className, that.className) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName);
    }
}
