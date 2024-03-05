package me.wiceh.screenshare;

import me.wiceh.screenshare.commands.ScreenShareCommand;
import me.wiceh.screenshare.commands.UnScreenShareCommand;
import me.wiceh.screenshare.listeners.ChatListener;
import me.wiceh.screenshare.listeners.QuitListener;
import me.wiceh.screenshare.utils.ColorUtils;
import me.wiceh.screenshare.utils.InventoryUtils;
import me.wiceh.screenshare.utils.ScreenShareUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static String PREFIX;
    private static ColorUtils colorUtils;
    private static InventoryUtils inventoryUtils;
    private static ScreenShareUtils screenShareUtils;

    @Override
    public void onEnable() {
        instance = this;
        colorUtils = new ColorUtils();
        PREFIX = getColorUtils().color("&8[&cSS&8]&r ");
        inventoryUtils = new InventoryUtils();
        screenShareUtils = new ScreenShareUtils();

        getConfig().options().copyDefaults(true);
        saveConfig();

        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        getCommand("screenshare").setExecutor(new ScreenShareCommand());
        getCommand("unscreenshare").setExecutor(new UnScreenShareCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static ColorUtils getColorUtils() {
        return colorUtils;
    }

    public static InventoryUtils getInventoryUtils() {
        return inventoryUtils;
    }

    public static ScreenShareUtils getScreenShareUtils() {
        return screenShareUtils;
    }
}
