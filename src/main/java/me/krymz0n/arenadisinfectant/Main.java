package me.krymz0n.arenadisinfectant;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public final class Main extends JavaPlugin implements Listener {
    private final ArrayList<Block> toRemove = new ArrayList<>();

    private final Logger log = getServer().getLogger();

    @Override
    public void onEnable() {
        removeBlocks();

        getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskTimer(this, this::purge, 20L, getConfig().getInt("SecondsToRemoveAllBlocks") * 20L);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (World w : Bukkit.getWorlds()) {
                for (Chunk c : w.getLoadedChunks()) {
                    for (Entity entity : c.getEntities()) {
                        if (entity instanceof EnderCrystal) {
                            entity.remove();
                        }
                    }
                }
            }
        }, 0L, getConfig().getInt("SecondsToRemoveCrystal") * 20L);


    }

    @Override
    public void onDisable() {
        purge();
    }

    private void removeBlocks() { // works ig
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
    // --purge all blocks in a list--
    private void purge() {
        log.info("Starting purge, this can take a few seconds...");
        try {
            for (Block b : toRemove) {
                if (b != null && b.getType() != Material.AIR) {
                    b.setType(Material.AIR);
                }
            }
            toRemove.clear();
            log.info("Purge complete!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
