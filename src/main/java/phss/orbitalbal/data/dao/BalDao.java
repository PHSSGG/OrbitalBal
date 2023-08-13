package phss.orbitalbal.data.dao;

import phss.orbitalbal.data.domain.PlayerBalAccount;
import phss.orbitalbal.database.Database;
import phss.orbitalbal.database.DatabaseManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class BalDao {

    static String SAVE_SQL = "INSERT INTO %s (uuid, balance) VALUES ('%s', '%s') ON DUPLICATE KEY UPDATE balance = VALUES(balance);";
    static String DELETE_SQL = "DELETE FROM %s WHERE uuid='%s';";

    private final Database database;

    public BalDao(Database database) {
        this.database = database;
    }

    public HashMap<UUID, PlayerBalAccount> loadAccounts() {
        HashMap<UUID, PlayerBalAccount> accounts = new HashMap<>();

        database.executeQuery("SELECT * FROM " + DatabaseManager.USERS_TABLE, resultSet -> {
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                BigDecimal balance = resultSet.getBigDecimal("balance");

                accounts.put(uuid, new PlayerBalAccount(uuid, balance));
            }
        });

        return accounts;
    }

    public void saveAccount(PlayerBalAccount account) {
        database.executeUpdate(String.format(
                SAVE_SQL,
                DatabaseManager.USERS_TABLE,
                account.getUuid().toString(), account.getBalance()
        ));
    }

    public void deleteAccount(PlayerBalAccount account) {
        database.executeUpdate(String.format(
                DELETE_SQL,
                DatabaseManager.USERS_TABLE,
                account.getUuid().toString()
        ));
    }

}
