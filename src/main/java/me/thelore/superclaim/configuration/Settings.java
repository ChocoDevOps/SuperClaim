package me.thelore.superclaim.configuration;

import me.thelore.superclaim.storage.impl.ConfigStorage;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private final ConfigStorage configStorage;
    private final Map<String, Object> settingList;

    public Settings() {
        this.configStorage = new ConfigStorage();
        this.settingList = new HashMap<>();

        loadSettings();
    }

    private void loadSettings() {
        for(String settings : configStorage.getConfiguration().getKeys(false)) {
            settingList.put(settings, configStorage.getConfiguration().get(settings));
        }
    }

    public Object getValue(String key) {
        return settingList.get(key);
    }
}
