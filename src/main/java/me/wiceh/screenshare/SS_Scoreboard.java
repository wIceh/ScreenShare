package me.wiceh.screenshare;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SS_Scoreboard {

    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private Objective objective;

    public SS_Scoreboard(Player player) {
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("ss_scoreboard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.setDisplayName(Main.getColorUtils().color("&c&lSCREENSHARE"));
        objective.getScore(Main.getColorUtils().color("  ")).setScore(4);
        objective.getScore(Main.getColorUtils().color("&cNick: &e" + player.getName())).setScore(3);
        objective.getScore(Main.getColorUtils().color(" ")).setScore(2);
        objective.getScore(Main.getColorUtils().color("&7play.server.it")).setScore(1);

        player.setScoreboard(scoreboard);
    }
}
