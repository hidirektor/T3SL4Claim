package me.t3sl4.claim.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.util.ClaimUtil;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Claim")
@Examples("claim chunk at player for player for 10 days")

public class ClaimEff extends Effect {
    static {
        Skript.registerEffect(ClaimEff.class, "claim [the [chunk]] %chunk% for %player% (for|expiring after) %timespan%");
    }

    private Expression<Player> playerExpr;
    private Expression<Chunk> chunkExpr;
    private Expression<Timespan> timespanExpr;

    @Override
    protected void execute(Event e) {
        Player p = playerExpr.getSingle(e);
        Chunk chunk = chunkExpr.getSingle(e);
        Timespan timespan = timespanExpr.getSingle(e);
        ClaimUtil claimUtil = T3SL4Claim.getInstance().getClaimUtil();
        String time = claimUtil.msToLongTime(timespan.getMilliSeconds());
        claimUtil.createClaim(p, chunk, claimUtil.timeAddCalculate(time));
    }

    @Override
    public String toString(Event e, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        chunkExpr = (Expression<Chunk>) expr[0];
        playerExpr = (Expression<Player>) expr[1];
        timespanExpr = (Expression<Timespan>) expr[2];
        return true;
    }
}
