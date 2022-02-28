package me.krymz0n.arenadisinfectant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    private final ArrayList<Block> toRemove = new ArrayList<>();

    private final Logger log = getServer().getLogger();

    @Override
    public void onEnable() {
        removeBlocks();

        Bukkit.getScheduler().runTaskTimer(this, this::purge, 20L, 1500L);


    }

    @Override
    public void onDisable() {
        purge();
    }

    private void removeBlocks() { // inefficient af,but it's a start
        log.info("Removing some blocks on startup, this may cause the server to take extra time to load...");
        int radius = 150;
        for (int x = radius; x >= -radius; x--) {
            for (int y = 150; y >= 1; y--) {
                for (int z = radius; z >= -radius; z--) {
                    Block b = Bukkit.getWorld("World").getBlockAt(0, 0, 0);
                    Block newBlock = b.getRelative(x, y, z);
                    if (newBlock.getType() != Material.AIR) {
                        if (newBlock.getType() != Material.BEDROCK) {
                            newBlock.setType(Material.AIR);
                        }
                    }
                }
            }
        }

        log.info("Block removal complete!");
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent evt) {
        if (evt.getBlock().getLocation().getBlockY() < 151 && evt.getBlock() != null) {
            toRemove.add(evt.getBlock());
        }
    }

    private void purge() {
        log.info("Starting purge, this can take a few seconds...");
        for (Block b : toRemove) {
            if (b != null && b.getType() != Material.AIR) {
                b.setType(Material.AIR);
                toRemove.remove(b);
            }
        }
        log.info("Purge complete!");
    }
}
