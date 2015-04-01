package net.minelink.lavasponge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LavaSponge extends JavaPlugin implements Listener {

    private static final int radius = 3;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void clearLava(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.SPONGE) return;

        Location loc = block.getLocation();

        for (int x = -radius; x < radius; ++x) {
            for (int y = -radius; y < radius; ++y) {
                for (int z = -radius; z < radius; ++z) {
                    Block b = loc.getWorld().getBlockAt(loc.clone().add(x, y, z));
                    if (b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_LAVA) {
                        b.setType(Material.AIR);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockLava(BlockFromToEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.LAVA &&
                block.getType() != Material.STATIONARY_LAVA) return;

        Location loc = block.getLocation();

        for (int x = -radius; x < radius; ++x) {
            for (int y = -radius; y < radius; ++y) {
                for (int z = -radius; z < radius; ++z) {
                    Block b = loc.getWorld().getBlockAt(loc.clone().add(x, y, z));
                    if (b.getType() == Material.SPONGE) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

}
