package me.t3sl4.claim.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.claimtypes.ClaimTypeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Unregister claim type")
@Examples({"unregister claimtype \"typename\""})

public class UnregisterClaimTypeEff extends Effect {
    static {
        Skript.registerEffect(UnregisterClaimTypeEff.class, "unregister claim(-| |)type %string%");
    }

    private Expression<String> nameExpr;

    @Override
    protected void execute(Event e) {
        String typeName = nameExpr.getSingle(e);
        ClaimTypeManager.unregister(typeName);
    }

    @Override
    public String toString(Event e, boolean b) {
        return "unregister" + nameExpr.getSingle(e) + "claim type";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        nameExpr = (Expression<String>) expr[0];
        return true;
    }
}
