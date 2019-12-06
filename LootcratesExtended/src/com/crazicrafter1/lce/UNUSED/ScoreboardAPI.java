package com.crazicrafter1.lce.util;

import com.crazicrafter1.lce.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {

    public static void setSideBar(Player player, String title, String[] messages) {
        Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

                Objective objective = board.registerNewObjective("lives", "dummy", "") ;//RenderType.INTEGER) ;//registerNewObjective("lives", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(title);

                StringBuilder spaces = new StringBuilder();

                for(int i=0; i<messages.length; i++){
                    Score score = null;

                    spaces.append(" ");

                    if (messages[i] == null || messages[i].equals("")) score = objective.getScore(spaces.toString());
                    else score = objective.getScore(messages[i]);

                    score.setScore(messages.length - i); //Example
                    player.setScoreboard(board);
                }
            }
        });


    }


    public static void removeScoreboard(Player p) {

        //p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

    }

}
