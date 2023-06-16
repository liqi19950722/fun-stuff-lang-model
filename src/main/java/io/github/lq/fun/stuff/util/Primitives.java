package io.github.lq.fun.stuff.util;

import it.unimi.dsi.fastutil.booleans.BooleanSpliterators;
import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
import it.unimi.dsi.fastutil.chars.CharSpliterators;
import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
import it.unimi.dsi.fastutil.floats.FloatSpliterators;
import it.unimi.dsi.fastutil.ints.IntSpliterators;
import it.unimi.dsi.fastutil.longs.LongSpliterators;
import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
import it.unimi.dsi.fastutil.shorts.ShortSpliterators;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;

import static jakarta.enterprise.lang.model.AnnotationMember.Kind.ARRAY;

public interface Primitives {
    static Spliterator<?> ofSpliterator(Object value) {
        if (value instanceof boolean[] booleanArray) {
            return BooleanSpliterators.wrap(booleanArray);
        }
        if (value instanceof byte[] byteArray) {
            return ByteSpliterators.wrap(byteArray);
        }
        if (value instanceof short[] shortArray) {
            return ShortSpliterators.wrap(shortArray);
        }
        if (value instanceof int[] intArray) {
            return IntSpliterators.wrap(intArray);
        }
        if (value instanceof long[] longArray) {
            return LongSpliterators.wrap(longArray);
        }
        if (value instanceof float[] floatArray) {
            return FloatSpliterators.wrap(floatArray);
        }
        if (value instanceof double[] doubleArray) {
            return DoubleSpliterators.wrap(doubleArray);
        }
        if (value instanceof char[] charArray) {
            return CharSpliterators.wrap(charArray);
        }
        if (value instanceof Object[] objectArray) {
            return ObjectSpliterators.wrap(objectArray);
        }
        throw new IllegalStateException("Unsupported value");
    }
}
