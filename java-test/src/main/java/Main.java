import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

interface Expr {
    public boolean filter(int x);
}

public class Main {

    public static void testFilter(Expr expr) {
        System.out.println("filter is run..");
    }

    public static void main(String[] args) {
        testFilter((x) -> {
            boolean flag = x > 0;
            System.out.println("LOG:" + x);
            return flag;
        });
    }
}
