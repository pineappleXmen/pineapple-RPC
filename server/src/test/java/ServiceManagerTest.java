import common.ReflectionUtils;
import dashuaibi.Request;
import dashuaibi.ServiceDescriptor;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class ServiceManagerTest {

    ServiceManager sm;
    @Before
    public void init(){
        sm=new ServiceManager();
    }
    @Test
    public void testRegister() {
        TestInterface bean = new testclass();
        sm.register(TestInterface.class,bean);
    }

    @Test
    public void testLookup() {
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor sdp =  ServiceDescriptor.from(TestInterface.class,method);
        Request request = new Request();
        request.setService(sdp);
        ServiceInstance sis =sm.lookup(request);
        assertNotNull(sis);
    }
}