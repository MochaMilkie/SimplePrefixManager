package me.mocha.simpleprefixmanager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class YMLData {
    private final SimplePrefixManager plugin;

    public YMLData(SimplePrefixManager plugin) {
        this.plugin = plugin;
    }


    File prefixFile;
    YamlConfiguration prefixConfig;

    public YamlConfiguration getPrefixConfig(){
        return this.prefixConfig;
    }

    public String loadFromYML(Player player) throws IOException, InvalidConfigurationException {
        prefixFile = new File(plugin.getDataFolder()+File.separator+"prefix.yml");
        YamlConfiguration prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        String uuid = player.getUniqueId().toString();
        if(player.hasPermission("SPM.prefix")){
            String prefix = prefixConfig.getString(uuid+".Prefix");
            boolean status = prefixConfig.getBoolean(uuid+".Enabled");
            if(status){
                return prefix;
            } else {
                return "";


            }
        }
        return "";
    }

    public void saveToYML(Player player, Boolean status , String prefix) throws IOException, InvalidConfigurationException {
        prefixFile = new File(plugin.getDataFolder()+File.separator+"prefix.yml");
        YamlConfiguration prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        String uuid = player.getUniqueId().toString();
        if(player.hasPermission("SPM.prefix")){
            if(!status){
                prefixConfig.set(uuid+".Enabled" , false);
                prefixConfig.save(prefixFile);
                return;
            }
            prefixConfig.set(uuid+".Prefix" , prefix);
            prefixConfig.set(uuid+".Enabled" , true);
            prefixConfig.save(prefixFile);

        }

    }
}
