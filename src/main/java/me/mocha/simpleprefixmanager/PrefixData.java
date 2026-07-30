package me.mocha.simpleprefixmanager;

/** Immutable prefix state for a player. */
public final class PrefixData {

    private final boolean enabled;
    private final String prefix;

    public PrefixData(boolean enabled, String prefix) {
        this.enabled = enabled;
        this.prefix = prefix == null ? "" : prefix;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPrefix() {
        return prefix;
    }
}
