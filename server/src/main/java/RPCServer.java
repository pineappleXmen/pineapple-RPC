import codec.Decoder;
import codec.Encoder;
import common.ReflectionUtils;
import dashuaibi.Request;
import dashuaibi.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RPCServer {
    private RPCServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;
    public RPCServer(RPCServerConfig config){
        this.config=config;
        this.net= ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);
        this.encoder=ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder=ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker= new ServiceInvoker();
    }
    public RPCServer(){
        this.config=new RPCServerConfig();
        this.net= ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);
        this.encoder=ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder=ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker= new ServiceInvoker();
    }
    public <T> void register(Class<T> interfaceClass,T bean) {
        serviceManager.register(interfaceClass,bean);
    }
    public void start(){
        this.net.start();
    }
    public void stop(){
        this.net.stop();
    }

    private RequestHandler handler= new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResponse) {
            Response resp = new Response();
            try {
                byte[] inbytes = IOUtils.readFully(receive,receive.available());
                Request request = decoder.decode(inbytes,Request.class);
                log.info("get request:{}",request);
                ServiceInstance sis = serviceManager.lookup(request);
                Object res =serviceInvoker.invoke(sis,request);
                resp.setData(res);
            } catch (Exception e) {
               log.warn(e.getMessage(),e);
               resp.setCode(1);
               resp.setMessage("rpc server got error"+e.getClass().getName()+":"+e.getMessage());
            }finally {
                try {
                byte[] outbytes =encoder.encode(resp);
                    toResponse.write(outbytes);
                    log.info("client got responsed");
                } catch (Exception e) {
                    log.warn(e.getMessage(),e);
                }
            }
        }
    };
}

