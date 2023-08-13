package phss.orbitalbal.utils;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import phss.orbitalbal.transaction.BalanceTransaction;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    public static void sendMessageList(CommandSender sender, List<String> message) {
        for (String line : message) {
            sender.sendMessage(line);
        }
    }

    public static String replaceColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> replaceColor(List<String> message) {
        ArrayList<String> list = new ArrayList<>();
        message.forEach(line -> list.add(replaceColor(line)));

        return list;
    }

    public static String setPlaceholdersToMessage(String message, CommandSender sender, OfflinePlayer target, BalanceTransaction transaction) {
        return message
                .replace("{sender}", sender.getName())
                .replace("{target}", target.getName())
                .replace("{amount}", "" + transaction.getAmount());
    }

}
