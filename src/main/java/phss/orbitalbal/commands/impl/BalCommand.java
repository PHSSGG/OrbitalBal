package phss.orbitalbal.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.Command;
import phss.orbitalbal.data.domain.PlayerBalAccount;
import phss.orbitalbal.data.repository.BalRepository;

public class BalCommand extends Command {

    public BalCommand(OrbitalBal plugin) {
        super(plugin, "bal");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(getMessagesConfig().getMessage("help.bal"));
                return false;
            }

            Player player = (Player) sender;
            executeBalSelf(player);
            return true;
        }

        executeBalTarget(sender, args);
        return true;
    }

    private void executeBalSelf(Player player) {
        BalRepository balRepository = getPlugin().getBalRepository();
        PlayerBalAccount account = balRepository.getAccount(player.getUniqueId())
                .orElse(balRepository.createAccount(player.getUniqueId()));

        player.sendMessage(getMessagesConfig().getMessage("balSelf").replace("{balance}", account.getBalanceFormatted()));
    }

    private void executeBalTarget(CommandSender sender, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null || !target.hasPlayedBefore()) {
            sender.sendMessage(getMessagesConfig().getMessage("targetNotFound"));
            return;
        }

        BalRepository balRepository = getPlugin().getBalRepository();
        PlayerBalAccount targetAccount = balRepository.getAccount(target.getUniqueId())
                .orElse(balRepository.createAccount(target.getUniqueId()));

        sender.sendMessage(getMessagesConfig().getMessage("balOther").replace("{target}", target.getName()).replace("{balance}", targetAccount.getBalanceFormatted()));
    }

}
