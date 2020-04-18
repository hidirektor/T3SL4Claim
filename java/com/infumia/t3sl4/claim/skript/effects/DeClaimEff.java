package com.infumia.t3sl4.claim.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.infumia.t3sl4.claim.T3SL4Claim;
import com.infumia.t3sl4.claim.util.ClaimUtil;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Declaim")
@Examples("declaim chunk at player")

public class DeClaimEff extends Effect {
    static {
        Skript.registerEffect(DeClaimEff.class, "declaim [the [chunk]] %chunk%");
    }

    private Expression<Chunk> chunkExpr;

    @Override
    protected void execute(Event e) {
        Chunk chunk = chunkExpr.getSingle(e);
        ClaimUtil claimUtil = T3SL4Claim.getInstance().getClaimUtil();
        claimUtil.deleteClaim(chunk);
    }

    @Override
    public String toString(Event e, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        chunkExpr = (Expression<Chunk>) expr[0];
        return true;
    }
}
