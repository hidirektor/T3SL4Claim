package me.t3sl4.claim.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class ClaimType {

	public static List<ClaimType> claimTypes = new ArrayList<ClaimType>();
	
	private String name;
	private String type_name;
	
	public ClaimType(String name, String type_name) {
		this.name = name;
		this.type_name = type_name;
		claimTypes.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public String getTypeName() {
		return type_name;
	}
	
	public static ClaimType valueOf(String type_name) {
		ClaimType type = null;
		for(ClaimType ctype : claimTypes) {
			if(ctype.getTypeName().equalsIgnoreCase(type_name)) {
				type = ctype;
				break;
			}
		}
		return type;
	}

	public static boolean containsType(String typename) {
		for(ClaimType ctype : claimTypes) {
			if(ctype.getTypeName().equalsIgnoreCase(typename)) {
				return true;
			}
		}
		return false;
	}
	
	public abstract int get(Player p);
	public abstract int get(String name);
	public abstract void set(Player p, int value);
	public abstract void set(String name, int value);
	public abstract void add(Player p, int value);
	public abstract void add(String name, int value);
	public abstract void remove(Player p, int value);
	public abstract void remove(String name, int value);
}
