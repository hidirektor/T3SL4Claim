package me.t3sl4.claim.listeners;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.gui.claimblock.ClaimBlockGUI;
import me.t3sl4.claim.gui.main.ClaimGUIItem;
import me.t3sl4.claim.util.ChunkVisualizer;
import me.t3sl4.claim.util.ClaimUtil;
import me.t3sl4.claim.util.MessageUtil;
import me.t3sl4.claim.util.SettingsManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    Economy econ = T3SL4Claim.getEconomy();
    SettingsManager manager = SettingsManager.getInstance();
    ClaimUtil claimUtil = T3SL4Claim.getClaimUtil();
    String menuName;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
		String version = Bukkit.getVersion();
		if(version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
			menuName = e.getInventory().getName();
		} else {
			menuName = e.getView().getTitle();
		}
		if(menuName.equalsIgnoreCase(MessageUtil.GUI_NAME)) {
			if(e.getCurrentItem() == null) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(e.getCurrentItem().getItemMeta().hasDisplayName()){
				e.setCancelled(true);
				Player p = (Player) e.getWhoClicked();
				String name = p.getName().toLowerCase();
				Chunk chunk = p.getLocation().getChunk();
				if(e.getSlot() == MessageUtil.SLOT) {
					p.closeInventory();
					ChunkVisualizer.viewers.add(p);
					ChunkVisualizer.viewerslocs.add(p.getLocation());
					ChunkVisualizer.showChunkVisualizer(p);
					return;
				} else if(e.getSlot() == MessageUtil.CLAIMBLOCKSLOT) {
					p.closeInventory();
					new ClaimBlockGUI();
					p.openInventory(ClaimBlockGUI.getClaimBlockMenuInventory());
					return;
				}
				if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
					p.sendMessage(MessageUtil.CLAIM_ALREADY_CLAIMED);
					p.closeInventory();
					return;
				}
				for (ClaimGUIItem cgi: ClaimGUIItem.guiItems) {
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
									p.sendMessage(MessageUtil.CLAIM_TIME_CHANGED.replace("%time%", str));
									ClaimUtil.claimBlockHolo.clearLines();
									for(int i=0; i<MessageUtil.HOLOLINES.size(); i++) {
										ClaimUtil.claimBlockHolo.insertTextLine(i, MessageUtil.HOLOLINES.get(i).replaceAll("%claim_owner%", p.getName()).replaceAll("%end_date%", str));
									}
								}else{
									p.sendMessage(MessageUtil.UNKNOWN_ERROR);
									p.closeInventory();
								}
							}else{
								String time = claimUtil.timeAddCalculate(day+"/00/0000 00:00");
								if(time!=null) {
									cgi.getClaimType().remove(p, cgi.getPrice());
									claimUtil.createClaim(p, chunk, time);
									claimUtil.setCapital(chunk, p, time);
									p.sendMessage(MessageUtil.CLAIM_SET);
								}else{
									p.sendMessage(MessageUtil.UNKNOWN_ERROR);
								}
							}
						} else {
							p.closeInventory();
							p.sendMessage(MessageUtil.HAS_NOT_VALUE.replace("%value%", cgi.getClaimType().getName()));
						}
						break;
					}
				}
			}
		} else if(menuName.equalsIgnoreCase(MessageUtil.CLAIMBLOCKGUI_NAME)) {
			if(e.getCurrentItem() == null) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(e.getCurrentItem().getItemMeta().hasDisplayName()) {
				e.setCancelled(true);
				Player p = (Player) e.getWhoClicked();
				String name = p.getName().toLowerCase();
				Chunk chunk = p.getLocation().getChunk();
				int claimID = ClaimUtil.getClaimIDChunk(chunk, p);
				int oldI = manager.getData().getInt("Claims." + name + "." + claimID + ".claimBlock");
				if(e.getSlot() == MessageUtil.TOPLEFTSLOT) {
					p.closeInventory();
					manager.getData().set("Claims."+name+"."+claimID+".claimBlock", 1);
					manager.saveData();
					ClaimUtil.changeCapital(chunk, p, oldI, 1);
					ClaimUtil.claimBlockHolo.teleport(ClaimUtil.claimBlockLoc);
				} else if(e.getSlot() == MessageUtil.TOPRIGHTSLOT) {
					p.closeInventory();
					manager.getData().set("Claims."+name+"."+claimID+".claimBlock", 2);
					manager.saveData();
					ClaimUtil.changeCapital(chunk, p, oldI, 2);
					ClaimUtil.claimBlockHolo.teleport(ClaimUtil.claimBlockLoc);
				} else if(e.getSlot() == MessageUtil.BOTTOMLEFTSLOT) {
					p.closeInventory();
					manager.getData().set("Claims."+name+"."+claimID+".claimBlock", 3);
					manager.saveData();
					ClaimUtil.changeCapital(chunk, p, oldI, 3);
					ClaimUtil.claimBlockHolo.teleport(ClaimUtil.claimBlockLoc);
				} else if(e.getSlot() == MessageUtil.BOTTOMRIGHTSLOT) {
					p.closeInventory();
					manager.getData().set("Claims."+name+"."+claimID+".claimBlock", 4);
					manager.saveData();
					ClaimUtil.changeCapital(chunk, p, oldI, 4);
				}
			}
		} else if(menuName.equalsIgnoreCase(MessageUtil.CLAIMADMIN_NAME)) {

		}
    }
}
