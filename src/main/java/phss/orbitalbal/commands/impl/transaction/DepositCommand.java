package phss.orbitalbal.commands.impl.transaction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.transaction.BalanceTransaction;
import phss.orbitalbal.transaction.type.TransactionType;

import static phss.orbitalbal.utils.MessageUtils.setPlaceholdersToMessage;

public class DepositCommand extends TransactionCommand {

    public DepositCommand(OrbitalBal plugin) {
        super(plugin, "deposit");
    }

    @Override
    public String getCommandName() {
        return "deposit";
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.DEPOSIT;
    }

    @Override
    protected void executePostTransaction(CommandSender sender, OfflinePlayer target, BalanceTransaction transaction) {
        sender.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("depositSuccess"), sender, target, transaction));
        if (target.isOnline()) {
            Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
            onlineTarget.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("accountDepositAdmin"), sender, target, transaction));
        }
    }

}