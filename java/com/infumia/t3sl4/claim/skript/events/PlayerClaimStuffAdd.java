package com.infumia.t3sl4.claim.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.infumia.t3sl4.claim.api.PlayerClaimEndEvent;
import com.infumia.t3sl4.claim.api.PlayerClaimStuffAddEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerClaimStuffAdd extends SkriptEvent {

    static {
        Skript.registerEvent("Claim Stuff Add", PlayerClaimStuffAdd.class, PlayerClaimStuffAddEvent.class, "claim stuff add")
                .description("Called when a claim stuff add.")
                .examples("on claim stuff add:")
                .since("0.1.2");
        EventValues.registerEventValue(PlayerClaimStuffAddEvent.class, Player.class, new Getter<Player, PlayerClaimStuffAddEvent>() {
            @Override
            public Player get(PlayerClaimStuffAddEvent e) {
                return e.getStuff();
            }
        }, 0);
        EventValues.registerEventValue(PlayerClaimStuffAddEvent.class, Chunk.class, new Getter<Chunk, PlayerClaimStuffAddEvent>() {
            @Override
            public Chunk get(PlayerClaimStuffAddEvent e) {
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
        return e instanceof PlayerClaimStuffAddEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "claim stuff add event";
    }
}
