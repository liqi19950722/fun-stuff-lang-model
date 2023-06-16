package io.github.lq.fun.stuff.lang.model;

import io.github.lq.fun.stuff.lang.model.declarations.ClassInfoImpl;
import jakarta.enterprise.lang.model.AnnotationInfo;
import jakarta.enterprise.lang.model.AnnotationMember;
import jakarta.enterprise.lang.model.declarations.ClassInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationInfoImpl implements AnnotationInfo {
    private final Annotation annotation;

    public AnnotationInfoImpl(Annotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public ClassInfo declaration() {
        return new ClassInfoImpl(annotation.annotationType());
    }

    @Override
    public boolean hasMember(String name) {
        try {
            annotation.annotationType().getDeclaredMethod(name);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }

    }

    @Override
    public AnnotationMember member(String name) {
        Method member;
        try {
            member = annotation.annotationType().getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }
        Object value = getValue(member, annotation);
        return new AnnotationMemberImpl(value);
    }

    @Override
    public Map<String, AnnotationMember> members() {
        Method[] members = annotation.annotationType().getDeclaredMethods();
        Map<String, AnnotationMember> result = new HashMap<>();
        for (Method member : members) {
            Object value = getValue(member, annotation);
            result.put(member.getName(), new AnnotationMemberImpl(value));
        }
        return result;
    }

    private Object getValue(Method method, Object object) {
        method.setAccessible(true);
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
