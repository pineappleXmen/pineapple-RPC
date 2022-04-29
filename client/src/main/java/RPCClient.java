import codec.Decoder;
import codec.Encoder;
import common.ReflectionUtils;

import java.lang.reflect.Proxy;

public class RPCClient {
    private RPCClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;
    public RPCClient(){
        this(new RPCClientConfig());
    }
    public RPCClient(RPCClientConfig config){
        this.config=config;
        this.encoder= ReflectionUtils.newInstance(this.config.getEncoderclass());
        this.decoder= ReflectionUtils.newInstance(this.config.getDecoderclass());
        this.selector= ReflectionUtils.newInstance(this.config.getTransportSelectorclass());
        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClass()
        );
    }
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz,encoder,decoder,selector)
        );
    }
}
