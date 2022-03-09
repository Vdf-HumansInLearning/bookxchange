package utils;



//import org.flywaydb.core.Flyway;

import java.io.File;
import java.sql.*;
import java.util.Properties;


public class JdbcConnection {


    private static Connection con;

    private static String url = "jdbc:mysql://localhost:3306/bookOLX";
    private static String username = "root";
    private static String password = "root";

  public static void main(String[] args) {
    //  getConnection();

        File resourcesDirectory = new File("src/main/resources");
        String absolutePath = resourcesDirectory.getAbsolutePath();
      System.out.println(absolutePath);


  }

    public static Connection getConnection() {
        Properties properties = PropertyLoader.loadProperties();


        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
