package io.github.lq.fun.stuff.lang.model.types;

import io.github.lq.fun.stuff.lang.model.AnnotationTargetImpl;
import jakarta.enterprise.lang.model.types.Type;

import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.AnnotatedWildcardType;
import java.util.Arrays;
import java.util.Objects;

public abstract class TypeImpl extends AnnotationTargetImpl implements Type {
    TypeImpl(AnnotatedType annotatedType) {
        super(annotatedType);
    }

    public static Type createType(AnnotatedType annotatedType) {
        if (annotatedType instanceof AnnotatedParameterizedType annotatedParameterizedType) {
            return new ParameterizedTypeImpl(annotatedParameterizedType);
        }
        if (annotatedType instanceof AnnotatedTypeVariable annotatedTypeVariable) {
            return new TypeVariableImpl(annotatedTypeVariable);
        }
        if (annotatedType instanceof AnnotatedArrayType annotatedArrayType) {
            return new ArrayTypeImpl(annotatedArrayType);
        }
        if (annotatedType instanceof AnnotatedWildcardType annotatedWildcardType) {
            return new WildcardTypeImpl(annotatedWildcardType);
        }
        if (annotatedType.getType() instanceof Class<?> clazz) {
            if (clazz.isPrimitive()) {
                if (clazz == void.class) {
                    return new VoidTypeImpl();
                } else {
                    return new PrimitiveTypeImpl(annotatedType);
                }
            }
            // FIXME : should support array
//            if (clazz.isArray()) {
//                return new ArrayTypeImpl((java.lang.reflect.AnnotatedArrayType) AnnotatedTypes.from(clazz));
//            }
            return new ClassTypeImpl(annotatedType);
        }
        throw new IllegalArgumentException("Unknown annotated type ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeImpl)) {
            return false;
        }
        TypeImpl type = (TypeImpl) o;
        return Objects.equals(((AnnotatedType) annotatedElement).getType(), ((AnnotatedType) type.annotatedElement).getType())
                && Objects.deepEquals(annotatedElement.getAnnotations(), type.annotatedElement.getAnnotations());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(((AnnotatedType) annotatedElement).getType());
        result = 31 * result + Arrays.hashCode(annotatedElement.getAnnotations());
        return result;
    }
}
