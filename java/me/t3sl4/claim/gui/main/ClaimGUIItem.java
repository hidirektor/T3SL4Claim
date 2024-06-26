package me.t3sl4.claim.gui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.t3sl4.claim.types.ClaimType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClaimGUIItem {
	public static List<ClaimGUIItem> guiItems = new ArrayList<>();
	
	private int slot;
	private ClaimType type;
	private String typeString;
	private String name;
	private Material item;
	private List<String> lore;
	private int price;

	private int day;
	
	public ClaimGUIItem(FileConfiguration fc, String selected, FileConfiguration fc2) {
		slot = fc.getInt("Gui.items."+selected+".slot");
		typeString = fc.isSet("Gui.items."+selected+".type") ? fc.getString("Gui.items."+selected+".type") : null;
		type = typeString != null ? ClaimType.valueOf(typeString) : null;
		name = fc.getString("Gui.items."+selected+".name");
		item = Material.valueOf(fc.getString("Gui.items."+selected+".item").toUpperCase(Locale.US));		
		lore = fc.getStringList("Gui.items."+selected+".lore");			
		price = type != null ? fc2.getInt("Settings.prices."+ type.getTypeName()+".value") : 0;
		day = fc.isSet("Gui.items."+selected+".day") ? fc.getInt("Gui.items."+selected+".day") : 30;
		guiItems.add(this);		
	}

	public int getSlot() {
		return slot;
	}

	public String getTypeString() {
		return typeString;
	}

	public ClaimType getClaimType() {
		return type;
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
	
	public int getPrice() {
		return price;
	}
	
	public ItemStack getItem() {
		ItemStack is = new ItemStack(getMaterial());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(colorize(getName()));
		im.setLore(coloredList(getLore()));
		is.setItemMeta(im);
		return is;
	}

	public int getDay() {
		return day;
	}

	private List<String> coloredList(List<String> list) {
		List<String> rlist = new ArrayList<String>();
		for(String str: list) {
			str = str.replace("&", "§");
			if (str.contains("%ücret%")) str = str.replace("%ücret%", getPrice()+"");
			rlist.add(str);
		}
		return rlist;
	}

	private String colorize(String str) {
		str = str.replace("&", "§");
		return str;
    }
	
}
