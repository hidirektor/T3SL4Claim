package me.t3sl4.claim.commands;

import java.util.ArrayList;
import java.util.List;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.gui.GUI;
import me.t3sl4.claim.util.*;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {
	public static List<Player> canInfo = new ArrayList<>();
	private ClaimUtil claimUtil = T3SL4Claim.getClaimUtil();
	public static ArrayList<Player> opmod = new ArrayList<>();
	public static boolean opMode = false;
	static SettingsManager manager = SettingsManager.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439")).create()));

		if (args.length == 0) {
			if(sender instanceof Player) {
				Player hover = (Player) sender;
				if(sender.isOp() || sender.hasPermission("t3sl4claim.general")) {
					for(String s : MessageUtil.INFO) {
						sender.sendMessage(String.valueOf(s));
					}
					hover.spigot().sendMessage(msg);
				} else {
					for(String s : MessageUtil.PLAYERINFO) {
						sender.sendMessage(String.valueOf(s));
					}
					hover.spigot().sendMessage(msg);
				}
			} else {
				for(String s : MessageUtil.INFO) {
					sender.sendMessage(String.valueOf(s));
				}
			}
			return true;
		} else if (args[0].equalsIgnoreCase("menü") || args[0].equalsIgnoreCase("menu")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				new GUI();
				p.openInventory(GUI.getInventory());
				return true;
			}
		} else if (args[0].equalsIgnoreCase("bilgi") || args[0].equalsIgnoreCase("info")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				if(!canInfo.contains(p)) {
					Chunk chunk = p.getLocation().getChunk();
					if (claimUtil.isClaimed(chunk)) {
						canInfo.add(p);
						String[] arg = claimUtil.getTimeAndPlayer(chunk);
						p.sendMessage(MessageUtil.CLAIM_INFO.replace("%player%", arg[0]).replace("%time%", arg[1]));
						return true;
					} else {
						p.sendMessage(MessageUtil.UNKNOWN_CLAIM);
					}
				}
				return true;
			}
		} else if(args[0].equalsIgnoreCase("sorgu") || args[0].equalsIgnoreCase("query")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				Chunk chunk = p.getLocation().getChunk();
				if(!claimUtil.isStaffOrOwner(p, chunk)){
					p.sendMessage(MessageUtil.YOU_ARE_NOT_STAFF);
					return true;
				}
				String[] arg = claimUtil.getTimeAndPlayer(chunk);
				p.sendMessage(MessageUtil.CLAIM_INFO.replace("%player%", arg[0]).replace("%time%", arg[1]));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("ekle") || args[0].equalsIgnoreCase("add")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				if(args.length==2) {
					String staff = args[1].toLowerCase();
					Chunk chunk = p.getLocation().getChunk();
					if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
						p.sendMessage(MessageUtil.YOU_ARE_NOT_STAFF);
						return true;
					}
					List<String> staffs = claimUtil.getStaffs(chunk);
					if(!staffs.contains(staff)) {
						claimUtil.addStaff(p, chunk, staff);
						p.sendMessage(MessageUtil.STAFF_ADDED.replace("%player%", staff));
					}else{
						p.sendMessage(MessageUtil.HE_ALREADY_STAFF);
					}
				}
				return true;
			}
		} else if (args[0].equalsIgnoreCase("sil") || args[0].equalsIgnoreCase("remove")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				if(args.length==2) {
					String staff = args[1].toLowerCase();
					Chunk chunk = p.getLocation().getChunk();
					if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
						p.sendMessage(MessageUtil.YOU_ARE_NOT_STAFF);
						return true;
					}
					List<String> staffs = claimUtil.getStaffs(chunk);
					if(staffs.contains(staff)) {
						claimUtil.delStaff(p, chunk, staff);
						p.sendMessage(MessageUtil.STAFF_REMOVED.replace("%player%", staff));
					}else{
						p.sendMessage(MessageUtil.HE_NOT_STAFF);
					}
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("yetkililer") || args[0].equalsIgnoreCase("staffs")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				Chunk chunk = p.getLocation().getChunk();
				String[] warp_split = MessageUtil.STAFF_LIST.split("%");
				String joinType = warp_split[1].substring(7).replace("'", "");
				p.sendMessage(MessageUtil.colorize(MessageUtil.STAFF_LIST.replace("%"+warp_split[1]+"%", String.join(joinType, claimUtil.getStaffs(p, chunk)))));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("liste") || args[0].equalsIgnoreCase("list")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
				return false;
			} else {
				String[] warp_split = MessageUtil.CLAIM_LIST.split("%");
				String joinType = warp_split[1].substring(7).replace("'", "");
				p.sendMessage(MessageUtil.colorize(MessageUtil.CLAIM_LIST.replace("%"+warp_split[1]+"%", String.join(joinType, claimUtil.getPlayerClaims(p)))));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("chunk")) {
			if(!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
			} else {
				Player p = (Player) sender;
				if(!claimUtil.isEnabledIn(p.getWorld())) {
					p.sendMessage(MessageUtil.CLAIM_DISABLED_WORLD);
					return false;
				} else {
					T3SL4Claim.viewers.add(p);
					T3SL4Claim.viewerslocs.add(p.getLocation());
					ChunkVisualizer.showChunkVisualizer(p);
					p.sendMessage(MessageUtil.colorize(MessageUtil.CHUNK_VIEWED));
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("opmode")) {
			Player p = (Player) sender;
			if(p.isOp() || p.hasPermission("t3sl4claim.opmode")) {
				if(opMode) {
					opMode = false;
					opmod.remove(p);
					p.sendMessage(MessageUtil.OPMODE_CLOSE);
					return true;
				} else {
					opMode = false;
				}
			} else {
				p.sendMessage(MessageUtil.ERROR_CMD);
				return true;
			}
			opmod.add(p);
			opMode = true;
			p.sendMessage(MessageUtil.OPMODE_OPEN);
			return true;
		} else if (args[0].equalsIgnoreCase("reload")) {
			if(!(sender instanceof Player)) {
				manager.reloadConfig();
				MessageUtil.loadMessages();
				Bukkit.getConsoleSender().sendMessage(MessageUtil.colorize(MessageUtil.RELOAD));
			} else if(sender.isOp()) {
				manager.reloadConfig();
				MessageUtil.loadMessages();
				sender.sendMessage(MessageUtil.colorize(MessageUtil.RELOAD));
			} else {
				sender.sendMessage(MessageUtil.ERROR_CMD);
				return false;
			}
		}
		return false;
	}

}