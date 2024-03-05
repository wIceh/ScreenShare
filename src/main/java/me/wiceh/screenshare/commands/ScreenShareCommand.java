package me.wiceh.screenshare.commands;

import me.wiceh.screenshare.Main;
import me.wiceh.screenshare.utils.ScreenShareUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScreenShareCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("set-controller")) {

                if(!player.hasPermission("screenshare.admin")) {
                    player.sendMessage(Main.getColorUtils().color("&cNon hai il permesso!"));
                    return true;
                }

                Main.getInstance().getConfig().set("locations.controller", player.getLocation());
                Main.getInstance().saveConfig();
                player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&aPosizione del controllore settata."));

            }else if(args[0].equalsIgnoreCase("set-controlled")) {

                if(!player.hasPermission("screenshare.admin")) {
                    player.sendMessage(Main.getColorUtils().color("&cNon hai il permesso!"));
                    return true;
                }

                Main.getInstance().getConfig().set("locations.controlled", player.getLocation());
                Main.getInstance().saveConfig();
                player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&aPosizione del controllato settata."));

            }else if(args[0].equalsIgnoreCase("set-end")) {

                if(!player.hasPermission("screenshare.admin")) {
                    player.sendMessage(Main.getColorUtils().color("&cNon hai il permesso!"));
                    return true;
                }

                Main.getInstance().getConfig().set("locations.end", player.getLocation());
                Main.getInstance().saveConfig();
                player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&aPosizione della fine del controllo settata."));

            }else {

                if(!player.hasPermission("screenshare.use")) {
                    player.sendMessage(Main.getColorUtils().color("&cNon hai il permesso!"));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if(target != null && target.isOnline()) {

                    if(target.getName().equals(player.getName())) {
                        player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Non puoi mettere in controllo te stesso!"));
                        return true;
                    }

                    if(Main.getScreenShareUtils().isControlled(target)) {
                        player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Questo giocatore sta già essendo controllato!"));
                        return true;
                    }

                    if(Main.getScreenShareUtils().isController(target)) {
                        player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Stai già controllando un giocatore!"));
                        return true;
                    }

                    Main.getScreenShareUtils().control(player, target);
                    player.sendMessage("");
                    player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&eStai controllando il giocatore: &6" + target.getName()));

                    target.sendTitle(Main.getColorUtils().color("&c&lCONTROLLO HACK!"), Main.getColorUtils().color("&7Non sloggare e fai quello che ti dice il controllore."));

                    TextComponent cheating = new TextComponent(Main.getColorUtils().color("&8[&cCHEAT&8]"));
                    cheating.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.getColorUtils().color(cheating.getText() + "\n&7Banna " + target.getName() + " per CHEATING")).create()));
                    cheating.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tempban " + target.getName() + " 30d Cheating -s"));

                    TextComponent space = new TextComponent(" ");

                    TextComponent clean = new TextComponent(Main.getColorUtils().color("&8[&aCLEAN&8]"));
                    clean.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.getColorUtils().color(clean.getText() + "\n&7Smetti di controllare " + target.getName())).create()));
                    clean.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/unss " + target.getName()));

                    player.sendMessage("");
                    player.spigot().sendMessage(cheating, space, clean);
                    player.sendMessage("");

                }else {
                    player.sendMessage(Main.getColorUtils().color(Main.getPrefix() + "&7Giocatore non trovato."));
                }
            }
        }else {
            player.sendMessage(Main.getColorUtils().color("&cUtilizzo: /" + label + " <player>"));
        }

        return true;
    }
}
