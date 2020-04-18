package com.infumia.t3sl4.claim.claimtypes;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.infumia.t3sl4.claim.T3SL4Claim;
import com.infumia.t3sl4.claim.util.ClaimTYPE;
import me.java9.kredi.KrediAPI;

import java.util.List;

public class ClaimTypeManager {
	public static void register(ClaimTYPE type) {
		ClaimTYPE.claimTypes.add(type);
	}

	public static void unregister(String typeName) {
		ClaimTYPE type = null;
		for (ClaimTYPE claimTYPE: ClaimTYPE.claimTypes) {
			if (claimTYPE.getTypeName().equalsIgnoreCase(typeName)) {
				type = claimTYPE;
				break;
			}
		}
		if (type!=null) {
			ClaimTYPE.claimTypes.remove(type);
		}
	}

	public static List<ClaimTYPE> getClaimTypes() {
		return ClaimTYPE.claimTypes;
	}

	public static void load() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Kredi")!=null) {
			register(new ClaimTYPE("Kredi", "kredi") {
				@Override
				public void set(String name, int value) {
					Player p = Bukkit.getPlayer(name);
					if (p != null) {
						KrediAPI.setKredi(p.getUniqueId().toString(), value);
					}
				}
		/*if (Bukkit.getServer().getPluginManager().getPlugin("YeniKredi")!= null) {
			register(new ClaimTYPE("Kredi", "kredi")) {
				@Override
				public void set(String name, int value) {
					Player p = Bukkit.getPlayer(name);
					if (p != null) {
						YeniKrediAPI.setKredi(p.getUniqueId().toString(), value);
					}
						}
					}
				}*/

				@Override
				public void set(Player p, int value) {
					KrediAPI.setKredi(p.getUniqueId().toString(), value);
				}

				@Override
				public int get(String name) {
					Player p = Bukkit.getPlayer(name);
					if (p != null) {
						return KrediAPI.getKredi(p.getUniqueId().toString());
					}
					return 0;
				}

				@Override
				public int get(Player p) {
					return KrediAPI.getKredi(p.getUniqueId().toString());
				}

				@Override
				public void add(String name, int value) {
					Player p = Bukkit.getPlayer(name);
					if (p != null) {
						KrediAPI.addKredi(p.getUniqueId().toString(), value);
					}
				}

				@Override
				public void add(Player p, int value) {
					KrediAPI.addKredi(p.getUniqueId().toString(), value);
				}

				@Override
				public void remove(Player p, int value) {
					KrediAPI.removeKredi(p.getUniqueId().toString(), value);
				}

				@Override
				public void remove(String name, int value) {
					Player p = Bukkit.getPlayer(name);
					if (p != null) {
						KrediAPI.removeKredi(p.getUniqueId().toString(), value);
					}
				}
			});
		}

		register(new ClaimTYPE("TL", "TL") {
			@SuppressWarnings("deprecation")
			@Override
			public void set(String name, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.withdrawPlayer(name, eco.getBalance(name));
				eco.depositPlayer(name, value);
			}

			@Override
			public void set(Player p, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.withdrawPlayer(p, eco.getBalance(p));
				eco.depositPlayer(p, value);
			}

			@SuppressWarnings("deprecation")
			@Override
			public int get(String name) {
				Economy eco = T3SL4Claim.getEconomy();
				return (int) eco.getBalance(name);
			}

			@Override
			public int get(Player p) {
				Economy eco = T3SL4Claim.getEconomy();
				return (int) eco.getBalance(p);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void add(String name, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.depositPlayer(name, value);
			}

			@Override
			public void add(Player p, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.depositPlayer(p, value);
			}

			@Override
			public void remove(Player p, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.withdrawPlayer(p, value);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void remove(String name, int value) {
				Economy eco = T3SL4Claim.getEconomy();
				eco.withdrawPlayer(name, value);
			}
		});
	}
}
