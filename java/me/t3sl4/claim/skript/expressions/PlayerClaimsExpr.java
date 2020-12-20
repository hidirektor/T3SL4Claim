package me.t3sl4.claim.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.T3SL4Claim;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Name("Claim of Player(s)")
@Examples({"claims of player",
            "chunk claims of all players"})

public class PlayerClaimsExpr extends SimpleExpression {
    static {
        Skript.registerExpression(PlayerClaimsExpr.class, Chunk.class, ExpressionType.SIMPLE, "%player%['s] [chunk] claim[s]",
                "[chunk] claim[s] of %player%");
        //register(PlayerClaimsExpr.class, Chunk.class, "[chunk] claim[s]", "player");
    }

    private Expression<Player> playerExpression;
    @Override
    protected Chunk[] get(Event e) {
        List<Chunk> chunks = new ArrayList<>();
        Player p = playerExpression.getSingle(e);
        chunks.addAll(T3SL4Claim.getClaimUtil().getPlayerChunks(p));
        return chunks.toArray(new Chunk[chunks.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class getReturnType() {
        return Chunk.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "chunk claims";
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        playerExpression = ((Expression<Player>) expr[0]);
        return true;
    }
}
