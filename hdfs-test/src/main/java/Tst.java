import java.io.File;

public class Tst {
    public static void main(String[] args) {
//        testFile();
//        testFile2();
        System.out.printf(System.getProperty("user.dir"));

    }

    private static void testFile2() {
        File file = new File("/test2.txt");
        String absolutePath = file.getAbsolutePath();
        System.out.printf(absolutePath);
//        E:\test2.txt
    }

    private static void testFile() {
        File file = new File("test2.txt");
        String absolutePath = file.getAbsolutePath();
        System.out.printf(absolutePath);
//        E:\code\bigdata-test\test2.txt



    }
}
