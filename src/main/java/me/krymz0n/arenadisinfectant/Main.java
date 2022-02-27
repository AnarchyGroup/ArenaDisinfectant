package me.krymz0n.arenadisinfectant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

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
}
