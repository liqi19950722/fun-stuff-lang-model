package io.github.lq.fun.stuff.lang.model.declarations;

import io.github.lq.fun.stuff.lang.model.AnnotationTargetImpl;
import jakarta.enterprise.lang.model.declarations.DeclarationInfo;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;

public abstract class DeclarationInfoImpl extends AnnotationTargetImpl implements DeclarationInfo {

    public DeclarationInfoImpl(AnnotatedElement annotatedElement) {
        super(annotatedElement);
    }
}
