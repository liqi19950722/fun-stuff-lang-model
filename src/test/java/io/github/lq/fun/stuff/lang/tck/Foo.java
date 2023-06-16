package io.github.lq.fun.stuff.lang.tck;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Parameter;

public class Foo {
    @A
    int a;

    void foo(int a) {
    }

    @Test
    public void should_() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        var foo = Foo.class.getDeclaredMethod("foo", int.class);
        var parameter = foo.getParameters()[0];
        var index = MethodHandles.lookup().findVarHandle(Parameter.class, "index", int.class);

        System.out.println(index.get(parameter));

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface A {

    }
}
