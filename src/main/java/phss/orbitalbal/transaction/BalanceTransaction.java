package phss.orbitalbal.transaction;

import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.data.domain.PlayerBalAccount;
import phss.orbitalbal.data.repository.BalRepository;
import phss.orbitalbal.transaction.type.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public class BalanceTransaction {

    UUID uuid;
    double amount;

    TransactionType transactionType;

    BalRepository balRepository;

    public BalanceTransaction(UUID uuid, double amount, TransactionType transactionType, BalRepository balRepository) {
        this.uuid = uuid;
        this.amount = amount;
        this.transactionType = transactionType;
        this.balRepository = balRepository;
    }

    public void makeTransaction() {
        updateAccountBalance();
    }

    protected void updateAccountBalance() {
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        PlayerBalAccount account = balRepository.getAccount(uuid).orElse(balRepository.createAccount(uuid));

        switch (transactionType) {
            case DEPOSIT:
                account.setBalance(account.getBalance().add(bigDecimalAmount));
                break;
            case WITHDRAW:
                account.setBalance(account.getBalance().subtract(bigDecimalAmount));
                break;
            case SET:
                account.setBalance(bigDecimalAmount);
                break;
        }

        if (account.getBalance().doubleValue() > 0.0) balRepository.savePlayerAccount(account);
        else balRepository.deletePlayerAccount(account);
    }

    public double getAmount() {
        return amount;
    }

    public static class Builder {

        UUID uuid;
        OrbitalBal plugin;

        double amount = 0;
        TransactionType transactionType = TransactionType.DEPOSIT;

        public Builder(UUID uuid, OrbitalBal plugin) {
            this.uuid = uuid;
            this.plugin = plugin;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public BalanceTransaction build() {
            return new BalanceTransaction(uuid, amount, transactionType, plugin.getBalRepository());
        }

    }

}
