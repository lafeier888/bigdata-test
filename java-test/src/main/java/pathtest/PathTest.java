package pathtest;

import java.io.InputStream;
import java.net.URL;

public class PathTest {
    public static void main(String[] args) {
        URL path1 = PathTest.class.getResource("");//   /classes/pathtest/
        URL path2 = PathTest.class.getResource("/");//  /classes/
        System.out.println(path1);
        System.out.println(path2);


        URL path3 = PathTest.class.getClassLoader().getResource("");//  /classes/
        URL path4 = PathTest.class.getClassLoader().getResource("/");// null
        System.out.println(path3);
        System.out.println(path4);

    }
}
