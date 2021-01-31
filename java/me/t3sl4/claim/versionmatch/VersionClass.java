package me.t3sl4.claim.versionmatch;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

final class VersionClass<T> {
    private static final char[] NUMBERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    @NotNull
    private final String rawClassName;
    @NotNull
    private final Class<? extends T> clazz;

    private VersionClass(@NotNull String rawClassName, @NotNull Class<? extends T> clazz) {
        this.rawClassName = rawClassName;
        this.clazz = clazz;
    }

    VersionClass(@NotNull Class<? extends T> clazz) {
        this(clazz.getSimpleName(), clazz);
    }

    @NotNull
    Class<? extends T> getVersionClass() {
        return this.clazz;
    }

    boolean match(@NotNull String version) {
        return this.version().equals(version);
    }

    @NotNull
    private String version() {
        int subString = this.versionSubString();
        if (subString == -1) {
            throw new IllegalStateException("version() -> Invalid name for \"" + this.clazz.getSimpleName() + "\"");
        } else {
            return this.rawClassName.substring(subString);
        }
    }

    private int versionSubString() {
        AtomicInteger subString = new AtomicInteger();
        char[] var2 = this.rawClassName.toCharArray();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char name = var2[var4];
            char[] var6 = NUMBERS;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                int number = var6[var8];
                if (name == number) {
                    return subString.get();
                }
            }

            subString.incrementAndGet();
        }

        return subString.get();
    }
}
