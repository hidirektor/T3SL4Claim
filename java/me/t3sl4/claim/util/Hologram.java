package me.t3sl4.claim.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hologram {

    public Location loc;
    public String name;
    public int claim_id;
    public static ArmorStand physicalEntity;
    static ArrayList<ArmorStand> holograms = new ArrayList<>();



    public Hologram(Location loc,String name,int claim_id){
        this.claim_id = claim_id;
        this.loc = loc;
        this.name = name;
        physicalEntity = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        holograms.add(physicalEntity);
        physicalEntity.setVisible(false);
        physicalEntity.setVisible(false);
        physicalEntity.setGravity(false);
        physicalEntity.setCustomNameVisible(true);
        physicalEntity.setBasePlate(false);
        physicalEntity.setCanPickupItems(false);

        physicalEntity.setCustomName(ChatColor.YELLOW+"Claim Sahibi : "+ChatColor.WHITE+name+ChatColor.YELLOW+" Claim ID : "
                +ChatColor.WHITE+claim_id);

    }

    public static void destroy() {
        for (ArmorStand x : holograms) {

            x.remove();
        }

    }

}
