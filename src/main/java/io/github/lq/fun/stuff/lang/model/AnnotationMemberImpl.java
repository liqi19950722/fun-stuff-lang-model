package io.github.lq.fun.stuff.lang.model;

import io.github.lq.fun.stuff.lang.model.declarations.ClassInfoImpl;
import io.github.lq.fun.stuff.lang.model.types.TypeImpl;
import io.github.lq.fun.stuff.reflect.AnnotatedClassTypeDecorator;
import jakarta.enterprise.lang.model.AnnotationInfo;
import jakarta.enterprise.lang.model.AnnotationMember;
import jakarta.enterprise.lang.model.declarations.ClassInfo;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static io.github.lq.fun.stuff.util.Primitives.ofSpliterator;
import static jakarta.enterprise.lang.model.AnnotationMember.Kind.ARRAY;


public class AnnotationMemberImpl implements AnnotationMember {
    final Kind kind;
    final Object value;

    public AnnotationMemberImpl(Object value) {
        this.value = value;
        this.kind = determineKind(value);
    }

    private static Kind determineKind(Object value) {
        return KIND_TABLE.stream()
                .filter(it -> it.predicate().test(value))
                .map(KindTableItem::kind)
                .findAny().orElseThrow(() -> new IllegalStateException("Unknown AnnotationMember Type"));
    }

    record KindTableItem(Predicate<Object> predicate, Kind kind) {
    }

    private static final List<KindTableItem> KIND_TABLE = List.of(
            new KindTableItem(value -> value instanceof Boolean, Kind.BOOLEAN),
            new KindTableItem(value -> value instanceof Byte, Kind.BYTE),
            new KindTableItem(value -> value instanceof Short, Kind.SHORT),
            new KindTableItem(value -> value instanceof Integer, Kind.INT),
            new KindTableItem(value -> value instanceof Long, Kind.LONG),
            new KindTableItem(value -> value instanceof Float, Kind.FLOAT),
            new KindTableItem(value -> value instanceof Double, Kind.DOUBLE),
            new KindTableItem(value -> value instanceof Character, Kind.CHAR),
            new KindTableItem(value -> value instanceof String, Kind.STRING),
            new KindTableItem(value -> value instanceof Enum, Kind.ENUM),
            new KindTableItem(value -> value instanceof Class, Kind.CLASS),
            new KindTableItem(value -> value instanceof Annotation, Kind.NESTED_ANNOTATION),
            new KindTableItem(value -> value instanceof boolean[], ARRAY),
            new KindTableItem(value -> value instanceof byte[], ARRAY),
            new KindTableItem(value -> value instanceof short[], ARRAY),
            new KindTableItem(value -> value instanceof int[], ARRAY),
            new KindTableItem(value -> value instanceof long[], ARRAY),
            new KindTableItem(value -> value instanceof float[], ARRAY),
            new KindTableItem(value -> value instanceof double[], ARRAY),
            new KindTableItem(value -> value instanceof char[], ARRAY),
            new KindTableItem(value -> value instanceof Object[], ARRAY)
    );

    @Override
    public Kind kind() {
        return this.kind;
    }

    private void checkKind(Kind kind) {
        if (this.kind != kind) {
            throw new IllegalStateException("Annotation member value is not a " + kind.name());
        }
    }

    @Override
    public boolean asBoolean() {
        checkKind(Kind.BOOLEAN);
        return (Boolean) value;
    }

    @Override
    public byte asByte() {
        checkKind(Kind.BYTE);
        return (Byte) value;
    }

    @Override
    public short asShort() {
        checkKind(Kind.SHORT);
        return (Short) value;
    }

    @Override
    public int asInt() {
        checkKind(Kind.INT);
        return (Integer) value;
    }

    @Override
    public long asLong() {
        checkKind(Kind.LONG);
        return (Long) value;
    }

    @Override
    public float asFloat() {
        checkKind(Kind.FLOAT);
        return (Float) value;
    }

    @Override
    public double asDouble() {
        checkKind(Kind.DOUBLE);
        return (Double) value;
    }

    @Override
    public char asChar() {
        checkKind(Kind.CHAR);
        return (Character) value;
    }

    @Override
    public String asString() {
        checkKind(Kind.STRING);
        return (String) value;
    }

    @Override
    public <E extends Enum<E>> E asEnum(Class<E> enumType) {
        checkKind(Kind.ENUM);
        return enumType.cast(value);
    }

    @Override
    public ClassInfo asEnumClass() {
        checkKind(Kind.ENUM);
        Class<?> enumType = ((Enum<?>) value).getDeclaringClass();
        return new ClassInfoImpl(enumType);
    }

    @Override
    public String asEnumConstant() {
        checkKind(Kind.ENUM);
        return ((Enum<?>) value).name();
    }

    @Override
    public jakarta.enterprise.lang.model.types.Type asType() {
        checkKind(Kind.CLASS);
        Class<?> clazz = (Class<?>) value;
        return TypeImpl.createType(new AnnotatedClassTypeDecorator(clazz));
    }

    @Override
    public AnnotationInfo asNestedAnnotation() {
        checkKind(Kind.NESTED_ANNOTATION);
        return new AnnotationInfoImpl((Annotation) value);
    }

    @Override
    public List<AnnotationMember> asArray() {
        checkKind(ARRAY);
        return StreamSupport.stream(ofSpliterator(value), false)
                .<AnnotationMember>map(AnnotationMemberImpl::new)
                .toList();
    }

}
