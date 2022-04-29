public class Client {

    public static void main(String[] args) {
        RPCClient client = new RPCClient();
        CalcServer service = client.getProxy(CalcServer.class);
        int r1 = service.add(1,3);
        int r2 =service.minus(5,9);
        System.out.println(r1);
        System.out.println(r2);

    }
}
