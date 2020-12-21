package me.t3sl4.claim.commands;

import java.util.ArrayList;
import java.util.List;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.ClaimUtil;
import me.t3sl4.claim.util.Gui;
import me.t3sl4.claim.util.Messages;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {

	public static List<Player> canInfo = new ArrayList<>();
	private ClaimUtil claimUtil = T3SL4Claim.getClaimUtil();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length == 0) {
			if(sender.isOp()) {
				sender.sendMessage(Messages.COMMAND_HELP1);
				sender.sendMessage(Messages.COMMAND_HELPRELOAD);
				sender.sendMessage(Messages.COMMAND_HELP2);
				sender.sendMessage(Messages.COMMAND_HELP3);
				sender.sendMessage(Messages.COMMAND_HELP4);
				sender.sendMessage(Messages.COMMAND_HELP5);
				sender.sendMessage(Messages.COMMAND_HELP6);
				sender.sendMessage(Messages.COMMAND_HELP7);
				sender.sendMessage(Messages.COMMAND_HELP8);
				sender.sendMessage(Messages.COMMAND_HELP9);
			} else if(!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP1);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELPRELOAD);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP2);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP3);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP4);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP5);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP6);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP7);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP8);
				Bukkit.getConsoleSender().sendMessage(Messages.COMMAND_HELP9);
			} else {
				sender.sendMessage(Messages.COMMAND_HELP1);
				sender.sendMessage(Messages.COMMAND_HELP2);
				sender.sendMessage(Messages.COMMAND_HELP3);
				sender.sendMessage(Messages.COMMAND_HELP4);
				sender.sendMessage(Messages.COMMAND_HELP5);
				sender.sendMessage(Messages.COMMAND_HELP6);
				sender.sendMessage(Messages.COMMAND_HELP7);
				sender.sendMessage(Messages.COMMAND_HELP8);
				sender.sendMessage(Messages.COMMAND_HELP9);
			}
			return true;
		} else if (args[0].equalsIgnoreCase("menü") || args[0].equalsIgnoreCase("menu")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				new Gui();
				p.openInventory(Gui.getInventory());
				return true;
			}
		} else if (args[0].equalsIgnoreCase("bilgi") || args[0].equalsIgnoreCase("info")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				if(!canInfo.contains(p)) {
					Chunk chunk = p.getLocation().getChunk();
					if (claimUtil.isClaimed(chunk)) {
						canInfo.add(p);
						claimUtil.showChunkBorders(chunk,p,(byte) 14);
						String[] arg = claimUtil.getTimeAndPlayer(chunk);
						p.sendMessage(Messages.CLAIM_INFO.replace("%player%", arg[0]).replace("%time%", arg[1]));
						return true;
					} else {
						p.sendMessage(Messages.UNKNOWN_CLAIM);
					}
				}
				return true;
			}
		} else if(args[0].equalsIgnoreCase("sorgu") || args[0].equalsIgnoreCase("query")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				Chunk chunk = p.getLocation().getChunk();
				if(!claimUtil.isStaffOrOwner(p, chunk)){
					p.sendMessage(Messages.YOU_ARE_NOT_STAFF);
					return true;
				}
				String[] arg = claimUtil.getTimeAndPlayer(chunk);
				p.sendMessage(Messages.CLAIM_INFO.replace("%player%", arg[0]).replace("%time%", arg[1]));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("ekle") || args[0].equalsIgnoreCase("add")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				if(args.length==2) {
					String staff = args[1].toLowerCase();
					Chunk chunk = p.getLocation().getChunk();
					if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
						p.sendMessage(Messages.YOU_ARE_NOT_STAFF);
						return true;
					}
					List<String> staffs = claimUtil.getStaffs(chunk);
					if(!staffs.contains(staff)) {
						claimUtil.addStaff(p, chunk, staff);
						p.sendMessage(Messages.STAFF_ADDED.replace("%player%", staff));
					}else{
						p.sendMessage(Messages.HE_ALREADY_STAFF);
					}
				}
				return true;
			}
		} else if (args[0].equalsIgnoreCase("sil") || args[0].equalsIgnoreCase("remove")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				if(args.length==2) {
					String staff = args[1].toLowerCase();
					Chunk chunk = p.getLocation().getChunk();
					if(claimUtil.isClaimed(chunk) && !claimUtil.isPlayerClaim(p, chunk)) {
						p.sendMessage(Messages.YOU_ARE_NOT_STAFF);
						return true;
					}
					List<String> staffs = claimUtil.getStaffs(chunk);
					if(staffs.contains(staff)) {
						claimUtil.delStaff(p, chunk, staff);
						p.sendMessage(Messages.STAFF_REMOVED.replace("%player%", staff));
					}else{
						p.sendMessage(Messages.HE_NOT_STAFF);
					}
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("yetkililer") || args[0].equalsIgnoreCase("staffs")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				Chunk chunk = p.getLocation().getChunk();
				String[] warp_split = Messages.STAFF_LIST.split("%");
				String joinType = warp_split[1].substring(7).replace("'", "");
				p.sendMessage(Messages.colorize(Messages.STAFF_LIST.replace("%"+warp_split[1]+"%", String.join(joinType, claimUtil.getStaffs(p, chunk)))));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("liste") || args[0].equalsIgnoreCase("list")) {
			Player p = (Player) sender;
			if(!claimUtil.isEnabledIn(p.getWorld())) {
				p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
				return false;
			} else if (!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
				return false;
			} else {
				String[] warp_split = Messages.CLAIM_LIST.split("%");
				String joinType = warp_split[1].substring(7).replace("'", "");
				p.sendMessage(Messages.colorize(Messages.CLAIM_LIST.replace("%"+warp_split[1]+"%", String.join(joinType, claimUtil.getPlayerClaims(p)))));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("chunk")) {
			if(!(sender instanceof Player)) {
				Bukkit.getConsoleSender().sendMessage(Messages.CONSOLE);
			} else {
				Player p = (Player) sender;
				if(!claimUtil.isEnabledIn(p.getWorld())) {
					p.sendMessage(Messages.CLAIM_DISABLED_WORLD);
					return false;
				} else {
					T3SL4Claim.viewers.add(p);
					T3SL4Claim.viewerslocs.add(p.getLocation());
					final Chunk chunk = p.getLocation().getChunk();
					Location corner1 = chunk.getBlock(0, 0, 0).getLocation();
					Location corner2 = chunk.getBlock(15, 0, 0).getLocation();
					Location corner3 = chunk.getBlock(0, 0, 15).getLocation();
					Location corner4 = chunk.getBlock(15, 0, 15).getLocation();
					int i = 0;
					int i2 = 0;
					for (i = 0; i < 127; ++i) {
						for (i2 = 0; i2 < 15; ++i2) {
							corner1 = chunk.getBlock(i2, i, 0).getLocation();
							corner2 = chunk.getBlock(15, i, i2).getLocation();
							corner3 = chunk.getBlock(15 - i2, i, 15).getLocation();
							corner4 = chunk.getBlock(0, i, 15 - i2).getLocation();
							if (corner1.getBlock().getType() == Material.AIR) {
								p.sendBlockChange(corner1, Material.GLASS, (byte)0);
							}
							if (corner2.getBlock().getType() == Material.AIR) {
								p.sendBlockChange(corner2, Material.GLASS, (byte)0);
							}
							if (corner3.getBlock().getType() == Material.AIR) {
								p.sendBlockChange(corner3, Material.GLASS, (byte)0);
							}
							if (corner4.getBlock().getType() == Material.AIR) {
								p.sendBlockChange(corner4, Material.GLASS, (byte)0);
							}
						}
					}
					p.sendMessage(Messages.colorize(Messages.CHUNK_VIEWED));
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("reload")) {
			if(!(sender instanceof Player)) {
				Messages.loadMessages(T3SL4Claim.getInstance().getConfig());
				Bukkit.getConsoleSender().sendMessage(Messages.colorize(Messages.RELOAD));
			} else if(sender.isOp()) {
				Messages.loadMessages(T3SL4Claim.getInstance().getConfig());
				sender.sendMessage(Messages.colorize(Messages.RELOAD));
			} else {
				sender.sendMessage(Messages.ERROR_CMD);
				return false;
			}
		}
		return false;
	}

}