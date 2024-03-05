package me.wiceh.screenshare.commands;

import me.wiceh.screenshare.Main;
import me.wiceh.screenshare.utils.ScreenShareUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnScreenShareCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("screenshare.use")) {
            player.sendMessage(Main.getColorUtils().color("&cNon hai il permesso!"));
            return true;
        }

        if(args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null && target.isOnline()) {

                if(!Main.getScreenShareUtils().isControlled(target)) {
                    player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Questo giocatore non è in controllo!"));
                    return true;
                }

                if(!Main.getScreenShareUtils().isController(player, target)) {
                    player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Non sei il controllore di questo giocatore!"));
                    return true;
                }

                Main.getScreenShareUtils().uncontrol(player, target);
                player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Hai smesso di controllare: &c" + target.getName() + "&7."));

            }else {
                player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Giocatore non trovato."));
            }
        }else {
            player.sendMessage(Main.getColorUtils().color("&cUtilizzo: /" + label + " <player>"));
        }

        return true;
    }
}
