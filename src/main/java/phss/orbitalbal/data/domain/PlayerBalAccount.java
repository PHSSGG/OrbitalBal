package phss.orbitalbal.data.domain;

import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.UUID;

public class PlayerBalAccount {

    public static DecimalFormat BALANCE_FORMAT = new DecimalFormat("#.###", new DecimalFormatSymbols());

    UUID uuid;
    BigDecimal balance;

    public PlayerBalAccount(UUID uuid, BigDecimal balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getBalanceFormatted() {
        return BALANCE_FORMAT.format(balance);
    }

    public void setBalance(BigDecimal balance) {
        if (balance.doubleValue() < 0.0) {
            Bukkit.getLogger().info("Cannot set negative amount of " + balance.doubleValue() + " to uuid " + uuid);
            this.balance = BigDecimal.ZERO;
        } else {
            this.balance = balance;
        }
    }

}
