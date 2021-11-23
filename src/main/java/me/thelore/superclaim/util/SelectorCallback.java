package me.thelore.superclaim.util;

import me.thelore.superclaim.claim.Territory;

public interface SelectorCallback {
    void onDone(Territory territory);
    void onError();
}
