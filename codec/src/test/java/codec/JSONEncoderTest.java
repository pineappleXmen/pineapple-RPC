package codec;

import junit.framework.TestCase;

public class JSONEncoderTest extends TestCase {

    public void testEncode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setName("dashuaibi");
        bean.setAge(18);
        byte[] bytes= encoder.encode(bean);
        Decoder decoder= new JSONDecoder();
        TestBean bean2 = decoder.decode(bytes, TestBean.class);
        assertEquals(bean2.getName(),"dashuaibi");
    }
}