package com.infumia.t3sl4.claim.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.infumia.t3sl4.claim.T3SL4Claim;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SkriptLoader {
    private T3SL4Claim instance;
    private SkriptAddon addonInstance;

    public SkriptLoader(T3SL4Claim instance) {
        this.instance = instance;
    }

    public void loadSyntaxs() {
        try {
            getAddonInstance().loadClasses("com.infumia.t3sl4.claim.skript", "effects", "expressions", "events");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T3SL4Claim getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        if(addonInstance == null) {
            addonInstance = Skript.registerAddon(getInstance());
        }
        return addonInstance;
    }

    public String replaceVariable(String variable, Player player) {
        variable = variable.replace("{", "").replace("}", "").replace("'", "")
                .replace("%player%", player.getName().toLowerCase())
                .replace("%the player%", player.getName().toLowerCase())
                .replace("%player's uuid%", player.getUniqueId().toString().toLowerCase())
                .replace("%uuid of player%", player.getUniqueId().toString().toLowerCase())
                .replace("%the UUID of the player%", player.getUniqueId().toString().toLowerCase());
        return variable;
    }

    public String replaceVariable(String variable, String value) {
        variable = variable.replace("{", "").replace("}", "").replace("'", "")
                .replace("%player%", value.toLowerCase())
                .replace("%the player%", value.toLowerCase())
                .replace("%player's uuid%", value.toLowerCase())
                .replace("%uuid of player%", value.toLowerCase())
                .replace("%the UUID of the player%", value.toLowerCase());
        return variable;
    }

    public int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
