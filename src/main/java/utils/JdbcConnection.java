package utils;

import org.flywaydb.core.Flyway;

import java.io.File;
import java.sql.*;
import java.util.Properties;


public class JdbcConnection {

    private static String url;
    private static String username;
    private static String password;
    private static Connection con;

  public static void main(String[] args) {
      getConnection();

        File resourcesDirectory = new File("src/main/resources");
        String absolutePath = resourcesDirectory.getAbsolutePath();

        Flyway fdb = Flyway.configure()
                .createSchemas(true)
                .dataSource(url,username,password)
                .locations("filesystem:"+absolutePath+"/db/migration")
                .schemas("bookOLX")
                .load();

//        fdb.clean();
        fdb.migrate();


  }

    public static Connection getConnection() {
        Properties properties = PropertyLoader.loadProperties();

        url = properties.getProperty("DB_URL");
        username = properties.getProperty("DB_USER");
        password = properties.getProperty("DB_PASSWORD");

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
