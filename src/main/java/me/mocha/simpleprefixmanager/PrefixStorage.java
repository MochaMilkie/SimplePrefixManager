package me.mocha.simpleprefixmanager;

import org.bukkit.entity.Player;

import java.io.IOException;

/** Defines the persistence operations used by the prefix service. */
public interface PrefixStorage {

    PrefixData load(Player player) throws IOException;

    void save(Player player, boolean enabled, String prefix) throws IOException;

    void close();
}
