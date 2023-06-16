package io.github.lq.fun.stuff.lang.tck;

import io.github.lq.fun.stuff.lang.model.declarations.ClassInfoImpl;
import org.jboss.cdi.lang.model.tck.LangModelVerifier;
import org.junit.jupiter.api.Test;

public class LangModelTCKTest {

    @Test
    public void testLangModel() {
        var classInfo = new ClassInfoImpl(LangModelVerifier.class);
        LangModelVerifier.verify(classInfo);
    }

}
