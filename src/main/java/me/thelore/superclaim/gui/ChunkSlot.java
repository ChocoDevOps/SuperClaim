package me.thelore.superclaim.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Chunk;

@Getter
@AllArgsConstructor
public class ChunkSlot {
    private int xOffset;
    private int yOffset;

    private Chunk chunk;
}
