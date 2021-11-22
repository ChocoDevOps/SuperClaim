package me.thelore.superclaim.task;

import me.thelore.superclaim.SuperClaim;
import org.bukkit.Bukkit;

public class AsyncTask {
    private Runnable runnable;

    public AsyncTask(Runnable runnable) {
        this.runnable = runnable;

        run();
    }

    private void run() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(SuperClaim.getInstance(), runnable, 1);
    }
}
