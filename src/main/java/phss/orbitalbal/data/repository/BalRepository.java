package phss.orbitalbal.data.repository;

import org.bukkit.OfflinePlayer;
import phss.orbitalbal.data.dao.BalDao;
import phss.orbitalbal.data.domain.PlayerBalAccount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class BalRepository {

    HashMap<UUID, PlayerBalAccount> accounts;

    final private BalDao balDao;

    public BalRepository(BalDao balDao) {
        this.balDao = balDao;
        this.accounts = balDao.loadAccounts();
    }

    public Optional<PlayerBalAccount> getAccount(UUID uuid) {
        return Optional.ofNullable(accounts.get(uuid));
    }

    public PlayerBalAccount createAccount(UUID uuid) {
        Optional<PlayerBalAccount> foundAccount = getAccount(uuid);
        if (foundAccount.isPresent()) {
            return foundAccount.get();
        } else {
            PlayerBalAccount account = new PlayerBalAccount(uuid, BigDecimal.ZERO);
            accounts.put(uuid, account);

            return account;
        }
    }

    public void savePlayerAccount(PlayerBalAccount account) {
        accounts.put(account.getUuid(), account);
        balDao.saveAccount(account);
    }

    public void deletePlayerAccount(PlayerBalAccount account) {
        if (accounts.containsKey(account.getUuid())) {
            accounts.remove(account.getUuid());
            balDao.deleteAccount(account);
        }
    }

}
