package utils;

import java.io.IOException;
import java.util.Properties;

public abstract class PropertyLoader {
    public static Properties loadProperties (){
        Properties props = new Properties();

        try {
            props.load(PropertyLoader.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}
