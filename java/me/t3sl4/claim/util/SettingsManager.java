package me.t3sl4.claim.util;

import java.io.*;

import me.t3sl4.claim.gui.admin.ClaimAdminGUIItem;
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

    private FileConfiguration claimadmingui;
    private File claimadminguifile;

    public void setup(Plugin p) {
        configfile = new File(p.getDataFolder(), "config.yml");

        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        if(!configfile.exists()){
            p.saveDefaultConfig();
        }
        config = p.getConfig();


        File dataFolder = new File(p.getDataFolder() + "/data/");
        if(!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        dfile = new File(p.getDataFolder(), "/data/data.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile);

        File guiFolder = new File(p.getDataFolder() + "/gui/");
        if(!guiFolder.exists()) {
            guiFolder.mkdirs();
        }

        mainguifile = new File(p.getDataFolder(), "/gui/main.yml");
        if(!mainguifile.exists()) {
            p.saveResource("gui/main.yml", false);
        }
        maingui = YamlConfiguration.loadConfiguration(mainguifile);

        claimblockguifile = new File(p.getDataFolder(), "/gui/claimblock.yml");
        if(!claimblockguifile.exists()) {
            p.saveResource("gui/claimblock.yml", false);
        }
        claimblockgui = YamlConfiguration.loadConfiguration(claimblockguifile);

        claimadminguifile = new File(p.getDataFolder(), "/gui/claimadmin.yml");
        if(!claimadminguifile.exists()) {
            p.saveResource("gui/claimadmin.yml", false);
        }
        claimadmingui = YamlConfiguration.loadConfiguration(claimadminguifile);
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

    public FileConfiguration getClaimAdminGUIConfig() {
        return claimadmingui;
    }

    public void saveConfig() {
        try {
            config.save(configfile);
            maingui.save(mainguifile);
            claimblockgui.save(claimblockguifile);
            claimadmingui.save(claimadminguifile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Dosyalar Kaydedilemedi!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configfile);
        maingui = YamlConfiguration.loadConfiguration(mainguifile);
        claimblockgui = YamlConfiguration.loadConfiguration(claimblockguifile);
        claimadmingui = YamlConfiguration.loadConfiguration(claimadminguifile);
    }

    public void reloadGUIConfigs() {
        for(String str: getGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimGUIItem(getGUIConfig(), str, getConfig());
        }

        for(String str: getClaimBlockGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimBlockGUIItem(getClaimBlockGUIConfig(), str, getConfig());
        }

        for(String str: getClaimAdminGUIConfig().getConfigurationSection("Gui.items").getKeys(false)) {
            new ClaimAdminGUIItem(getClaimAdminGUIConfig(), str, getConfig());
        }
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}