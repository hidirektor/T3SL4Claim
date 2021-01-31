package me.t3sl4.claim.versionmatch;

import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.cactoos.scalar.NumberOf;
import org.cactoos.text.Replaced;
import org.cactoos.text.TextOf;
import org.jetbrains.annotations.NotNull;

public final class Version {
    @NotNull
    private static final Pattern PATTERN = Pattern.compile("v?(?<major>[0-9]+)[._](?<minor>[0-9]+)(?:[._](?<micro>[0-9]+))?(?<sub>.*)");
    @NotNull
    private final String version;

    public Version(@NotNull String version) {
        this.version = version;
    }

    public Version() {
        this(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1));
    }

    @NotNull
    public String raw() {
        return this.version;
    }

    public int major() {
        return (new NumberOf(new Replaced(new TextOf(this.version), () -> {
            return PATTERN;
        }, (matcher) -> {
            return matcher.group("major");
        }))).intValue();
    }

    public int minor() {
        return (new NumberOf(new Replaced(new TextOf(this.version), () -> {
            return PATTERN;
        }, (matcher) -> {
            return matcher.group("minor");
        }))).intValue();
    }

    public int micro() {
        return (new NumberOf(new Replaced(new Replaced(new TextOf(this.version), "R", ""), () -> {
            return PATTERN;
        }, (matcher) -> {
            return matcher.group("micro");
        }))).intValue();
    }
}
