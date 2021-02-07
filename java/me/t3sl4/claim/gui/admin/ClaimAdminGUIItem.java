package me.t3sl4.claim.gui.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClaimAdminGUIItem {
    public static List<ClaimAdminGUIItem> claimAdminGUIItems = new ArrayList<>();

    private int slot;
    private String name;
    private Material item;
    private List<String> lore;

    public ClaimAdminGUIItem(FileConfiguration fc, String selected, FileConfiguration fc2) {
        slot = fc.getInt("Gui.items."+selected+".slot");
        name = fc.getString("Gui.items."+selected+".name");
        item = Material.valueOf(fc.getString("Gui.items."+selected+".item").toUpperCase(Locale.US));
        lore = fc.getStringList("Gui.items."+selected+".lore");
        claimAdminGUIItems.add(this);
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return item;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemStack getItem() {
        ItemStack is = new ItemStack(getMaterial());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(colorize(getName()));
        im.setLore(coloredList(getLore()));
        is.setItemMeta(im);
        return is;
    }

    private List<String> coloredList(List<String> list) {
        List<String> rlist = new ArrayList<String>();
        for(String str: list) {
            str = str.replace("&", "§");
            rlist.add(str);
        }
        return rlist;
    }

    private String colorize(String str) {
        str = str.replace("&", "§");
        return str;
    }

}
