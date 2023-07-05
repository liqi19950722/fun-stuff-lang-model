package io.github.lq.fun.stuff.lang.model.types;

import jakarta.enterprise.lang.model.types.Type;
import jakarta.enterprise.lang.model.types.WildcardType;

import java.lang.reflect.AnnotatedWildcardType;

public class WildcardTypeImpl extends TypeImpl implements WildcardType {
    private final AnnotatedWildcardType annotatedWildcardType;

    private final boolean hasUpperBounds;
    public WildcardTypeImpl(AnnotatedWildcardType annotatedWildcardType) {
        super(annotatedWildcardType);
        this.annotatedWildcardType = annotatedWildcardType;
        this.hasUpperBounds = (annotatedWildcardType.getAnnotatedLowerBounds().length == 0);
    }

    @Override
    public Type upperBound() {
        if (!hasUpperBounds) {
            return null;
        }

        return createType(annotatedWildcardType.getAnnotatedUpperBounds()[0]);
    }

    @Override
    public Type lowerBound() {
        if (hasUpperBounds) {
            return null;
        }
        return createType(annotatedWildcardType.getAnnotatedLowerBounds()[0]);
    }

}
