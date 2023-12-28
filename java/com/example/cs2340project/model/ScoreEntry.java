package com.example.cs2340project.model;

import android.os.CountDownTimer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreEntry {
    private final String playerName;
    private final int score;
    private final Date timestamp;
    private long timeLeft;
    private CountDownTimer timer;

    public ScoreEntry(String playerName, int score, Date timestamp) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = timestamp;
        this.timeLeft = 30000;
        this.timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
            }
            @Override
            public void onFinish() {
            }

        }.start();
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    @Override
    public String toString() {
        return "Player: " + playerName + ", Score: " + score + ", Timestamp: "
                + getFormattedTimestamp();
    }
}