package me.t3sl4.claim.util;

import me.t3sl4.claim.T3SL4Claim;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Gui {
	
	static Inventory inv;

    public Gui(){
        inv = Bukkit.createInventory(null, 9*Messages.GUI_SIZE, Messages.GUI_NAME);

        List<ClaimGuiItem> reloadCGI = new ArrayList<>();
        for(ClaimGuiItem cgi: ClaimGuiItem.guiItems) {
            if (cgi.getClaimType()==null && cgi.getTypeString()!=null) {
                reloadCGI.add(cgi);
            }
        	inv.setItem(cgi.getSlot(), cgi.getItem());
        }
        if (!reloadCGI.isEmpty()) {
            inv = Bukkit.createInventory(null, 9*Messages.GUI_SIZE, Messages.GUI_NAME);
            ClaimGuiItem.guiItems.clear();
            for(String str: T3SL4Claim.getManager().getConfig().getConfigurationSection("Gui.items").getKeys(false)) {
                new ClaimGuiItem(T3SL4Claim.getManager().getConfig(), str);
            }
            loadItems();
        }
    }

    private void loadItems() {
        for(ClaimGuiItem cgi: ClaimGuiItem.guiItems) {
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
