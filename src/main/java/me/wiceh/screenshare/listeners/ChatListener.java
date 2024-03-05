package me.wiceh.screenshare.listeners;

import me.wiceh.screenshare.Main;
import me.wiceh.screenshare.utils.ScreenShareUtils;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(Main.getScreenShareUtils().isController(player)) {
            event.setCancelled(true);
            Player controller = player;
            Player controlled = Main.getScreenShareUtils().getControls().get(player);
            User controllerUser = LuckPermsProvider.get().getUserManager().getUser(controller.getUniqueId());

            String prefix = controllerUser.getCachedData().getMetaData().getPrefix() != null ? controllerUser.getCachedData().getMetaData().getPrefix() : "";
            String prefixes = controllerUser.getCachedData().getMetaData().getPrefixes().values().toString() != null ? controllerUser.getCachedData().getMetaData().getPrefixes().values().toString() : "";
            String suffix = controllerUser.getCachedData().getMetaData().getSuffix() != null ? controllerUser.getCachedData().getMetaData().getSuffix() : "";
            String suffixes = controllerUser.getCachedData().getMetaData().getSuffixes().values().toString() != null ? controllerUser.getCachedData().getMetaData().getSuffixes().values().toString() : "";
            String controllerFormat = Main.getColorUtils().color(Main.getInstance().getConfig().getString("chat.controller-format").replace(
                    "{PREFIX}", prefix
            ).replace(
                    "{PREFIXES}", prefixes
            ).replace(
                    "{SUFFIX}", suffix
            ).replace(
                    "{SUFFIXES}", suffixes
            ).replace(
                    "{PLAYER}", controller.getName()
            ).replace(
                    "{DISPLAYNAME}", controller.getDisplayName()
            ).replace(
                    "{MESSAGE}", event.getMessage()
            ));
            controller.sendMessage(Main.getColorUtils().color(controllerFormat));
            controlled.sendMessage(Main.getColorUtils().color(controllerFormat));
        }else if(Main.getScreenShareUtils().isControlled(player)) {
            event.setCancelled(true);
            Player controlled = player;
            Player controller = Main.getScreenShareUtils().getController(controlled);
            User controlledUser = LuckPermsProvider.get().getUserManager().getUser(controlled.getUniqueId());
            String prefix = controlledUser.getCachedData().getMetaData().getPrefix() != null ? controlledUser.getCachedData().getMetaData().getPrefix() : "";
            String prefixes = controlledUser.getCachedData().getMetaData().getPrefixes().values().toString() != null ? controlledUser.getCachedData().getMetaData().getPrefixes().values().toString() : "";
            String suffix = controlledUser.getCachedData().getMetaData().getSuffix() != null ? controlledUser.getCachedData().getMetaData().getSuffix() : "";
            String suffixes = controlledUser.getCachedData().getMetaData().getSuffixes().values().toString() != null ? controlledUser.getCachedData().getMetaData().getSuffixes().values().toString() : "";
            String controlledFormat = Main.getColorUtils().color(Main.getInstance().getConfig().getString("chat.controlled-format").replace(
                    "{PREFIX}", prefix
            ).replace(
                    "{PREFIXES}", prefixes
            ).replace(
                    "{SUFFIX}", suffix
            ).replace(
                    "{SUFFIXES}", suffixes
            ).replace(
                    "{PLAYER}", controlled.getName()
            ).replace(
                    "{DISPLAYNAME}", controlled.getDisplayName()
            ).replace(
                    "{MESSAGE}", event.getMessage()
            ));
            controller.sendMessage(Main.getColorUtils().color(controlledFormat));
            controlled.sendMessage(Main.getColorUtils().color(controlledFormat));
        }
    }

    @EventHandler
    public void onChat2(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().getName().equalsIgnoreCase("screenshare")) {
            if(!Main.getScreenShareUtils().isController(player) && !Main.getScreenShareUtils().isControlled(player)) {
                event.setCancelled(true);
                player.getWorld().getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(Main.getColorUtils().color("&d[DEBUG] &7" + player.getName() + "&7: &f" + event.getMessage())));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat3(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(player.getWorld().getName().equalsIgnoreCase("screenshare")) {
            return;
        }

        event.setCancelled(true);

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        String prefix = user.getCachedData().getMetaData().getPrefix() != null ? user.getCachedData().getMetaData().getPrefix() : "";
        String suffix = user.getCachedData().getMetaData().getSuffix() != null ? user.getCachedData().getMetaData().getSuffix() : "";
        String format = Main.getColorUtils().color(Main.getInstance().getConfig().getString("chat.default-format").replace(
                "{PREFIX}", prefix
        ).replace(
                "{SUFFIX}", suffix
        ).replace(
                "{PLAYER}", player.getName()
        ).replace(
                "{DISPLAYNAME}", player.getDisplayName()
        ).replace(
                "{MESSAGE}", event.getMessage()
        ));
        player.getWorld().getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(format));
    }
}
