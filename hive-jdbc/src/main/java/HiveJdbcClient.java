import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
        Connection con = DriverManager.getConnection("jdbc:hive2://vm01:10000/default", "lafeier", "lafeier888");
        Statement stmt = con.createStatement();


        String sql="insert into\n" +
                "    table test.score\n" +
                "values\n" +
                "    (1, \"语文\", 90),\n" +
                "    (1, \"数学\", 80),\n" +
                "    (2, \"语文\", 85),\n" +
                "    (2, \"数学\", 95),\n" +
                "    (3, \"语文\", 90),\n" +
                "    (3, \"数学\", 75)";


        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res);
        }


    }
}