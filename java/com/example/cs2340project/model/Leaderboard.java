package com.example.cs2340project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard {
    private static ArrayList<PlayerScore> scores;
    private static Leaderboard uniqueInstance;


    public Leaderboard(ArrayList<PlayerScore> list) {
        this.scores = list;
    }
    public Leaderboard() {
        this.scores = new ArrayList<>();
    }
    public PlayerScore getMostRecentScore() {
        if (scores == null || scores.size() == 0) {
            return null;
        }

        PlayerScore mostRecent = scores.get(0);
        for (PlayerScore score : scores) {
            if (score.getTimeLeft() > mostRecent.getTimeLeft()) {
                mostRecent = score;
            }
        }
        return mostRecent;
    }

    public void addScore(PlayerScore score) {
        scores.add(score);
        Collections.sort(scores, new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore entry1, PlayerScore entry2) {

                return Integer.compare(entry2.getScore(),
                        entry1.getScore());
            }
        });
    }

    public ArrayList<PlayerScore> getScores() {
        return scores;
    }
    public static Leaderboard getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Leaderboard();
        }
        return uniqueInstance;
    }

}