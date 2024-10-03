package jp.nagua.elements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Team {

    private static Map<Player, Integer> teamInfomation;

    static class Color{
        public static int RED = 0;
        public static int BLUE = 1;
    }

    private int color;
    private String name;
    private List<Player> players;

    public Team(int color, String name) {
        this.color = color;
        this.name = name;
        this.players = new ArrayList<>();
        Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name).setPrefix(ChatColor.GRAY + "[" + name + ChatColor.GRAY + "]" + ChatColor.RESET);
    }

    public void addPlayer(Player player) {
        if(!players.contains(player)) {
            if(teamInfomation.get(player) == null) {
                players.add(player);
                teamInfomation.put(player, color);
            }
        }
    }

    public void removePlayer(Player player) {
        if(players.contains(player)) {
            players.remove(player);
            teamInfomation.remove(player);
        }
    }

    public static int getTeamColor(Player player) {
        if(teamInfomation.get(player) != null) {
            return teamInfomation.get(player);
        }
        return -1;
    }
}
