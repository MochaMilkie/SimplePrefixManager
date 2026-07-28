package me.mocha.simpleprefixmanager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.event.EventPriority.HIGHEST;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatEvent(AsyncPlayerChatEvent event){
        if(event.getPlayer().hasPermission("SPM.color")){
            String message = event.getMessage();
            Pattern HEX_PATTERN = Pattern.compile("(#[A-Fa-f0-9)]{6})");
            Matcher matcher = HEX_PATTERN.matcher(message);
            while (matcher.find()) {
                message = message.replace(matcher.group(), "" + ChatColor.of(matcher.group()));
            }
            message = ChatColor.translateAlternateColorCodes('&', message);
            event.setMessage(message);
        }
    }
}
