package me.wiceh.screenshare.utils;

import me.wiceh.screenshare.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryUtils {

    public void save(Player player) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("inventory.armor", player.getInventory().getArmorContents());
        config.set("inventory.content", player.getInventory().getContents());
        try {
            config.save(new File(Main.getInstance().getDataFolder() + "\\data\\" + player.getName() + ".yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restore(Player player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder() + "\\data\\" + player.getName() + ".yml"));
        ItemStack[] armor = (ItemStack[]) ((List) config.get("inventory.armor")).toArray(new ItemStack[0]);
        ItemStack[] content = (ItemStack[]) ((List) config.get("inventory.content")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(armor);
        player.getInventory().setContents(content);
    }

    public void removeArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
}
