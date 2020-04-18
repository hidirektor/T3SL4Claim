package com.infumia.t3sl4.claim.listeners;

import com.infumia.t3sl4.claim.T3SL4Claim;
import com.infumia.t3sl4.claim.util.ClaimGuiItem;
import com.infumia.t3sl4.claim.util.ClaimUtil;
import com.infumia.t3sl4.claim.util.Messages;
import com.infumia.t3sl4.claim.util.SettingsManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    Economy econ = T3SL4Claim.getEconomy();
    SettingsManager manager = SettingsManager.getInstance();
    ClaimUtil claimUtil = T3SL4Claim.getClaimUtil();


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!e.getInventory().getName().equalsIgnoreCase(Messages.GUI_NAME)) return;  
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().hasItemMeta()) return;
        if(e.getCurrentItem().getItemMeta().hasDisplayName()){
			e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            Chunk chunk = p.getLocation().getChunk();
            if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
            	p.sendMessage(Messages.CLAIM_ALREADY_CLAIMED);
            	p.closeInventory();
            	return;
            }
            for (ClaimGuiItem cgi: ClaimGuiItem.guiItems) {
            	if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(cgi.getItem().getItemMeta().getDisplayName())) {
            		if (cgi.getClaimType()==null) break;
            		if(cgi.getClaimType().get(p)>=cgi.getPrice()) {
						p.closeInventory();
						int day = cgi.getDay();
            			if(claimUtil.isPlayerClaim(p, chunk)) {
            				String time = claimUtil.getTime(chunk);
            				if(time!=null) {
            					cgi.getClaimType().remove(p, cgi.getPrice());
            					String str = claimUtil.timeAddCalculate(time, day+"/00/0000 00:00");
            					claimUtil.setTime(chunk, str);
            					p.sendMessage(Messages.CLAIM_TIME_CHANGED.replace("%time%", str));
            				}else{
            					p.sendMessage(Messages.UNKNOWN_ERROR);
            					p.closeInventory();
            				}
            			}else{
            				String time = claimUtil.timeAddCalculate(day+"/00/0000 00:00");
            				if(time!=null) {       
            					cgi.getClaimType().remove(p, cgi.getPrice());
            					claimUtil.createClaim(p, chunk, time);
            					p.sendMessage(Messages.CLAIM_SET);
            				}else{
            					p.sendMessage(Messages.UNKNOWN_ERROR);
            				}
            			}
            		} else {
            			p.closeInventory();
						p.sendMessage(Messages.HAS_NOT_VALUE.replace("%value%", cgi.getClaimType().getName()));
					}
            		break;
            	}
            }
        }
    }
}
