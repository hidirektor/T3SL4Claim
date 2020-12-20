package me.t3sl4.claim.skript.expressions;

import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.T3SL4Claim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Name("Claim Owner")
@Examples("chunk at player's claim owner")

public class OwnerOfExpr extends PropertyExpression<Chunk, Player> {
    static {
        register(OwnerOfExpr.class, Player.class, "[chunk] claim owner", "chunks");
    }

    @Override
    protected Player[] get(Event e, Chunk[] chunks) {
        List<Player> players = new ArrayList<>();
        for (Chunk chunk: getExpr().getArray(e)) {
            String player = T3SL4Claim.getClaimUtil().getPlayer(chunk);
            if (player == null) players.add(null);
            Player p = Bukkit.getPlayer(player);
            if (p == null) players.add(null);
            players.add(p);
        }
        return players.toArray(new Player[players.size()]);
    }

    @Override
    public Class getReturnType() {
        return Player.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "chunk claim owner";
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Chunk>) expr[0]);
        return true;
    }
}
