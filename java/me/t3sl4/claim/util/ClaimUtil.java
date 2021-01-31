package me.t3sl4.claim.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.api.PlayerChunkClaimEvent;
import me.t3sl4.claim.api.PlayerClaimEndEvent;
import me.t3sl4.claim.api.PlayerClaimStuffAddEvent;
import me.t3sl4.claim.api.PlayerClaimStuffKickEvent;
import me.t3sl4.claim.commands.ClaimCommand;

import me.t3sl4.claim.versionmatch.Version;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimUtil {
	static SettingsManager manager = T3SL4Claim.getManager();
	Material material = XMaterial.RED_WOOL.parseMaterial();

	public boolean isClaimed(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return false;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getAllClaims(){
		List<String> claims = new ArrayList<>();
		if(!manager.getData().isConfigurationSection("Claims")) return claims;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				int x = manager.getData().getInt("Claims." + player + "."+claimNumber+".x");
				int z = manager.getData().getInt("Claims." + player + "."+claimNumber+".x");
				String time = manager.getData().getString("Claims." + player + "."+claimNumber+".time");
				claims.add(player+","+claimNumber+","+x+"-"+z+","+time);
			}
		}
		return claims;
	}

	public boolean isPlayerClaim(Player p, Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return false;
		if(!manager.getData().isConfigurationSection("Claims."+p.getName().toLowerCase())) return false;
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+p.getName().toLowerCase()).getKeys(false)) {
			if (manager.getData().getInt("Claims." + p.getName().toLowerCase() + "."+claimNumber+".x") == chunk.getX()
					&& manager.getData().getInt("Claims." + p.getName().toLowerCase() + "."+claimNumber+ ".z") == chunk.getZ()) {
				return true;
			}
		}
		return false;
	}

	public List<String> getPlayerClaims(Player p){
		if(!manager.getData().isConfigurationSection("Claims")) return new ArrayList<>();
		if(!manager.getData().isConfigurationSection("Claims."+p.getName().toLowerCase())) return new ArrayList<>();
		List<String> claims = new ArrayList<>();
		String name = p.getName().toLowerCase();
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+name).getKeys(false)) {
			String world = manager.getData().getString("Claims." + name + "."+claimNumber+".world");
			int x = manager.getData().getInt("Claims." + name + "."+claimNumber+".saved-loc.x");
			int z = manager.getData().getInt("Claims." + name + "."+claimNumber+".saved-loc.z");
			claims.add(MessageUtil.CLAIM + claimNumber + MessageUtil.SPLITTER + MessageUtil.WORLD + world + MessageUtil.SPACETWO + MessageUtil.X + x + MessageUtil.SPACETWO + MessageUtil.Z + z + MessageUtil.SPACETWO);
		}
		return claims;
	}

	public List<Chunk> getPlayerChunks(Player p){
		if(!manager.getData().isConfigurationSection("Claims")) return new ArrayList<>();
		if(!manager.getData().isConfigurationSection("Claims."+p.getName().toLowerCase())) return new ArrayList<>();
		List<Chunk> claims = new ArrayList<>();
		String name = p.getName().toLowerCase();
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+name).getKeys(false)) {
			int x = manager.getData().getInt("Claims." + name + "."+claimNumber+".x");
			int z = manager.getData().getInt("Claims." + name + "."+claimNumber+".z");
			claims.add(p.getWorld().getChunkAt(x,z));
		}
		return claims;
	}

	public String getTime(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return null;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					return manager.getData().getString("Claims."+player+"."+claimNumber+".time");
				}
			}
		}
		return null;
	}

	public String getPlayer(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return null;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					return player;
				}
			}
		}
		return null;
	}

	public String[] getTimeAndPlayer(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return null;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					String time = manager.getData().getString("Claims."+player+"."+claimNumber+".time");
					return new String[] {player, time};
				}
			}
		}
		return null;
	}

	public void setTime(Chunk chunk, String time){
		if(!manager.getData().isConfigurationSection("Claims")) return;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					manager.getData().set("Claims."+player+"."+claimNumber+".time", time);
					manager.saveData();
					break;
				}
			}
		}
	}

	public void createClaim(Player p, Chunk chunk, String time){
		int number = 1;
		while(true) {
			if(!manager.getData().isSet("Claims."+p.getName().toLowerCase()+"."+number)) {
				String name = p.getName().toLowerCase();
				PlayerChunkClaimEvent pcce = new PlayerChunkClaimEvent(p, chunk);
				Bukkit.getPluginManager().callEvent(pcce);
				if (pcce.isCancelled()) break;
				manager.getData().set("Claims."+name+"."+number+".world", chunk.getWorld().getName());
				manager.getData().set("Claims."+name+"."+number+".x", chunk.getX());
				manager.getData().set("Claims."+name+"."+number+".z", chunk.getZ());
				manager.getData().set("Claims."+name+"."+number+".saved-loc.x", p.getLocation().getX());
				manager.getData().set("Claims."+name+"."+number+".saved-loc.z", p.getLocation().getZ());
				manager.getData().set("Claims."+name+"."+number+".time", time);
				manager.saveData();

				break;
			}else{
				number++;
			}
		}
	}

	public void deleteClaim(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					PlayerClaimEndEvent pcee = new PlayerClaimEndEvent(Bukkit.getPlayer(player), chunk);
					Bukkit.getPluginManager().callEvent(pcee);
					if (pcee.isCancelled()) break;
					manager.getData().set("Claims."+player+"."+claimNumber, null);
					manager.saveData();

					break;
				}
			}
		}
	}

	public void addStaff(Player p, Chunk chunk, String staff){
		String name = p.getName().toLowerCase();
		if(!manager.getData().isConfigurationSection("Claims")) return;
		if(!manager.getData().isConfigurationSection("Claims."+name)) return;
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+name).getKeys(false)) {
			if (manager.getData().getInt("Claims." + name + "."+claimNumber+".x") == chunk.getX()
					&& manager.getData().getInt("Claims." + name + "."+claimNumber+ ".z") == chunk.getZ()) {
				PlayerClaimStuffAddEvent event = new PlayerClaimStuffAddEvent(p, chunk, Bukkit.getPlayer(staff));
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) break;
				List<String> staffs = getStaffs(p, chunk);
				staffs.add(staff.toLowerCase());
				manager.getData().set("Claims."+name+"."+claimNumber+".staffs", staffs);
				manager.saveData();
				break;
			}
		}
	}

	public void delStaff(Player p, Chunk chunk, String staff){
		String name = p.getName().toLowerCase();
		if(!manager.getData().isConfigurationSection("Claims")) return;
		if(!manager.getData().isConfigurationSection("Claims."+name)) return;
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+name).getKeys(false)) {
			if (manager.getData().getInt("Claims." + name + "."+claimNumber+".x") == chunk.getX()
					&& manager.getData().getInt("Claims." + name + "."+claimNumber+ ".z") == chunk.getZ()) {
				PlayerClaimStuffKickEvent pcske = new PlayerClaimStuffKickEvent(p, chunk, Bukkit.getPlayer(staff));
				Bukkit.getPluginManager().callEvent(pcske);
				if (pcske.isCancelled()) break;
				List<String> staffs = getStaffs(p, chunk);
				staffs.remove(staff.toLowerCase());
				manager.getData().set("Claims."+name+"."+claimNumber+".staffs", staffs);
				manager.saveData();
				break;
			}
		}
	}

	public boolean canBuild(Player p, Chunk chunk) {
		if (isClaimed(chunk)) {
			if (isStaffOrOwner(p, chunk)) {
				return true;
			}else {
				return false;
			}
		} else {
			return true;
		}
	}

	public List<String> getStaffs(Player p, Chunk chunk){
		String name = p.getName().toLowerCase();
		if(!manager.getData().isConfigurationSection("Claims")) return new ArrayList<>();
		if(!manager.getData().isConfigurationSection("Claims."+name)) return new ArrayList<>();
		for(String claimNumber: manager.getData().getConfigurationSection("Claims."+name).getKeys(false)) {
			if (manager.getData().getInt("Claims." + name + "."+claimNumber+".x") == chunk.getX()
					&& manager.getData().getInt("Claims." + name + "."+claimNumber+ ".z") == chunk.getZ()) {
				if(!manager.getData().isSet("Claims."+name+"."+claimNumber+".staffs")) {
					return new ArrayList<String>();
				}else{
					return manager.getData().getStringList("Claims."+name+"."+claimNumber+".staffs");
				}
			}
		}
		return new ArrayList<>();
	}

	public List<String> getStaffs(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return new ArrayList<>();
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					if(!manager.getData().isSet("Claims."+player+"."+claimNumber+".staffs")) {
						return new ArrayList<String>();
					}else{
						return manager.getData().getStringList("Claims."+player+"."+claimNumber+".staffs");
					}
				}
			}
		}
		return new ArrayList<>();
	}

	private List<String> getStaffsAndOwner(Chunk chunk){
		if(!manager.getData().isConfigurationSection("Claims")) return new ArrayList<>();
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if (manager.getData().getInt("Claims." + player + "."+claimNumber+".x") == chunk.getX()
						&& manager.getData().getInt("Claims." + player + "."+claimNumber+ ".z") == chunk.getZ()) {
					if(!manager.getData().isSet("Claims."+player+"."+claimNumber+".staffs")) {
						List<String> staffs = new ArrayList<String>();
						staffs.add(player.toLowerCase());
						return staffs;
					}else{
						List<String> staffs = manager.getData().getStringList("Claims."+player+"."+claimNumber+".staffs");
						staffs.add(player.toLowerCase());
						return staffs;
					}
				}
			}
		}
		return new ArrayList<>();
	}

	public boolean isStaffOrOwner(Player p, Chunk chunk){
		return getStaffsAndOwner(chunk).contains(p.getName().toLowerCase());
	}

	@SuppressWarnings("deprecation")
	public void showChunkBorders(final Chunk x, final Player p, byte data) {
		final int height = p.getLocation().getBlockY() + 2;

		for (int i = 0; i < 16; i++) {
			Block b1 = x.getBlock(i, height, 0);
			Block b2 = x.getBlock(0, height, i);
			Block b3 = x.getBlock(15, height, i);
			Block b4 = x.getBlock(i, height, 15);

			p.sendBlockChange(b1.getLocation(), material, data);
			p.sendBlockChange(b2.getLocation(), material, data);
			p.sendBlockChange(b3.getLocation(), material, data);
			p.sendBlockChange(b4.getLocation(), material, data);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				for (int i = 0; i < 16; i++) {
					Block b1 = x.getBlock(i, height, 0);
					Block b2 = x.getBlock(0, height, i);
					Block b3 = x.getBlock(15, height, i);
					Block b4 = x.getBlock(i, height, 15);

					b1.getState().update();
					b2.getState().update();
					b3.getState().update();
					b4.getState().update();
				}
				ClaimCommand.canInfo.remove(p);
			}
		}.runTaskLaterAsynchronously(T3SL4Claim.getInstance(), 20L*15);


	}

	public boolean isEnabledIn(World world) {
		if(MessageUtil.ENABLED_WORLDS.contains(world.getName())) {
			return true;
		} else {
			return false;
		}
	}

	public String timeAddCalculate(String target) {
		try{
			String time = getServerTime();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date = df.parse(time);
			String[] targetargs = target.split(" ");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, parseInt(targetargs[0].split("/")[0]));
			cal.add(Calendar.MONTH, parseInt(targetargs[0].split("/")[1]));
			cal.add(Calendar.YEAR, parseInt(targetargs[0].split("/")[2]));

			cal.add(Calendar.HOUR_OF_DAY, parseInt(targetargs[1].split(":")[0]));
			cal.add(Calendar.MINUTE, parseInt(targetargs[1].split(":")[1]));

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String timeAddCalculate(String one, String target) {
		try{
			String time = one;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date = df.parse(time);
			String[] targetargs = target.split(" ");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, parseInt(targetargs[0].split("/")[0]));
			cal.add(Calendar.MONTH, parseInt(targetargs[0].split("/")[1]));
			cal.add(Calendar.YEAR, parseInt(targetargs[0].split("/")[2]));

			cal.add(Calendar.HOUR_OF_DAY, parseInt(targetargs[1].split(":")[0]));
			cal.add(Calendar.MINUTE, parseInt(targetargs[1].split(":")[1]));

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getServerTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		df.format(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return df.format(cal.getTime());
	}

	public String timeRemoveCalculateStr(String target) {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date2 = df.parse(getServerTime());
			Date date1 = df.parse(target);
			long ms = date1.getTime()-date2.getTime();
			String time = msToTime(ms);
			String[] args = time.split(":");
			String calc = "";
			if(!args[0].equalsIgnoreCase("0")) {
				calc = args[0] + " gün";
			}
			if(!args[1].equalsIgnoreCase("0")) {
				calc = calc + " "+ args[1] + " saat";
			}
			if(!args[2].equalsIgnoreCase("0")) {
				calc = calc + " "+ args[2] + " dakika";
			}
			return calc;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	//g�n saat dakika
	private String msToTime(long ms) {
		long seconds = ms/1000;
		long minutes = seconds/60;
		long hours = minutes/60;
		long days = hours/24;
		return (days + ":" + hours%24 + ":" + minutes%60);
	}

	public String msToLongTime(long ms) {
		long seconds = ms/1000;
		long minutes = seconds/60;
		long hours = minutes/60;
		long days = hours/24;
		long month = days/30;
		long year = month/12;
		return (days%30 + "/" + month%12 + "/" + year + " " + hours%24 + ":" + minutes%60);
	}

	private int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}