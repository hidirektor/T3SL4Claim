package com.infumia.t3sl4.claim.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.infumia.t3sl4.claim.api.PlayerClaimStuffAddEvent;
import com.infumia.t3sl4.claim.api.PlayerClaimStuffKickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ClaimOwnerExpr extends SimpleExpression<Player> {
    static {
        Skript.registerExpression(ClaimOwnerExpr.class, Player.class, ExpressionType.SIMPLE, "[claim( |-)]owner");
    }

    @Override
    protected Player[] get(Event e) {
        if (!(e instanceof PlayerClaimStuffKickEvent || e instanceof PlayerClaimStuffAddEvent)) return null;
        Player player;
        if (e instanceof PlayerClaimStuffKickEvent) player = ((PlayerClaimStuffKickEvent)e).getOwner();
        else player = ((PlayerClaimStuffAddEvent)e).getOwner();
        return new Player[] {player};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class getReturnType() {
        return Player.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "claim owner";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(PlayerClaimStuffAddEvent.class, PlayerClaimStuffKickEvent.class)) {
            Skript.error("Cannot use 'owner' outside of a claim kick/claim add event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }
}
