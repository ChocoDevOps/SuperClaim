package me.thelore.superclaim.claim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Territory {
    private String worldName;

    private int minX;
    private int minY;
    private int minZ;

    private int maxX;
    private int maxY;
    private int maxZ;
}
