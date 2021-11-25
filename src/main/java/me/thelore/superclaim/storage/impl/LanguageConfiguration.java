package me.thelore.superclaim.storage.impl;

import me.thelore.superclaim.storage.Configuration;

public class LanguageConfiguration extends Configuration {
    public LanguageConfiguration() {
        super("language.yml", true);
    }

    public String getMessage(String path) {
        return getConfiguration().getString(path);
    }
}
