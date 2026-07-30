package me.mocha.simpleprefixmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

import static java.lang.String.join;

public class PrefixCommand implements CommandExecutor {

    private final SimplePrefixManager plugin;

    public PrefixCommand(SimplePrefixManager plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) commandSender;
        DataManager data = plugin.getDataManager();
        String args = join(" ", strings);
        if(player.hasPermission("SPM.prefix")){
            if(args.equals("enable")){
                try {
                    PrefixData prefixData = data.loadPrefixData(player);
                    data.save(player, true, prefixData.getPrefix());
                    player.sendMessage("Your custom prefix has been enabled.");
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (args.equals("disable")) {
                try {
                    PrefixData prefixData = data.loadPrefixData(player);
                    data.save(player, false, prefixData.getPrefix());
                    player.sendMessage("Your custom prefix has been disabled.");
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                try {
                    data.save(player , true , args);
                    player.sendMessage("Your custom prefix is now set to: " + args);
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }
}
