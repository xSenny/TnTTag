package me.xsenny.tnttag.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardMethods {

    public static void setScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("TntTag", "orice");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score online = obj.getScore(ChatColor.GRAY+"> Online");
        online.setScore(15);

        Team onlineCounter = board.registerNewTeam("onlineCounter");
        onlineCounter.addEntry(ChatColor.WHITE+""+ChatColor.BLACK);
        if (Bukkit.getOnlinePlayers().size() == 0) {
            onlineCounter.setPrefix(ChatColor.DARK_RED + "0" + ChatColor.RED + "/" + ChatColor.DARK_RED + Bukkit.getMaxPlayers());
        } else {
            onlineCounter.setPrefix("" + ChatColor.DARK_RED + Bukkit.getOnlinePlayers().size() + ChatColor.RED + "/" + ChatColor.DARK_RED + Bukkit.getMaxPlayers());
        }
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);

        Score money = obj.getScore(ChatColor.GRAY + "» Money");
        money.setScore(13);

        Team moneyCounter = board.registerNewTeam("moneyCounter");
        moneyCounter.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        moneyCounter.setPrefix(ChatColor.GREEN + "$" + 4);
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(12);

        player.setScoreboard(board);
    }

    public static void updateScoreboard(Player player){
        Scoreboard board = player.getScoreboard();

        if (Bukkit.getOnlinePlayers().size() == 0) {
            board.getTeam("onlineCounter").setPrefix(ChatColor.DARK_RED + "0" + ChatColor.RED + "/" + ChatColor.DARK_RED + Bukkit.getMaxPlayers());
        } else {
            board.getTeam("onlineCounter").setPrefix(ChatColor.DARK_RED + "" + Bukkit.getOnlinePlayers().size() + ChatColor.RED + "/" + ChatColor.DARK_RED + Bukkit.getMaxPlayers());
        }


        board.getTeam("moneyCounter").setPrefix(ChatColor.GREEN + "$" + 4);
    }

}
