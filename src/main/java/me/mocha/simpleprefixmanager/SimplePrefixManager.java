package me.mocha.simpleprefixmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplePrefixManager extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("prefix.yml" , false);

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PAPI(this).register();
        }
        Bukkit.getPluginManager().registerEvents(new ChatListener() , this);
        getCommand("prefix").setExecutor(new PrefixCommand(this));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
