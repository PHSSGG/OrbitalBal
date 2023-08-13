package phss.orbitalbal.config.providers;

import org.bukkit.ChatColor;
import phss.orbitalbal.config.Config;
import phss.orbitalbal.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class MessagesConfig {

    final private Config langConfig;

    public MessagesConfig(Config langConfig) {
        this.langConfig = langConfig;
    }

    public String getMessage(String message) {
        return MessageUtils.replaceColor(langConfig.get().getString("Messages." + message, ""));
    }

    public List<String> getMessageList(String message) {
        return MessageUtils.replaceColor(langConfig.get().getStringList("Messages." + message));
    }

}
