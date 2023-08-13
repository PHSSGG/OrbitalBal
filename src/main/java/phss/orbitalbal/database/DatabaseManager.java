package phss.orbitalbal.database;

import phss.orbitalbal.config.providers.DatabaseConfig;
import phss.orbitalbal.database.impl.MySQLDatabase;

public class DatabaseManager {

    final public static String USERS_TABLE = "bal_users";

    Database database;

    public DatabaseManager initDatabase(DatabaseConfig databaseConfig) {
        database = new MySQLDatabase(databaseConfig);

        openDatabaseConnection();
        if (database.isConnected()) {
            createDefaultTables();
        }
        return this;
    }

    public void openDatabaseConnection() {
        database.openConnection();
    }

    public void createDefaultTables() {
        getDatabase().execute("CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (uuid VARCHAR(36) PRIMARY KEY NOT NULL, balance BIGINT);");
    }

    public Database getDatabase() {
        return database;
    }

}