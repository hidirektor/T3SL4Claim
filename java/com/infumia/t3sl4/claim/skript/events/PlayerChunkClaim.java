package com.infumia.t3sl4.claim.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.infumia.t3sl4.claim.api.PlayerChunkClaimEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerChunkClaim extends SkriptEvent {

    static {
        Skript.registerEvent("Chunk Claim", PlayerChunkClaim.class, PlayerChunkClaimEvent.class, "player chunk claim")
                .description("Called when a chunk claimed.")
                .examples("on player chunk claim:")
                .since("0.1.2");
        EventValues.registerEventValue(PlayerChunkClaimEvent.class, Player.class, new Getter<Player, PlayerChunkClaimEvent>() {
            @Override
            public Player get(PlayerChunkClaimEvent e) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerChunkClaimEvent.class, Chunk.class, new Getter<Chunk, PlayerChunkClaimEvent>() {
            @Override
            public Chunk get(PlayerChunkClaimEvent e) {
                return e.getChunk();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof PlayerChunkClaimEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "player chunk claim event";
    }
}
