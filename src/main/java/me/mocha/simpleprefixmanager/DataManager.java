package me.mocha.simpleprefixmanager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DataManager {

    private final SimplePrefixManager plugin;
    private YMLData ymldata;
    private SQLData sqldata;
    private String saveType;
    public DataManager(SimplePrefixManager plugin){
        this.plugin = plugin;
        String saveType = plugin.getConfig().getString("save-data-type");
        if(saveType.equalsIgnoreCase("yml")){
            this.ymldata = new YMLData(plugin);
            this.saveType = "yml";
        }
        else if(saveType.equalsIgnoreCase("sql")){
            this.sqldata = new SQLData();
            this.saveType = "sql";
        }
    }
    public String load(Player player) throws IOException, InvalidConfigurationException {
        switch(saveType){
            case "yml":
                return ymldata.loadFromYML(player);
            case "sql":
                return sqldata.loadFromSQL(player);
        }

        return "";
    }
    public void save(Player player,Boolean enabled, String prefix) throws IOException, InvalidConfigurationException {


    }
}
