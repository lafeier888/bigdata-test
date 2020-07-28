import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class POIDataProcess {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\lafeier\\Desktop\\poi\\poi样例数据.csv");
        String content = FileUtils.readFileToString(file);
        String[] split = content.split("0: jdbc:hive2://dd001:2181,dd008:2181,dd015:2>");
        System.out.println(split.length);
    }
}
