package me.t3sl4.claim.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.t3sl4.claim.T3SL4Claim;
import me.t3sl4.claim.types.ClaimTypeManager;
import me.t3sl4.claim.skript.SkriptLoader;
import me.t3sl4.claim.types.ClaimType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
@Name("Register claim type")
@Examples("register new claimtype {coin::%player%} with name \"Coin\" with typename \"coinType\"")

public class RegisterClaimTypeEff extends Effect {
    static {
        Skript.registerEffect(RegisterClaimTypeEff.class, "register [new] claim(-| |)type %-objects% with name %string% [with type( |)name %string%]");
    }

    private String variableValue;
    private Expression<String> nameExpr;
    private Expression<String> typenameExpr;

    @Override
    protected void execute(Event e) {
        String name = nameExpr.getSingle(e);
        String typeName = name.toLowerCase();
        if (typenameExpr!=null) {
            typeName = typenameExpr.getSingle(e);
        }
        if (ClaimType.containsType(typeName)) {
            Skript.error("This claim type name already exists!");
            return;
        }
        ClaimTypeManager.register(new ClaimType(name, typeName) {
            private SkriptLoader skriptLoader = T3SL4Claim.getInstance().getSkriptLoader();

            @Override
            public int get(Player p) {
                String variable = skriptLoader.replaceVariable(variableValue, p);
                Object var = Variables.getVariable(variable, null, false);
                if (var==null) return 0;
                String str = var.toString();
                return skriptLoader.parseInt(str);
            }

            @Override
            public int get(String name) {
                String variable = skriptLoader.replaceVariable(variableValue, name);
                Object var = Variables.getVariable(variable, null, false);
                if (var==null) return 0;
                String str = var.toString();
                return skriptLoader.parseInt(str);
            }

            @Override
            public void set(Player p, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, p);
                Variables.setVariable(variable, value, null, false);
            }

            @Override
            public void set(String name, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, name);
                Variables.setVariable(variable, value, null, false);
            }

            @Override
            public void add(Player p, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, p);
                Object var = Variables.getVariable(variable, null, false);
                int num = 0;
                if (var!=null) {
                    String str = var.toString();
                    num = skriptLoader.parseInt(str);
                }
                Variables.setVariable(variable, num+value, null, false);
            }

            @Override
            public void add(String name, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, name);
                Object var = Variables.getVariable(variable, null, false);
                int num = 0;
                if (var!=null) {
                    String str = var.toString();
                    num = skriptLoader.parseInt(str);
                }
                Variables.setVariable(variable, num+value, null, false);
            }

            @Override
            public void remove(Player p, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, p);
                Object var = Variables.getVariable(variable, null, false);
                int num = 0;
                if (var!=null) {
                    String str = var.toString();
                    num = skriptLoader.parseInt(str);
                }
                Variables.setVariable(variable, num-value, null, false);
            }

            @Override
            public void remove(String name, int value) {
                String variable = skriptLoader.replaceVariable(variableValue, name);
                Object var = Variables.getVariable(variable, null, false);
                int num = 0;
                if (var!=null) {
                    String str = var.toString();
                    num = skriptLoader.parseInt(str);
                }
                Variables.setVariable(variable, num-value, null, false);
            }
        });
    }

    @Override
    public String toString(Event e, boolean b) {
        return "register" + nameExpr.getSingle(e) + "claim type";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        variableValue = expr[0].toString();
        nameExpr = (Expression<String>) expr[1];
        if (expr.length==3) {
            typenameExpr = (Expression<String>) expr[2];
        }
        return true;
    }
}
