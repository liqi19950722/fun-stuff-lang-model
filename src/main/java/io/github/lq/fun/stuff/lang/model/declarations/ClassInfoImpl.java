package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import io.github.lq.fun.stuff.lang.model.types.TypeVariableImpl;
import io.github.lq.fun.stuff.reflect.AnnotatedTypeVariableDecorator;
import jakarta.enterprise.lang.model.declarations.ClassInfo;
import jakarta.enterprise.lang.model.declarations.FieldInfo;
import jakarta.enterprise.lang.model.declarations.MethodInfo;
import jakarta.enterprise.lang.model.declarations.PackageInfo;
import jakarta.enterprise.lang.model.declarations.RecordComponentInfo;
import jakarta.enterprise.lang.model.types.Type;
import jakarta.enterprise.lang.model.types.TypeVariable;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ClassInfoImpl extends DeclarationInfoImpl implements ClassInfo {

    private static final Predicate<Member> IS_NOT_SYNTHETIC = it -> !it.isSynthetic();
    Class<?> clazz;

    String name;

    public ClassInfoImpl(Class<?> clazz) {
        super(clazz);
        this.clazz = clazz;

        this.name = clazz.getName();
    }

    @Override
    public String name() {
        return clazz.getName();
    }

    @Override
    public String simpleName() {
        return clazz.getSimpleName();
    }

    @Override
    public PackageInfo packageInfo() {
        var pkg = clazz.getPackage();
        return pkg != null ? new PackageInfoImpl(pkg) : null;
    }

    @Override
    public List<TypeVariable> typeParameters() {
        return Arrays.stream(clazz.getTypeParameters())
                .<TypeVariable>map(classTypeVariable -> new TypeVariableImpl(new AnnotatedTypeVariableDecorator(classTypeVariable)))
                .toList();
    }

    @Override
    public Type superClass() {
        AnnotatedType superclass = clazz.getAnnotatedSuperclass();
        return superclass != null ? TypeImpl.createType(superclass) : null;
    }

    @Override
    public ClassInfo superClassDeclaration() {
        Class<?> superclass = clazz.getSuperclass();
        return superclass != null ? new ClassInfoImpl(superclass) : null;
    }

    @Override
    public List<Type> superInterfaces() {
        return Arrays.stream(clazz.getAnnotatedInterfaces())
                .map(annotatedType -> TypeImpl.createType(annotatedType)).toList();
    }

    @Override
    public List<ClassInfo> superInterfacesDeclarations() {
        return Arrays.stream(clazz.getInterfaces())
                .map(it -> (ClassInfo) new ClassInfoImpl(it)).toList();
    }

    @Override
    public boolean isPlainClass() {
        return !isInterface() && !isEnum() && !isAnnotation() && !isRecord();
    }

    @Override
    public boolean isInterface() {
        if (isAnnotation()) return false;
        return clazz.isInterface();
    }


    @Override
    public boolean isEnum() {
        return clazz.isEnum();
    }

    @Override
    public boolean isAnnotation() {
        return clazz.isAnnotation();
    }

    @Override
    public boolean isRecord() {
        return clazz.isRecord();
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
        return clazz.getModifiers();
    }

    @Override
    public Collection<MethodInfo> constructors() {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(IS_NOT_SYNTHETIC)
                .map(constructor -> (MethodInfo) new MethodInfoImpl(constructor)).toList();
    }

    @Override
    public Collection<MethodInfo> methods() {
        return getAllMethods().stream()
                .filter(IS_NOT_SYNTHETIC)
                .map(constructor -> (MethodInfo) new MethodInfoImpl(constructor)).toList();
    }

    private Set<Method> getAllMethods() {
        var methodCollection = new HashSet<Method>();
        doGetAllMembers(clazz, methodCollection, Class::getDeclaredMethods);
        return methodCollection;
    }


    @Override
    public Collection<FieldInfo> fields() {
        return getAllFields().stream()
                .filter(IS_NOT_SYNTHETIC)
                .map(field -> (FieldInfo) new FieldInfoImpl(field)).toList();
    }

    private Set<Field> getAllFields() {
        var fieldCollection = new HashSet<Field>();
        doGetAllMembers(clazz, fieldCollection, Class::getDeclaredFields);
        return fieldCollection;
    }

    private static <M extends Member> void doGetAllMembers(Class<?> clazz, Set<M> memberCollection, Function<Class<?>, M[]> function) {
        if (Objects.isNull(clazz) || Objects.equals(clazz, Object.class)) {
            return;
        }
        doGetAllMembers(clazz.getSuperclass(), memberCollection, function);
        Arrays.stream(clazz.getInterfaces()).forEach(superInterface -> doGetAllMembers(superInterface, memberCollection, function));
        memberCollection.addAll(Arrays.stream(function.apply(clazz)).toList());
    }

    @Override
    public Collection<RecordComponentInfo> recordComponents() {
        if (isRecord()) {
            return Arrays.stream(clazz.getRecordComponents())
                    .map(recordComponent -> (RecordComponentInfo) new RecordComponentInfoImpl(recordComponent))
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfoImpl classInfo = (ClassInfoImpl) o;
        return Objects.equals(name, classInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
