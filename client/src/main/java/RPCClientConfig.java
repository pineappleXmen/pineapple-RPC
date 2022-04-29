import codec.Decoder;
import codec.Encoder;
import codec.JSONDecoder;
import codec.JSONEncoder;
import dashuaibi.Peer;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RPCClientConfig {
    private Class<? extends TransportClient> transportClass =HTTPTransportClient.class;
    private Class<? extends Encoder> encoderclass = JSONEncoder.class;
    private Class<? extends Decoder> decoderclass = JSONDecoder.class;
    private Class<? extends TransportSelector> transportSelectorclass = randomTransportSelector.class;
    private int connectCount =1;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1",3000)
    );
}
