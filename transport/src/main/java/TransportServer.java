public interface TransportServer {
    void init(int port,RequestHandler requestHandler);
    void start();
    void stop();
}
