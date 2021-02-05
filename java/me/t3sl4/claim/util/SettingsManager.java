package me.t3sl4.claim.util;

import java.io.File;
import java.io.IOException;

import me.t3sl4.claim.gui.claimblock.ClaimBlockGUIItem;
import me.t3sl4.claim.gui.main.ClaimGUIItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {

    private SettingsManager() { }

    static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    private Plugin p;

    private FileConfiguration config;
    private File configfile;

    private FileConfiguration data;
    private File dfile;

    private FileConfiguration maingui;
    private File mainguifile;

    private FileConfiguration claimblockgui;
    private File claimblockguifile;

    public void setup(Plugin p) {
        configfile = new File(p.getDataFolder(), "config.yml");

        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        if(!configfile.exists()){
            p.saveDefaultConfig();
        }
        config = p.getConfig();


        dfile = new File(p.getDataFolder(), "data.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile);

        mainguifile = new File(p.getDataFolder(), "maingui.yml");
        if(!mainguifile.exists()) {
            p.saveResource("maingui.yml", false);
        }
        maingui = YamlConfiguration.loadConfiguration(mainguifile);

        claimblockguifile = new File(p.getDataFolder(), "claimblockgui.yml");
        if(!claimblockguifile.exists()) {
            p.saveResource("claimblockgui.yml", false);
        }
        claimblockgui = YamlConfiguration.loadConfiguration(claimblockguifile);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getGUIConfig() {
        return maingui;
    }

    public FileConfiguration getClaimBlockGUIConfig() {
        return claimblockgui;
    }

    public void saveConfig() {
        try {
            config.save(configfile);
            maingui.save(mainguifile);
            claimblockgui.save(claimblockguifile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml, maingui.yml ve claimblockgui.yml!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configfile);
        maingui = YamlConfiguration.loadConfiguration(mainguifile);
        claimblockgui = YamlConfiguration.loadConfiguration(claimblockguifile);
    }

    public void reloadGUIConfigs() {
        for(String str: getGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimGUIItem(getGUIConfig(), str, getConfig());
        }

        for(String str: getClaimBlockGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimBlockGUIItem(getClaimBlockGUIConfig(), str, getConfig());
        }
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}