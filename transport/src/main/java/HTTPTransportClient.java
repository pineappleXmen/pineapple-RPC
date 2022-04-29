import dashuaibi.Peer;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HTTPTransportClient implements TransportClient{
    private String url;
    @Override
    public void connect(Peer peer) {
        this.url="http://"+peer.getHost()+":"+peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            IOUtils.copy(data,urlConnection.getOutputStream());
            int res = urlConnection.getResponseCode();
            if(res==HttpURLConnection.HTTP_OK){
                return urlConnection.getInputStream();
            }else {
                return urlConnection.getErrorStream();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void close() {

    }
}
