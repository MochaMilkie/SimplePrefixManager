package me.mocha.simpleprefixmanager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public final class YMLData implements PrefixStorage {
    private final SimplePrefixManager plugin;

    public YMLData(SimplePrefixManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public PrefixData load(Player player) {
        File prefixFile = new File(plugin.getDataFolder(), "prefix.yml");
        YamlConfiguration prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        String uuid = player.getUniqueId().toString();
        String prefix = prefixConfig.getString(uuid + ".Prefix", "");
        boolean status = prefixConfig.getBoolean(uuid + ".Enabled", false);
        return new PrefixData(status, prefix);
    }

    @Override
    public void save(Player player, boolean status, String prefix) throws IOException {
        File prefixFile = new File(plugin.getDataFolder(), "prefix.yml");
        YamlConfiguration prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        String uuid = player.getUniqueId().toString();
        prefixConfig.set(uuid + ".Prefix", prefix == null ? "" : prefix);
        prefixConfig.set(uuid + ".Enabled", status);
        prefixConfig.save(prefixFile);

    }

    @Override
    public void close() {
        // YAML storage writes changes immediately.
    }
}
