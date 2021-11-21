package me.thelore.superclaim.storage;

import lombok.Getter;
import lombok.SneakyThrows;
import me.thelore.superclaim.SuperClaim;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {
    @Getter
    private FileConfiguration configuration;

    private final File file;
    private final boolean saveDefaults;

    protected Configuration(String fileName, boolean saveDefaults) {
        String basePath = SuperClaim.getInstance().getDataFolder().getPath();
        fileName = fileName.endsWith(".yml") ? fileName : fileName + ".yml";
        this.saveDefaults = saveDefaults;

        file = new File(basePath + File.separator + fileName);

        setupConfiguration();
    }

    private void setupConfiguration() {
        if (!file.exists() && !saveDefaults) {
            createFile();
        }

        if (saveDefaults) {
            SuperClaim.getInstance().saveResource(file.getName(), false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    private void createFile() {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.createNewFile()) {
            System.out.println("Failed to create configuration file " + file.getName());
        }
    }

    protected void saveConfig() {
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
