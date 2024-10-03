package jp.nagua.commands;

import jp.nagua.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PingCommand implements CommandExecutor {

    static {
        Main.getPlugin().getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            CraftPlayer craftplayer = (CraftPlayer) sender;
            sender.sendMessage(ChatColor.AQUA + "Your average ping is " + craftplayer.getHandle().ping + "ms");
            return true;
        }
        return false;
    }
}
