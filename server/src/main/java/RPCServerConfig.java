import codec.Decoder;
import codec.Encoder;
import codec.JSONDecoder;
import codec.JSONEncoder;
import lombok.Data;

@Data
public class RPCServerConfig {
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port =3000;
}
