package me.thelore.superclaim.chat;

import me.thelore.superclaim.SuperClaim;

public interface Messaging {
    default ChatManager getChatManager() {
        return SuperClaim.getInstance().getChatManager();
    }
}
