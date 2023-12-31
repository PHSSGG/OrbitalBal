package phss.orbitalbal.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import phss.orbitalbal.config.providers.DatabaseConfig;
import phss.orbitalbal.database.Database;
import phss.orbitalbal.database.functions.SQLConsumer;
import phss.orbitalbal.database.functions.SQLFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase implements Database {

    private HikariDataSource dataSource;
    private final HikariConfig config;

    public MySQLDatabase(DatabaseConfig config) {
        this(config.getHostname(), config.getPort(),
                config.getDatabase(),
                config.getUsername(), config.getPassword());
    }

    public MySQLDatabase(String hostname, int port, String database, String username, String password) {
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
    }

    @Override
    public Connection openConnection() {
        if (dataSource == null) {
            dataSource = new HikariDataSource(config);
        }

        return getConnection();
    }

    @Override
    public <T> T executeSQL(String sql, SQLFunction<T> action) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = prepareStatement(connection, sql);
            return action.apply(statement);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void executeSQLQuery(String sql, SQLConsumer<ResultSet> action) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = prepareStatement(connection, sql);
            ResultSet resultSet = statement.executeQuery();

            action.accept(resultSet);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean isConnected() {
        return !dataSource.isClosed();
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}