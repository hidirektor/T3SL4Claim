package me.t3sl4.claim.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.util.ClaimType;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Name("Claim Types")
@Examples({"claim-types"})

public class ClaimTypesExpr extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ClaimTypesExpr.class, String.class, ExpressionType.SIMPLE, "claim(-|)types");
    }

    @Override
    protected String[] get(Event e) {
        List<String> list = new ArrayList<>();
        for (ClaimType claimTYPE: ClaimType.claimTypes) {
            list.add(claimTYPE.getTypeName());
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "claim types";
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
