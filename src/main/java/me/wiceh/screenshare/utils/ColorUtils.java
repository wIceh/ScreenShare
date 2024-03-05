package me.wiceh.screenshare.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
