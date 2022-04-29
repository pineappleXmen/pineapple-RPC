public class CalcServerimpl implements CalcServer{
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int minus(int a, int b) {
        return a-b;
    }
}
