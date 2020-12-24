package me.t3sl4.claim.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.t3sl4.claim.api.PlayerClaimEndEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerChunkEnd extends SkriptEvent {

    static {
        Skript.registerEvent("Chunk Claim End", PlayerChunkEnd.class, PlayerClaimEndEvent.class, "player claim end")
                .description("Called when a chunk ended.")
                .examples("on player claim end:")
                .since("0.1.2");
        EventValues.registerEventValue(PlayerClaimEndEvent.class, Player.class, new Getter<Player, PlayerClaimEndEvent>() {
            @Override
            public Player get(PlayerClaimEndEvent e) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerClaimEndEvent.class, Chunk.class, new Getter<Chunk, PlayerClaimEndEvent>() {
            @Override
            public Chunk get(PlayerClaimEndEvent e) {
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
        return e instanceof PlayerClaimEndEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "player claim end event";
    }
}
