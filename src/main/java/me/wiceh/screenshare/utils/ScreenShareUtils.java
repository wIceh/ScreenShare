package me.wiceh.screenshare.utils;

import me.wiceh.screenshare.Main;
import me.wiceh.screenshare.SS_Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenShareUtils {

    public Map<Player, Player> controls = new HashMap<>();

    public boolean isController(Player player) {
        return getControls().containsKey(player);
    }

    public boolean isControlled(Player player) {
        return getControls().containsValue(player);
    }

    public void control(Player controller, Player controlled) {
        getControls().put(controller, controlled);
        new SS_Scoreboard(controller);
        new SS_Scoreboard(controlled);
        Location controllerLocation = (Location) Main.getInstance().getConfig().get("locations.controller");
        Location controlledLocation = (Location) Main.getInstance().getConfig().get("locations.controlled");
        controller.teleport(controllerLocation);
        Main.getInventoryUtils().save(controller);
        controller.getInventory().clear();
        Main.getInventoryUtils().removeArmor(controller);
        controlled.teleport(controlledLocation);
        Main.getInventoryUtils().save(controlled);
        controlled.getInventory().clear();
        Main.getInventoryUtils().removeArmor(controlled);
    }

    public boolean isController(Player player, Player target) {
        return getControls().containsKey(player) && getControls().get(player) == target;
    }

    public Player getController(Player controlled) {
        for(int i = 0; i < getControls().size(); i++) {
            if(getControls().values().toArray()[i].equals(controlled)) {
                Player controller = (Player) getControls().keySet().toArray()[i];
                return controller;
            }
        }
        return null;
    }

    public void uncontrol(Player controller, Player controlled) {
        getControls().remove(controller, controlled);
        controller.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        controlled.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Location endLocation = (Location) Main.getInstance().getConfig().get("locations.end");
        controller.teleport(endLocation);
        controller.getInventory().clear();
        Main.getInventoryUtils().restore(controller);
        controlled.teleport(endLocation);
        controlled.getInventory().clear();
        Main.getInventoryUtils().restore(controlled);
    }

    public Player getControlled(Player player) {
        return controls.get(player);
    }

    public Map<Player, Player> getControls() {
        return controls;
    }
}
