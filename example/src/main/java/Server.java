public class Server {
    public static void main(String[] args) {
        RPCServer server = new RPCServer();
        server.register(CalcServer.class,new CalcServerimpl());
        server.start();

    }
}
