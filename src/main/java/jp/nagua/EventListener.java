package jp.nagua;

import jp.nagua.api.PlayerAPI;
import jp.nagua.commands.PacketArmorStand;
import jp.nagua.elements.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GREEN + event.getPlayer().getName() + " joined. (" +
                ChatColor.WHITE + Bukkit.getServer().getOnlinePlayers().size() + "/10" + ChatColor.GREEN + ")");
        PlayerAPI.initalizePlayer(event.getPlayer());
        PlayerAPI.teleportLobby(event.getPlayer());
        PacketArmorStand stand = new PacketArmorStand(event.getPlayer().getLocation(), event.getPlayer().getName());
        Main.standMap.put(event.getPlayer(), stand);
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player != event.getPlayer()) {
                stand.sendSpawn(player);
                Main.standMap.get(player).sendSpawn(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityTargetLivingEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getTarget() instanceof Player) {
            Player victim = (Player) event.getTarget();
            Player attacker = (Player) event.getEntity();
            if(Team.getTeamColor(victim) != 1 && Team.getTeamColor(attacker) != -1) {
                if(Team.getTeamColor(victim) == Team.getTeamColor(attacker)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
