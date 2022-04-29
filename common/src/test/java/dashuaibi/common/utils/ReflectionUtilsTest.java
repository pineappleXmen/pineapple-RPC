package dashuaibi.common.utils;

import common.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReflectionUtilsTest {
    @Test
    public void newInstance(){
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(testClass);

    }
    @Test
    public void getPublicMethods(){
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1,publicMethods.length);
        String methodname = publicMethods[0].getName();
        assertEquals("b",methodname);

    }
    @Test
    public void invoke(){
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method b = publicMethods[0];
        TestClass test = new TestClass();
        Object r = ReflectionUtils.invoke(test, b);
        assertEquals("b",r);

    }
}
