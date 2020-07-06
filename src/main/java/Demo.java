import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        File file = new File("/1.csv");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        FileReader fileReader = new FileReader(file);
        int read = fileReader.read();
        System.out.println(read);
    }
}
