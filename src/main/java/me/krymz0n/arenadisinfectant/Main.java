package me.krymz0n.arenadisinfectant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {
    private final ArrayList<Block> toRemove = new ArrayList<>();

    @Override
    public void onEnable() {
        removeBlockys();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void removeBlockys() { // inefficient af,but it's a start
        int raduis = 150;
        for (int x = raduis; x >= -raduis; x--) {
            for (int y = 150; y >= 1; y--) {
                for (int z = raduis; z >= -raduis; z--) {
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
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent evt) {
        if (evt.getBlock().getLocation().getBlockY() < 151 && evt.getBlock() != null) {
            toRemove.add(evt.getBlock());
        }
    }

    private void purge() {
        for (Block b : toRemove) {
            // more
        }
    }
}
