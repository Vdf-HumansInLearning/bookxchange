package utils;


import org.flywaydb.core.Flyway;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


public class JdbcConnection {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pass = "root";

        File resourcesDirectory = new File("src/main/resources");
        String absolutePath = resourcesDirectory.getAbsolutePath();

        Flyway fdb = Flyway.configure()
                .createSchemas(true)
                .dataSource(url,user,pass)
                .locations("filesystem:"+absolutePath+"/db/migration")
                .schemas("bookOLX")
                .load();

        fdb.clean();
        fdb.migrate();
    }
}
