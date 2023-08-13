package phss.orbitalbal.config.providers;

import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseConfig {

    String hostname;
    int port;

    String database;

    String username;
    String password;

    public DatabaseConfig(FileConfiguration storage) {
        this(
                storage.getString("Database.hostname"),
                storage.getInt("Database.port"),
                storage.getString("Database.database"),
                storage.getString("Database.username"),
                storage.getString("Database.password")
        );
    }

    public DatabaseConfig(String hostname, int port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
