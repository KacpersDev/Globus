package tech.azuriteservices.utils;

import org.bukkit.ChatColor;

public class CC {

    public static String toColor(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
