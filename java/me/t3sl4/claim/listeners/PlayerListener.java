package me.t3sl4.claim.listeners;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.ChunkVisualizer;
import me.t3sl4.claim.util.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (ChunkVisualizer.viewers.contains(player)) {
            final Location loc = player.getLocation();
            final World world = loc.getWorld();
            final int index = ChunkVisualizer.viewers.indexOf(player);
            final Location prevloc = ChunkVisualizer.viewerslocs.get(index);
            final Chunk chunk = prevloc.getChunk();
            if (loc.getX() != ChunkVisualizer.viewerslocs.get(index).getX() || loc.getY() != ChunkVisualizer.viewerslocs.get(index).getY() || loc.getZ() != ChunkVisualizer.viewerslocs.get(index).getZ()) {
                Location corner1 = chunk.getBlock(0, 0, 0).getLocation();
                Location corner2 = chunk.getBlock(15, 0, 0).getLocation();
                Location corner3 = chunk.getBlock(0, 0, 15).getLocation();
                Location corner4 = chunk.getBlock(15, 0, 15).getLocation();
                int i = 0;
                int i2 = 0;
                for (i = 0; i < 127; ++i) {
                    for (i2 = 0; i2 < 15; ++i2) {
                        corner1 = chunk.getBlock(i2, i, 0).getLocation();
                        corner2 = chunk.getBlock(15, i, i2).getLocation();
                        corner3 = chunk.getBlock(15 - i2, i, 15).getLocation();
                        corner4 = chunk.getBlock(0, i, 15 - i2).getLocation();
                        if (corner1.getBlock().getType() == Material.AIR) {
                            player.sendBlockChange(corner1, Material.AIR, (byte)0);
                        }
                        if (corner2.getBlock().getType() == Material.AIR) {
                            player.sendBlockChange(corner2, Material.AIR, (byte)0);
                        }
                        if (corner3.getBlock().getType() == Material.AIR) {
                            player.sendBlockChange(corner3, Material.AIR, (byte)0);
                        }
                        if (corner4.getBlock().getType() == Material.AIR) {
                            player.sendBlockChange(corner4, Material.AIR, (byte)0);
                        }
                    }
                }
                player.sendMessage(MessageUtil.colorize(MessageUtil.CHUNK_REMOVED));
                ChunkVisualizer.viewers.remove(player);
                ChunkVisualizer.viewerslocs.remove(index);
            }
        }
    }
}
