package io.github.lq.fun.stuff.lang.model;

import jakarta.enterprise.lang.model.AnnotationInfo;
import jakarta.enterprise.lang.model.AnnotationTarget;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AnnotationTargetImpl implements AnnotationTarget {

    protected final AnnotatedElement annotatedElement;

    public Supplier<AnnotatedType> getAnnotatedTypeSupplier() {
        return annotatedTypeSupplier;
    }

    public void setAnnotatedTypeSupplier(Supplier<AnnotatedType> annotatedTypeSupplier) {
        this.annotatedTypeSupplier = annotatedTypeSupplier;
    }

    private Supplier<AnnotatedType> annotatedTypeSupplier;

    protected AnnotationTargetImpl(AnnotatedElement annotatedElement) {
        this.annotatedElement = annotatedElement;
    }

    AnnotatedElement annotatedType() {
        return annotatedTypeSupplier != null ? annotatedTypeSupplier.get() : annotatedElement;
    }

    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return annotatedType().isAnnotationPresent(annotationType);
    }

    @Override
    public boolean hasAnnotation(Predicate<AnnotationInfo> predicate) {
        return Arrays.stream(annotatedType().getAnnotations())
                .anyMatch(it -> predicate.test(new AnnotationInfoImpl(it)));
    }

    @Override
    public <T extends Annotation> AnnotationInfo annotation(Class<T> annotationType) {
        var annotation = annotatedType().getAnnotation(annotationType);
        if (annotation == null) {
            return null;
        }
        return new AnnotationInfoImpl(annotation);
    }

    @Override
    public <T extends Annotation> Collection<AnnotationInfo> repeatableAnnotation(Class<T> annotationType) {

        return Arrays.stream(annotatedType().getAnnotationsByType(annotationType))
                .map(AnnotationInfoImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<AnnotationInfo> annotations(Predicate<AnnotationInfo> predicate) {
        return Arrays.stream(annotatedType().getAnnotations())
                .map(AnnotationInfoImpl::new)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<AnnotationInfo> annotations() {
        return Arrays.stream(annotatedType().getAnnotations())
                .map(AnnotationInfoImpl::new)
                .collect(Collectors.toList());
    }
}
