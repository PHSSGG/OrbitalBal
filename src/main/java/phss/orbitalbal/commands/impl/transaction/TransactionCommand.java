package phss.orbitalbal.commands.impl.transaction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.Command;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.validators.AmountValidator;
import phss.orbitalbal.commands.requirements.validators.IsOperatorValidator;
import phss.orbitalbal.commands.requirements.validators.TargetValidator;
import phss.orbitalbal.transaction.BalanceTransaction;
import phss.orbitalbal.transaction.type.TransactionType;

import java.util.Arrays;
import java.util.List;

public abstract class TransactionCommand extends Command {

    public TransactionCommand(OrbitalBal plugin, String name) {
        super(plugin, name);
    }

    @Override
    public List<RequirementValidator<?>> getRequirements() {
        return Arrays.asList(new IsOperatorValidator(), new TargetValidator(), new AmountValidator());
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (!checkRequirements(sender, getCommandName(), 2, args)) return false;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount = Double.parseDouble(args[1]);

        BalanceTransaction transaction = new BalanceTransaction.Builder(target.getUniqueId(), getPlugin())
                .setTransactionType(getTransactionType())
                .setAmount(amount)
                .build();
        transaction.makeTransaction();

        executePostTransaction(sender, target, transaction);
        return true;
    }

    public abstract String getCommandName();
    public abstract TransactionType getTransactionType();

    protected abstract void executePostTransaction(CommandSender sender, OfflinePlayer target, BalanceTransaction transaction);

}
