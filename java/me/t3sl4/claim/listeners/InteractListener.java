package me.t3sl4.claim.listeners;


import me.t3sl4.claim.T3SL4Claim;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
    	Block block = e.getBlock();
    	Player p = e.getPlayer();
        
    	Chunk chunk = block.getChunk();

    	if(!T3SL4Claim.getClaimUtil().canBuild(p, chunk)) {
    		e.setCancelled(true);
    	}
	}
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
    	if(e.getAction() == Action.LEFT_CLICK_AIR){
        	return;
        }
        Block block = e.getClickedBlock();
        if(block == null){
        	return;
        }
        Player p = e.getPlayer();
        Chunk chunk = block.getChunk();

        if(!T3SL4Claim.getClaimUtil().canBuild(p, chunk)) {
        	e.setCancelled(true);
        }
	}
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
    	Block block = e.getBlock();
        Player p = e.getPlayer();
        Chunk chunk = block.getChunk();

        if(!T3SL4Claim.getClaimUtil().canBuild(p, chunk)) {
            e.setCancelled(true);
        }
	}
}
