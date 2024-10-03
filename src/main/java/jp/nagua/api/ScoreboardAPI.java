package jp.nagua.api;

import jp.nagua.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {

    public static Scoreboard createScoreboard() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        return scoreboard;
    }

    public static void initalizeScoreboard(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy");
        objective.setDisplayName(ChatColor.GOLD + "Emoji Games");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(" ").setScore(9);
        Team team1 = scoreboard.registerNewTeam("team1");
        team1.addEntry(ChatColor.YELLOW + "ONLINES: ");
        team1.setSuffix(ChatColor.GRAY + "0/10");
        objective.getScore(ChatColor.YELLOW + "ONLINES: ").setScore(8);
        objective.getScore("  ").setScore(7);
        objective.getScore(ChatColor.GOLD + "play.emoji.com").setScore(6);
    }

    public static void refreshScoreboard() {
        Team team1 = Main.scoreboard.getTeam("team1");
        team1.setSuffix(ChatColor.GRAY + "" + Bukkit.getOnlinePlayers().size() + "/10");
    }
}
