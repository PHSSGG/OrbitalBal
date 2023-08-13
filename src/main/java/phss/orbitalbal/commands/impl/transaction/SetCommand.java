package phss.orbitalbal.commands.impl.transaction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.transaction.BalanceTransaction;
import phss.orbitalbal.transaction.type.TransactionType;


import static phss.orbitalbal.utils.MessageUtils.setPlaceholdersToMessage;

public class SetCommand extends TransactionCommand {

    public SetCommand(OrbitalBal plugin) {
        super(plugin, "setbal");
    }

    @Override
    public String getCommandName() {
        return "setbal";
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.SET;
    }

    @Override
    protected void executePostTransaction(CommandSender sender, OfflinePlayer target, BalanceTransaction transaction) {
        sender.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("setSuccess"), sender, target, transaction));
        if (target.isOnline()) {
            Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
            onlineTarget.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("accountSet"), sender, target, transaction));
        }
    }

}