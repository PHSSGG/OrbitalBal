package phss.orbitalbal.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;
import phss.orbitalbal.config.providers.MessagesConfig;
import phss.orbitalbal.utils.MessageUtils;

import java.util.Collections;
import java.util.List;

public abstract class Command extends org.bukkit.command.Command implements CommandExecutor {

    final private OrbitalBal plugin;
    final private MessagesConfig messagesConfig;

    public Command(OrbitalBal plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.messagesConfig = new MessagesConfig(plugin.getLang());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return onExecute(sender, args);
    }

    public List<RequirementValidator<?>> getRequirements() {
        return Collections.emptyList();
    }

    public boolean checkRequirements(CommandSender sender, String command, int argsSize, String[] args) {
        if (args.length < argsSize) {
            onErrorResponse(sender, "help." + command, false);
            return false;
        }

        boolean meetRequirements = true;
        String errorMessage = null;

        for (RequirementValidator<?> requirement : getRequirements()) {
            RequirementValidatorResponse<?> response = requirement.validate(plugin, sender, args);
            if (!response.isSucceeded()) {
                meetRequirements = false;
                errorMessage = response.getErrorMessage();
                break;
            }
        }

        if (!meetRequirements) {
            onErrorResponse(sender, errorMessage, false);
            return false;
        }

        return true;
    }

    public abstract boolean onExecute(CommandSender sender, String[] args);
    public void onErrorResponse(CommandSender sender, String errorMessage, boolean isList) {
        if (isList) MessageUtils.sendMessageList(sender, getMessagesConfig().getMessageList(errorMessage));
        else sender.sendMessage(getMessagesConfig().getMessage(errorMessage));
    }

    public OrbitalBal getPlugin() {
        return plugin;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

}