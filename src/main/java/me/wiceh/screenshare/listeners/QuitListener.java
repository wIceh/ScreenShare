package me.wiceh.screenshare.listeners;

import me.wiceh.screenshare.Main;
import me.wiceh.screenshare.utils.ScreenShareUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(Main.getScreenShareUtils().isControlled(player)) {
            Player controller = Main.getScreenShareUtils().getController(player);
            controller.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&4" + player.getName() + " &cè uscito dal controllo."));
            Main.getScreenShareUtils().controls.remove(controller);
            Location endLocation = (Location) Main.getInstance().getConfig().get("locations.end");
            controller.teleport(endLocation);
            player.teleport(endLocation);
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            controller.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }else if(Main.getScreenShareUtils().isController(player)) {
            Player controlled = Main.getScreenShareUtils().getControlled(player);
            Main.getScreenShareUtils().getControls().remove(player);
            Location endLocation = (Location) Main.getInstance().getConfig().get("locations.end");
            controlled.teleport(endLocation);
            player.teleport(endLocation);
            controlled.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
}
