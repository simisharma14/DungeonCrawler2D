package com.example.cs2340project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340project.model.Leaderboard;
import com.example.cs2340project.model.PlayerScore;

import java.util.ArrayList;
import java.util.Date;

public class LeaderboardViewModel extends ViewModel {
    private MutableLiveData<Leaderboard> leaderboardData = new MutableLiveData<>();
    private long timeLeft = 0;
    private long timeStamp;
    private String name;

    private int playerHealth;

    public LeaderboardViewModel() {
        leaderboardData.setValue(new Leaderboard());
    }

    public LiveData<Leaderboard> getLeaderboardData() {
        return leaderboardData;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeStamp() {
        timeStamp = new Date().getTime();
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth; // Setter for player health
    }

    public void addToLeaderboard() {
        Leaderboard currBoard = leaderboardData.getValue();
        PlayerScore playerScore = new PlayerScore(name, timeStamp, timeLeft, playerHealth);
        currBoard.addScore(playerScore);
        leaderboardData.setValue(currBoard);
    }

    public Leaderboard getCurrBoard() {
        Leaderboard currBoard = leaderboardData.getValue();
        return currBoard;
    }

    public void updateLeaderboard(ArrayList<PlayerScore> list) {
        leaderboardData.setValue(new Leaderboard(list));
    }
}

