package me.t3sl4.claim.gui.admin;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClaimAdminGUI {

    static Inventory claimadmininv;

    public ClaimAdminGUI() {
        claimadmininv = Bukkit.createInventory(null, 9* MessageUtil.CLAIMADMIN_SIZE, MessageUtil.CLAIMADMIN_NAME);
        List<ClaimAdminGUIItem> reloadCGI = new ArrayList<>();
        for(ClaimAdminGUIItem cgi: ClaimAdminGUIItem.claimAdminGUIItems) {
            claimadmininv.setItem(cgi.getSlot(), cgi.getItem());
        }
        if (!reloadCGI.isEmpty()) {
            claimadmininv = Bukkit.createInventory(null, 9*MessageUtil.CLAIMADMIN_SIZE, MessageUtil.CLAIMADMIN_NAME);
            ClaimAdminGUIItem.claimAdminGUIItems.clear();
            for(String str: T3SL4Claim.getManager().getClaimBlockGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
                new ClaimAdminGUIItem(T3SL4Claim.getManager().getGUIConfig(), str, T3SL4Claim.getManager().getConfig());
            }
            loadItems();
        }
    }

    private void loadItems() {
        for(ClaimAdminGUIItem cgi: ClaimAdminGUIItem.claimAdminGUIItems) {
            claimadmininv.setItem(cgi.getSlot(), cgi.getItem());
        }
    }

    public static Inventory getClaimBlockMenuInventory(){
        return claimadmininv;
    }

    public static ItemStack getItem(int slot){
        return claimadmininv.getItem(slot);
    }
}
