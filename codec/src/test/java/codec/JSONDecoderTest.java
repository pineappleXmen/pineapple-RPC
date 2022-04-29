package codec;

import junit.framework.TestCase;

public class JSONDecoderTest extends TestCase {

    public void testDecode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("dashuaibi");
        bean.setAge(18);
        byte[] bytes= encoder.encode(bean);
        assertNotNull(bytes);
    }
}