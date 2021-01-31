package me.t3sl4.claim.gui;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.MessageUtil;
import me.t3sl4.claim.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUI {
	
	static Inventory inv;

    public GUI(){
        inv = Bukkit.createInventory(null, 9* MessageUtil.GUI_SIZE, MessageUtil.GUI_NAME);

        List<ClaimGUIItem> reloadCGI = new ArrayList<>();
        for(ClaimGUIItem cgi: ClaimGUIItem.guiItems) {
            if (cgi.getClaimType()==null && cgi.getTypeString()!=null) {
                reloadCGI.add(cgi);
            }
        	inv.setItem(cgi.getSlot(), cgi.getItem());
        }
        if (!reloadCGI.isEmpty()) {
            inv = Bukkit.createInventory(null, 9*MessageUtil.GUI_SIZE, MessageUtil.GUI_NAME);
            ClaimGUIItem.guiItems.clear();
            for(String str: T3SL4Claim.getManager().getGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
                new ClaimGUIItem(T3SL4Claim.getManager().getGUIConfig(), str, T3SL4Claim.getManager().getConfig());
            }
            loadItems();
        }
    }

    private void loadItems() {
        for(ClaimGUIItem cgi: ClaimGUIItem.guiItems) {
            inv.setItem(cgi.getSlot(), cgi.getItem());
        }
    }

    public static Inventory getInventory(){
    	return inv;
    }

    public static ItemStack getItem(int slot){
        return inv.getItem(slot);
    }
}
