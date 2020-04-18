package com.infumia.t3sl4.claim.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.infumia.t3sl4.claim.api.PlayerClaimStuffAddEvent;
import com.infumia.t3sl4.claim.api.PlayerClaimStuffKickEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PlayerClaimStuffKick extends SkriptEvent {

    static {
        Skript.registerEvent("Claim Stuff Add", PlayerClaimStuffKick.class, PlayerClaimStuffKickEvent.class, "claim stuff kick")
                .description("Called when a claim stuff kicked.")
                .examples("on claim stuff kick:")
                .since("0.1.2");
        EventValues.registerEventValue(PlayerClaimStuffKickEvent.class, Chunk.class, new Getter<Chunk, PlayerClaimStuffKickEvent>() {
            @Override
            public Chunk get(PlayerClaimStuffKickEvent e) {
                return e.getChunk();
            }
        }, 0);
        EventValues.registerEventValue(PlayerClaimStuffKickEvent.class, Player.class, new Getter<Player, PlayerClaimStuffKickEvent>() {
            @Override
            public Player get(PlayerClaimStuffKickEvent e) {
                return e.getStuff();
            }
        }, 0);
    }


    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof PlayerClaimStuffKickEvent;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "claim stuff kick event";
    }
}
