package jp.nagua;

import jp.nagua.elements.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + "がログインしました。");
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
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
}
