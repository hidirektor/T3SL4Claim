package me.t3sl4.claim;

import me.t3sl4.claim.listeners.PlayerListener;
import me.t3sl4.claim.skript.SkriptLoader;
import me.t3sl4.claim.util.ClaimUtil;
import me.t3sl4.claim.commands.ClaimCommand;
import me.t3sl4.claim.util.MessageUtil;
import me.t3sl4.claim.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.t3sl4.claim.types.ClaimTypeManager;
import me.t3sl4.claim.listeners.InteractListener;
import me.t3sl4.claim.listeners.InventoryClick;
import me.t3sl4.claim.scheduler.RemovingRunnable;
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
        Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
        Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
        Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
        Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
        Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
        Bukkit.getConsoleSender().sendMessage("    ");
        manager.setup(this);
        if(getServer().getPluginManager().getPlugin("HolographicDisplays")!=null && setupEconomy()) {
            getLogger().info("-------------------------------------------------------------------------");
            getLogger().info("Ekonomi Sistemi Aktif!");
            getLogger().info("Hologram Sistemi Aktif!");
            getLogger().info("-------------------------------------------------------------------------");
        } else {
            getLogger().info("-------------------------------------------------------------------------");
            getLogger().severe("[HATA] Ekonomi Sistemi Aktif Edilemedi.");
            getLogger().severe("[HATA] Hologram Sistemi Aktif Edilemedi.");
            getLogger().severe("Gerekli Eklentiler: Vault + HolographicDisplays.");
            getLogger().info("-------------------------------------------------------------------------");
            getServer().getPluginManager().disablePlugin(this);
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
        MessageUtil.loadMessages();
    }

    public static SettingsManager getManager(){
        return manager;
    }
    
    private void registerListeners() {
    	getServer().getPluginManager().registerEvents(new InteractListener(), this);
    	getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
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
