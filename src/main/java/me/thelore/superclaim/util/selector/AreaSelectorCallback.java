package me.thelore.superclaim.util.selector;

import me.thelore.superclaim.claim.Territory;

public interface AreaSelectorCallback {
    void onDone(Territory territory);
    void onError();
}
