import codec.Decoder;
import codec.Encoder;
import dashuaibi.Request;
import dashuaibi.Response;
import dashuaibi.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.rmi.Remote;

@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;
    private Class clazz;
    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder,TransportSelector transportSelector){
        this.encoder=encoder;
        this.decoder=decoder;
        this.selector=transportSelector;
        this.clazz=clazz;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request =new Request();
        request.setService(ServiceDescriptor.from(clazz,method));
        request.setParameters(args);
        Response resp = invokeRemote(request);
        if(resp==null||resp.getCode()!=0){
            throw new IllegalStateException("fail to invoke"+resp);
        }
        return  resp.getData();

    }

    private Response invokeRemote(Request request) {
        Response resp = null;
        TransportClient client =null;
        try {
            client=selector.select();
            byte[] outbytes=encoder.encode(request);
            InputStream receive = client.write(new ByteArrayInputStream(outbytes));
            byte[] inbytes = IOUtils.readFully(receive, receive.available());
            resp=decoder.decode(inbytes,Response.class);

        } catch (Exception e) {
            log.warn(e.getMessage(),e);
            resp=new Response();
            resp.setCode(1);
            resp.setMessage("rpcclient got error"+e.getClass()+":"+e.getMessage());
        } finally {
            if(client!=null)
                selector.release(client);
        }
        return resp;
    }


}
