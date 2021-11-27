package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.chat.Placeholder;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import me.thelore.superclaim.gui.ChunkSlot;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.task.AsyncTask;
import me.thelore.superclaim.util.ItemBuilder;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MapGuiProvider implements InventoryProvider, Messaging {
    private final SmartInventory gui;
    public MapGuiProvider(Player player) {
        gui = SmartInventory.builder()
                .provider(this)
                .id("mapGui")
                .title("Claims map")
                .closeable(true)
                .size(5, 9)
                .build();

        open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        new AsyncTask(() -> {
            ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();

            for (ChunkSlot chunk : getChunksAroundPlayer(player)) {
                int baseX = 4;
                int baseY = 2;

                int x = baseX + chunk.getXOffset();
                int y = baseY + chunk.getYOffset();

                String title = getChatManager().getMessage("claimMap_item_title", new Placeholder("{coord}", chunk.getChunk().getX() + " " + chunk.getChunk().getZ()));

                if (claimHandler.getClaim(chunk.getChunk()).size() == 0) {
                    contents.set(y, x, ClickableItem.empty(ItemBuilder.build(Material.WHITE_STAINED_GLASS_PANE, title)));
                } else {
                    List<Claim> claimList = claimHandler.getClaim(chunk.getChunk());
                    List<String> lore = new ArrayList<>();

                    boolean hasPermissions = false;

                    for (Claim claim : claimList) {
                        lore.add(getChatManager().getMessage("claimMap_item_lore",
                                new Placeholder("{claimName}", claim.getClaimIdentifier().getDisplayName()),
                                new Placeholder("{claimOwner}", claim.getClaimIdentifier().getPlayerName())));

                        ClaimPlayer claimPlayer = claim.getClaimPlayer(player.getName());

                        if (claimPlayer != null && claimPlayer.getClaimPermissions().size() > 0) {
                            hasPermissions = true;
                        }
                    }

                    if (hasPermissions) {
                        contents.set(y, x, ClickableItem.empty(ItemBuilder.build(Material.YELLOW_STAINED_GLASS_PANE, title, lore)));
                    } else {
                        contents.set(y, x, ClickableItem.empty(ItemBuilder.build(Material.RED_STAINED_GLASS_PANE, title, lore)));
                    }
                }
            }
        });
    }

    public Collection<ChunkSlot> getChunksAroundPlayer(Player player) {
        int[] xOffset = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] zOffset = {-2, -1, 0, 1, 2};

        World world = player.getWorld();
        int baseX = player.getLocation().getChunk().getX();
        int baseZ = player.getLocation().getChunk().getZ();

        Collection<ChunkSlot> chunksAroundPlayer = new HashSet<>();
        for (int x : xOffset) {
            for (int z : zOffset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunksAroundPlayer.add(new ChunkSlot(x, z, chunk));
            }
        }
        return chunksAroundPlayer;
    }

    private void open(Player player) {
        gui.open(player);
    }
}
