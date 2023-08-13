package phss.orbitalbal.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.Command;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.validators.*;
import phss.orbitalbal.transaction.BalanceTransaction;
import phss.orbitalbal.transaction.type.TransactionType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static phss.orbitalbal.utils.MessageUtils.setPlaceholdersToMessage;

public class GiveCommand extends Command {

    public GiveCommand(OrbitalBal plugin) {
        super(plugin, "give");
    }

    @Override
    public List<RequirementValidator<?>> getRequirements() {
        return Arrays.asList(new IsPlayerValidator(), new TargetValidator(), new AmountValidator(), new HasBalanceValidator());
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (!checkRequirements(sender, "give", 2, args)) return false;

        Player player = (Player) sender;
        if (args[0].equals(player.getName())) {
            sender.sendMessage(getMessagesConfig().getMessage("cannotSelf"));
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount = Double.parseDouble(args[1]);

        makeTransaction(player.getUniqueId(), TransactionType.WITHDRAW, amount);
        BalanceTransaction transaction = makeTransaction(target.getUniqueId(), TransactionType.DEPOSIT, amount);

        sender.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("giveSuccess"), sender, target, transaction));
        if (target.isOnline()) {
            Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
            onlineTarget.sendMessage(setPlaceholdersToMessage(getMessagesConfig().getMessage("accountDeposit"), sender, target, transaction));
        }
        return true;
    }

    private BalanceTransaction makeTransaction(UUID uuid, TransactionType transactionType, double amount) {
        BalanceTransaction transaction = new BalanceTransaction.Builder(uuid, getPlugin())
                .setTransactionType(transactionType)
                .setAmount(amount)
                .build();
        transaction.makeTransaction();

        return transaction;
    }

}
