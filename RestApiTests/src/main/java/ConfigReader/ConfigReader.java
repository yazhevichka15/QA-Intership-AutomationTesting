package ConfigReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final String username;
    private final String password;

    public ConfigReader() {
        Properties properties = readConfig();
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private Properties readConfig() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }

        return properties;
    }
}
