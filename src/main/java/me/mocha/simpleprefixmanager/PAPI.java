package me.mocha.simpleprefixmanager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PAPI extends PlaceholderExpansion {

    private final SimplePrefixManager plugin;

    public PAPI(SimplePrefixManager plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "SPM";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MochaMilkie";
    }

    @Override
    public @NotNull String getVersion() {
        return "v1.1";
    }

    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player , @NotNull String identifier){
        if(identifier.equalsIgnoreCase("prefix") && player != null){
            DataManager data = plugin.getDataManager();
            if (player.hasPermission("SPM.prefix")){
                try {
                    return data.load(player);
                } catch (IOException e) {
                    plugin.getLogger().warning(e.getMessage());
                }

            }
        }
        return "";

    }
}
