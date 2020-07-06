import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FSApiDemo {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
//        federation集群 使用viewfs
//        FileSystem fs = FileSystem.get(new URI("viewfs://federationfs"), configuration, "lafeier");
//        listfiles(fs,"/ns1");

//        ha集群
//        FileSystem fs = FileSystem.get(new URI("hdfs://mycluster"), configuration, "lafeier");

        FileSystem fs = FileSystem.get(new URI("hdfs://vm01:9000"), configuration, "lafeier");
        listfiles(fs, "/");
//        fs.copyFromLocalFile(new Path("" ),new Path("/user/lafeier"));


        fs.close();
    }


    private static void listfiles(FileSystem fileSystem, String path) throws IOException {
        RemoteIterator<LocatedFileStatus> remoteIterator = fileSystem.listFiles(new Path(path), true);
        while (remoteIterator.hasNext()) {
            LocatedFileStatus next = remoteIterator.next();
            String name = next.getPath().getName();
            System.out.println(name);

        }
    }
}
