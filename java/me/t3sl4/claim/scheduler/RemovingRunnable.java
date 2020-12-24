package me.t3sl4.claim.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.t3sl4.claim.api.PlayerClaimEndEvent;
import me.t3sl4.claim.util.MessageUtil;
import me.t3sl4.claim.util.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class RemovingRunnable implements Runnable {

	SettingsManager manager = SettingsManager.getInstance();

	private boolean isCancelled = false;
	@Override
	public void run() {
		if(!manager.getData().isConfigurationSection("Claims")) return;
		for(String player: manager.getData().getConfigurationSection("Claims").getKeys(false)){
			if(!manager.getData().isConfigurationSection("Claims."+player)) continue;
			if (isCancelled) {
				isCancelled = false;
				break;
			}
			for(String claimNumber: manager.getData().getConfigurationSection("Claims."+player).getKeys(false)) {
				if(timeIsFinish(manager.getData().getString("Claims."+player+"."+claimNumber+".time"))){
					int x = manager.getData().getInt("Claims."+player+"."+claimNumber+".x");
					int z = manager.getData().getInt("Claims."+player+"."+claimNumber+".z");
					Chunk chunk = Bukkit.getPlayer(player).getWorld().getChunkAt(x,z);
					PlayerClaimEndEvent pcee = new PlayerClaimEndEvent(Bukkit.getPlayer(player), chunk);
					Bukkit.getPluginManager().callEvent(pcee);
					if (pcee.isCancelled()) {
						isCancelled = true;
						break;
					}
					Bukkit.broadcastMessage(MessageUtil.CLAIM_END.replace("%player%", player).replace("%claim%", claimNumber).replace("%location%", "x: "+x*16+", z: "+z*16));
					manager.getData().set("Claims."+player+"."+claimNumber, null);
					manager.saveData();
				}
			}
		}
	}

	private boolean timeIsFinish(String target) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(target);
			if(date.getTime()-new Date().getTime()<=0) {
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}