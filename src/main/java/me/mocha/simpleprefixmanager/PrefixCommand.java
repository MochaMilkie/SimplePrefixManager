package me.mocha.simpleprefixmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.String.join;

public class PrefixCommand implements CommandExecutor {

    private final SimplePrefixManager plugin;

    public PrefixCommand(SimplePrefixManager plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        DataManager data = new DataManager(plugin);
        String args = join(" ", strings);
        if(player.hasPermission("SPM.prefix")){
            if(args.equals("enable")){
                try {
                    String prefix = data.load(player);
                    if(prefix == null){
                        prefix = "";
                    }
                    data.save(player , true , prefix);
                    player.sendMessage("Your custom prefix has been enabled.");
                    return true;
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }

            } else if (args.equals("disable")) {
                try {
                    String prefix = data.load(player);
                    data.save(player , false , prefix);
                    player.sendMessage("Your custom prefix has been disabled.");
                    return true;
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                try {
                    data.save(player , true , args);
                    player.sendMessage("Your custom prefix is now set to: " + args);
                    return true;
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }
}
