package phss.orbitalbal.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class Config {

    private File file;
    private FileConfiguration fileConfiguration;

    private Plugin plugin;
    private String fileName;

    private String folder;

    public Config(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + ".yml";
        this.folder = plugin.getDataFolder().getPath();
    }

    public Config(Plugin plugin, String fileName, String folder) {
        this.plugin = plugin;
        this.fileName = fileName + ".yml";
        this.folder = plugin.getDataFolder().getPath() + "/" + folder;
    }

    public FileConfiguration get() {
        return fileConfiguration;
    }

    public void load() throws IOException, InvalidConfigurationException {
        File pluginFolder = new File(folder);
        if (!pluginFolder.exists()) pluginFolder.mkdirs();

        createNewFile();
        fileConfiguration = createFileConfigurationFromFile(file);
    }

    private void createNewFile() {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            InputStream inputStream = plugin.getResource(fileName);
            try {
                Files.copy(Objects.requireNonNull(inputStream), file.toPath());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private FileConfiguration createFileConfigurationFromFile(File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(file);
            return yamlConfiguration;
        } catch (IOException | InvalidConfigurationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void reload() {
        try {
            load();
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}