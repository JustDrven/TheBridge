package cz.drven.thebridge.utils;

import org.bukkit.ChatColor;

public class Colors {

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
