package me.mocha.simpleprefixmanager;

import org.bukkit.entity.Player;

import java.io.IOException;

public final class DataManager {

    private final PrefixStorage storage;

    public DataManager(SimplePrefixManager plugin) {
        String saveMethod = plugin.getConfig().getString("save-method", "YAML");
        if (saveMethod.equalsIgnoreCase("YAML") || saveMethod.equalsIgnoreCase("YML")) {
            storage = new YMLData(plugin);
        } else if (saveMethod.equalsIgnoreCase("SQLITE") || saveMethod.equalsIgnoreCase("SQL")) {
            storage = new SQLData(plugin);
        } else if (saveMethod.equalsIgnoreCase("MYSQL")) {
            storage = new MySQLData(plugin);
        } else {
            throw new IllegalArgumentException("Unsupported save-method: " + saveMethod);
        }
    }

    public String load(Player player) throws IOException {
        PrefixData prefixData = storage.load(player);
        return prefixData.isEnabled() ? prefixData.getPrefix() : "";
    }

    public PrefixData loadPrefixData(Player player) throws IOException {
        return storage.load(player);
    }

    public void save(Player player, boolean enabled, String prefix) throws IOException {
        storage.save(player, enabled, prefix);
    }

    public void close() {
        storage.close();
    }
}
