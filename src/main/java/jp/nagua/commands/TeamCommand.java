package jp.nagua.commands;

import jp.nagua.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    static {
        Main.getPlugin().getCommand("team").setExecutor(new TeamCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 3) {
            return true;
        }
        return false;
    }
}
