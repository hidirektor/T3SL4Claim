package com.infumia.t3sl4.claim;

import java.io.IOException;
import java.util.logging.Logger;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.infumia.t3sl4.claim.skript.SkriptLoader;
import com.infumia.t3sl4.claim.util.ClaimUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.infumia.t3sl4.claim.claimtypes.ClaimTypeManager;
import com.infumia.t3sl4.claim.commands.ClaimCommand;
import com.infumia.t3sl4.claim.listeners.InteractListener;
import com.infumia.t3sl4.claim.listeners.InventoryClick;
import com.infumia.t3sl4.claim.scheduler.RemovingRunnable;
import com.infumia.t3sl4.claim.util.Messages;
import com.infumia.t3sl4.claim.util.SettingsManager;
import net.milkbowl.vault.economy.Economy;

public class T3SL4Claim extends JavaPlugin{

	private static SettingsManager manager = SettingsManager.getInstance();
    private static Economy economy = null;
    private static T3SL4Claim instance;
    private static ClaimUtil claimUtil;

    private SkriptLoader skriptLoader;
    
    @Override
    public void onEnable(){
        instance = this;
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ___            __                       _         ");
        Bukkit.getConsoleSender().sendMessage(" |_ _|  _ __    / _|  _   _   _ __ ___   (_)   __ _ ");
        Bukkit.getConsoleSender().sendMessage("  | |  | '_ \\  | |_  | | | | | '_ ` _ \\  | |  / _` |");
        Bukkit.getConsoleSender().sendMessage("  | |  | | | | |  _| | |_| | | | | | | | | | | (_| |");
        Bukkit.getConsoleSender().sendMessage(" |___| |_| |_| |_|    \\__,_| |_| |_| |_| |_|  \\__,_|");
        Bukkit.getConsoleSender().sendMessage("    ");
        manager.setup(this);
        if(!(setupEconomy())){
            getLogger().info("-------------------------------------------------------------------------");
            getLogger().severe("[HATA] Ekonomi Sistemi Aktif Edilemedi.");
            getLogger().info("-------------------------------------------------------------------------");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            getLogger().info("-------------------------------------------------------------------------");
            getLogger().info("Ekonomi Sistemi Aktif!");
            getLogger().info("-------------------------------------------------------------------------");
        }
        if (getServer().getPluginManager().getPlugin("Skript")!=null) {
            skriptLoader = new SkriptLoader(this);
            skriptLoader.loadSyntaxs();
        }
        claimUtil = new ClaimUtil();
        ClaimTypeManager.load();
        registerComamands();
        registerListeners();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new RemovingRunnable(),20,20*20);
        Messages.loadMessages(getConfig());
    }

    public static SettingsManager getManager(){
        return manager;
    }
    
    private void registerListeners() {
    	getServer().getPluginManager().registerEvents(new InteractListener(), this);
    	getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }
    
    private void registerComamands() {
    	getCommand("claim").setExecutor(new ClaimCommand());
    }
    
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public static Economy getEconomy(){
        return economy;
    }

    public static ClaimUtil getClaimUtil() {
        return claimUtil;
    }

    public SkriptLoader getSkriptLoader() {
        return skriptLoader;
    }

    public static T3SL4Claim getInstance() {
        if(instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

}
