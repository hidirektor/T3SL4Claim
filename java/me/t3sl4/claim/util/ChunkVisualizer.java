package me.t3sl4.claim.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChunkVisualizer {
    public static ArrayList<Player> viewers;
    public static ArrayList<Location> viewerslocs;

    static {
        ChunkVisualizer.viewers = new ArrayList<Player>();
        ChunkVisualizer.viewerslocs = new ArrayList<Location>();
    }

    public static void showChunkVisualizer(Player p) {
        final Chunk chunk = p.getLocation().getChunk();
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
                    p.sendBlockChange(corner1, MessageUtil.BORDER, (byte)0);
                }
                if (corner2.getBlock().getType() == Material.AIR) {
                    p.sendBlockChange(corner2, MessageUtil.BORDER, (byte)0);
                }
                if (corner3.getBlock().getType() == Material.AIR) {
                    p.sendBlockChange(corner3, MessageUtil.BORDER, (byte)0);
                }
                if (corner4.getBlock().getType() == Material.AIR) {
                    p.sendBlockChange(corner4, MessageUtil.BORDER, (byte)0);
                }
            }
        }
        p.sendMessage(MessageUtil.colorize(MessageUtil.CHUNK_VIEWED));
    }
}
