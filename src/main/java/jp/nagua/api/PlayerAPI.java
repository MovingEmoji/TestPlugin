package jp.nagua.api;

import jp.nagua.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerAPI {

    public static void initalizePlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setScoreboard(Main.scoreboard);
    }

    public static void teleportLobby(Player player) {
        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 36, 0.5));
    }

}
