import common.ReflectionUtils;
import dashuaibi.Peer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class randomTransportSelector implements TransportSelector{
    private List<TransportClient>  clients;
    public  randomTransportSelector(){
        clients=new ArrayList<>();
    }
    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count=Math.max(count,1);
        for(Peer peer:peers){
            for(int i=0;i<count;i++){
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect {}",peer);
        }
    }

    @Override
    public TransportClient select() {
        int i= new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public void close() {
        for(TransportClient client:clients){
            client.close();
        }
        clients.clear();
    }
}
