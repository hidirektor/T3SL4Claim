package com.infumia.t3sl4.claim.api;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerClaimStuffKickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player owner;
    private Player stuff;
    private Chunk chunk;
    private boolean isCanceled = false;

    public PlayerClaimStuffKickEvent(Player owner, Chunk chunk, Player stuff) {
        this.owner = owner;
        this.stuff = stuff;
        this.chunk = chunk;
    }

    public Player getOwner() {
        return owner;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public Player getStuff() {
        return stuff;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCanceled = b;
    }
}
