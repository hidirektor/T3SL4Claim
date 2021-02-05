package me.t3sl4.claim.gui.claimblock;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClaimBlockGUI {

    static Inventory claimblockinv;

    public ClaimBlockGUI() {
        claimblockinv = Bukkit.createInventory(null, 9* MessageUtil.CLAIMBLOCKGUI_SIZE, MessageUtil.CLAIMBLOCKGUI_NAME);
        List<ClaimBlockGUIItem> reloadCGI = new ArrayList<>();
        for(ClaimBlockGUIItem cgi: ClaimBlockGUIItem.claimBlockGUIItems) {
            claimblockinv.setItem(cgi.getSlot(), cgi.getItem());
        }
        if (!reloadCGI.isEmpty()) {
            claimblockinv = Bukkit.createInventory(null, 9*MessageUtil.CLAIMBLOCKGUI_SIZE, MessageUtil.CLAIMBLOCKGUI_NAME);
            ClaimBlockGUIItem.claimBlockGUIItems.clear();
            for(String str: T3SL4Claim.getManager().getClaimBlockGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
                new ClaimBlockGUIItem(T3SL4Claim.getManager().getGUIConfig(), str, T3SL4Claim.getManager().getConfig());
            }
            loadItems();
        }
    }

    private void loadItems() {
        for(ClaimBlockGUIItem cgi: ClaimBlockGUIItem.claimBlockGUIItems) {
            claimblockinv.setItem(cgi.getSlot(), cgi.getItem());
        }
    }

    public static Inventory getClaimBlockMenuInventory(){
        return claimblockinv;
    }

    public static ItemStack getItem(int slot){
        return claimblockinv.getItem(slot);
    }
}
