package me.thelore.superclaim.chat;

import me.thelore.superclaim.storage.impl.LanguageConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

public class ChatManager {
    private final LanguageConfiguration languageConfiguration;

    public ChatManager() {
        this.languageConfiguration = new LanguageConfiguration();
    }

    public String getMessage(String path, Placeholder... placeholders) {
        String temp = languageConfiguration.getMessage(path);

        for(Placeholder placeholder : placeholders) {
            temp = temp.replace(placeholder.getKey(), placeholder.getValue());
        }
        temp = ChatColor.translateAlternateColorCodes('&', temp);

        return temp;
    }

    public void sendMessage(CommandSender sender, String path, Placeholder... placeholders) {
        String temp = languageConfiguration.getMessage(path);

        for(Placeholder placeholder : placeholders) {
            temp = temp.replace(placeholder.getKey(), placeholder.getValue());
        }
        temp = ChatColor.translateAlternateColorCodes('&', temp);

        sender.sendMessage(temp);
    }
}
