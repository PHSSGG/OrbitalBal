package phss.orbitalbal;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import phss.orbitalbal.commands.impl.*;
import phss.orbitalbal.commands.impl.transaction.*;
import phss.orbitalbal.config.Config;
import phss.orbitalbal.config.providers.DatabaseConfig;
import phss.orbitalbal.data.dao.BalDao;
import phss.orbitalbal.data.repository.BalRepository;
import phss.orbitalbal.database.DatabaseManager;

import java.io.IOException;

public class OrbitalBal extends JavaPlugin {

    DatabaseManager databaseManager;

    BalDao balDao;
    BalRepository balRepository;

    Config settings = new Config(this, "settings");
    Config lang = new Config(this, "lang");

    @Override
    public void onEnable() {
        loadConfigs();

        databaseManager = new DatabaseManager().initDatabase(new DatabaseConfig(settings.get()));
        balDao = new BalDao(databaseManager.getDatabase());
        balRepository = new BalRepository(balDao);

        registerCommands();
    }

    private void registerCommands() {
        getCommand("bal").setExecutor(new BalCommand(this));
        getCommand("earn").setExecutor(new EarnCommand(this));
        getCommand("give").setExecutor(new GiveCommand(this));
        getCommand("deposit").setExecutor(new DepositCommand(this));
        getCommand("remove").setExecutor(new RemoveCommand(this));
        getCommand("setbal").setExecutor(new SetCommand(this));
    }

    private void loadConfigs() {
        try {
            settings.load();
            lang.load();
        } catch (IOException | InvalidConfigurationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public BalRepository getBalRepository() {
        return balRepository;
    }

    public Config getSettings() {
        return settings;
    }

    public Config getLang() {
        return lang;
    }

}