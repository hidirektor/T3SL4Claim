package me.t3sl4.claim.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.api.PlayerChunkClaimEvent;
import me.t3sl4.claim.api.PlayerClaimEndEvent;
import me.t3sl4.claim.api.PlayerClaimStuffAddEvent;
import me.t3sl4.claim.api.PlayerClaimStuffKickEvent;
import me.t3sl4.claim.commands.ClaimCommand;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimUtil {
	static SettingsManager manager = T3SL4Claim.getManager();
	private static Material material = XMaterial.RED_WOOL.parseMaterial();
	public static ArrayList<Chunk> chunks = new ArrayList<>();
	public static Chunk chunk;
	static int height;
	public static Hologram topLeftHolo, topRightHolo, bottomLeftHolo, bottomRightHolo;
	public static Location placeLoc;

	public static boolean isClaimed(Chunk chunk){
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

	public static boolean isPlayerClaim(Player p, Chunk chunk){
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
				manager.getData().set("Claims."+name+"."+number+".claimBlock", 1);
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
			} else if(ClaimCommand.opmod.contains(p)) {
				return true;
			} else {
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
	public static void showChunkBorders(final Chunk x, final Player p, byte data) {
		height = p.getLocation().getBlockY() + 2;
		chunks.add(x);

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

	public static void setCapital(Chunk x, Player p, String time, int claimID) {
		String name = p.getName().toLowerCase();
		Location center = x.getBlock(0, 0, 0).getLocation();
		center.setY(center.getWorld().getHighestBlockYAt(center));

		Location topLeft = x.getBlock(0, 0, 15).getLocation();
		topLeft.setY(topLeft.getWorld().getHighestBlockYAt(topLeft));
		Location topRight = x.getBlock(0, 0, 0).getLocation();
		topRight.setY(topRight.getWorld().getHighestBlockYAt(topRight));
		Location bottomLeft = x.getBlock(15, 0, 15).getLocation();
		bottomLeft.setY(bottomLeft.getWorld().getHighestBlockYAt(bottomLeft));
		Location bottomRight = x.getBlock(15, 0, -15).getLocation();
		bottomRight.setY(bottomRight.getWorld().getHighestBlockYAt(bottomRight));

		if(manager.getData().get("Claims."+name+"."+claimID+".claimBlock").equals(1)) {
			topLeft.getBlock().setType(MessageUtil.CLAIMBLOCK);
		} else if(manager.getData().get("Claims."+name+"."+claimID+".claimBlock").equals(2)) {
			topRight.getBlock().setType(MessageUtil.CLAIMBLOCK);
		} else if(manager.getData().get("Claims."+name+"."+claimID+".claimBlock").equals(3)) {
			bottomLeft.getBlock().setType(MessageUtil.CLAIMBLOCK);
		} else if(manager.getData().get("Claims."+name+"."+claimID+".claimBlock").equals(4)) {
			bottomRight.getBlock().setType(MessageUtil.CLAIMBLOCK);
		}

		//HoloLoad(p, topLeft, topRight, bottomLeft, bottomRight, time);
	}

	public static void changeCapital(Chunk x, Player p, int oldI, int newI) {
		String name = p.getName().toLowerCase();
		Location topLeft = x.getBlock(0, 0, 15).getLocation();
		topLeft.setY(topLeft.getWorld().getHighestBlockYAt(topLeft)-1);
		Location topRight = x.getBlock(0, 0, 0).getLocation();
		topRight.setY(topRight.getWorld().getHighestBlockYAt(topRight)-1);
		Location bottomLeft = x.getBlock(15, 0, 15).getLocation();
		bottomLeft.setY(bottomLeft.getWorld().getHighestBlockYAt(bottomLeft)-1);
		Location bottomRight = x.getBlock(15, 0, -15).getLocation();
		bottomRight.setY(bottomRight.getWorld().getHighestBlockYAt(bottomRight)-1);

		if(isClaimed(x) && isPlayerClaim(p, x)) {
			if(oldI == newI) {
				p.sendMessage(MessageUtil.ALREADY_RIGHT_THERE);
				return;
			}

			if(oldI == 1) {
				topLeft.getBlock().setType(Material.AIR);
			} else if(oldI == 2) {
				topRight.getBlock().setType(Material.AIR);
			} else if(oldI == 3) {
				bottomLeft.getBlock().setType(Material.AIR);
			} else if(oldI == 4) {
				bottomRight.getBlock().setType(Material.AIR);
			}

			if(newI == 1) {
				placeLoc = x.getBlock(0, 0, 15).getLocation();
				placeLoc.setY(placeLoc.getWorld().getHighestBlockYAt(placeLoc));
				placeLoc.getBlock().setType(MessageUtil.CLAIMBLOCK);
			} else if(newI == 2) {
				placeLoc = x.getBlock(0, 0, 0).getLocation();
				placeLoc.setY(placeLoc.getWorld().getHighestBlockYAt(placeLoc));
				placeLoc.getBlock().setType(MessageUtil.CLAIMBLOCK);
			} else if(newI == 3) {
				placeLoc = x.getBlock(15, 0, 15).getLocation();
				placeLoc.setY(placeLoc.getWorld().getHighestBlockYAt(placeLoc));
				placeLoc.getBlock().setType(MessageUtil.CLAIMBLOCK);
			} else if(newI == 4) {
				placeLoc = x.getBlock(15, 0, -15).getLocation();
				placeLoc.setY(placeLoc.getWorld().getHighestBlockYAt(placeLoc));
				placeLoc.getBlock().setType(MessageUtil.CLAIMBLOCK);
			}
		} else {
			p.sendMessage(MessageUtil.NOT_BELONG_YOU);
		}
		// TODO
		//Hologramları tekrar ayarla.
		//Claim süresi bittiğinde hologramlar ve claim blockları temizlenmeli
		//HoloLoad(p, topLeft, topRight, bottomLeft, bottomRight, time);
	}

	public static void HoloLoad(Player p, Location topLeft, Location topRight, Location bottomLeft, Location bottomRight, String time) {
		TextLine textLine;
		String name = p.getName();
		String holol1 = MessageUtil.HOLOLINE1.replaceAll("%claim_owner%", name);
		String holol2 = MessageUtil.HOLOLINE2.replaceAll("%end_date%", time);

		Location topLefta = topLeft.add(0, +MessageUtil.HOLOHEIGHT, 0);
		Location topRighta = topRight.add(0, +MessageUtil.HOLOHEIGHT, 0);
		Location bottomLefta = bottomLeft.add(0, +MessageUtil.HOLOHEIGHT, 0);
		Location bottomRighta = bottomRight.add(0, +MessageUtil.HOLOHEIGHT, 0);

		topLeftHolo = HologramsAPI.createHologram(T3SL4Claim.getInstance(), topLeft);
		textLine = topLeftHolo.insertTextLine(0, holol1);
		textLine = topLeftHolo.insertTextLine(1, holol2);

		topRightHolo = HologramsAPI.createHologram(T3SL4Claim.getInstance(), topRight);
		textLine = topRightHolo.insertTextLine(0, holol1);
		textLine = topRightHolo.insertTextLine(1, holol2);

		bottomLeftHolo = HologramsAPI.createHologram(T3SL4Claim.getInstance(), bottomLeft);
		textLine = bottomLeftHolo.insertTextLine(0, holol1);
		textLine = bottomLeftHolo.insertTextLine(1, holol2);

		bottomRightHolo = HologramsAPI.createHologram(T3SL4Claim.getInstance(), bottomRight);
		textLine = bottomRightHolo.insertTextLine(0, holol1);
		textLine = bottomRightHolo.insertTextLine(1, holol2);
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

	public static void stopChunksBorder(ArrayList<Chunk> chunks){
		for(Chunk x : chunks){
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
		}
	}

	public static void stopChunkBorders(Chunk x) {
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
	}

	public static Integer getClaimAmount(Player p){
		Chunk origChunk = p.getLocation().getChunk();
		int amount = 0;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				chunk = p.getWorld().getChunkAt(origChunk.getX() + x, origChunk.getZ() + z);
				if (isClaimed(chunk)) {
					amount += 1;
				}
			}
		}
		return amount;
	}

	public static Integer getClaimIDChunk(Chunk x, Player p){
		int chunkX = x.getX();
		int chunkZ = x.getZ();
		int claimID = 0;

		if(isPlayerClaim(p, x)) {
			for(String claim_owner: manager.getData().getConfigurationSection("Claims").getKeys(false)) {
				for(String claim_id: manager.getData().getConfigurationSection("Claims." + claim_owner).getKeys(false)) {
					claimID = Integer.parseInt(claim_id);
					if(manager.getData().getInt("Claims."+p+"."+claimID+".saved-loc.x") == chunkX
							&& manager.getData().getInt("Claims."+p+"."+claimID+".saved-loc.z") == chunkZ){
						return claimID;
					}
				}
			}
		} else {
			if(!isClaimed(x)) {
				int tempNumber = 1;
				while(tempNumber == 1) {
					tempNumber -=1;
					return 1;
				}
			}
		}
		return claimID;
	}

	public static ArrayList<Chunk> getClaimsClose(Player p) {
		ArrayList<Chunk> claimedChunks = new ArrayList<>();
		Chunk origChunk = p.getLocation().getChunk();

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				chunk = p.getWorld().getChunkAt(origChunk.getX() + x, origChunk.getZ() + z);
				if (isClaimed(chunk)) {
					claimedChunks.add(chunk);
				}
			}
		}
		return claimedChunks;
	}

	//gün saat dakika
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