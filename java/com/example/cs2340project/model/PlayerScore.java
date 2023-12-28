package com.example.cs2340project.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;

public class PlayerScore implements Parcelable {
    private String playerName;
    private long timestamp;
    private long timeLeft;
    private int score;

    private int playerHealth;

    public PlayerScore(String playerName, long timestamp, long timeLeft, int playerHealth) {
        this.playerName = playerName;
        this.timestamp = timestamp;
        this.timeLeft = timeLeft;
        this.playerHealth = playerHealth;
        if (playerHealth == 0) {
            this.score = (((int) timeLeft) / 1000);
        } else {
            this.score = (((int) timeLeft) / 1000) * playerHealth;

        }

    }

    protected PlayerScore(Parcel in) {
        playerName = in.readString();
        timestamp = in.readLong();
        timeLeft = in.readLong();
        score = in.readInt();
        playerHealth = in.readInt();
    }

    public static final Creator<PlayerScore> CREATOR = new Creator<PlayerScore>() {
        @Override
        public PlayerScore createFromParcel(Parcel in) {
            return new PlayerScore(in);
        }

        @Override
        public PlayerScore[] newArray(int size) {
            return new PlayerScore[size];
        }
    };

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTimeStampString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    public String getTimeLeftString() {
        long seconds = timeLeft / 1000;
        return String.format("End Time: %02d:%02d", seconds / 60, seconds % 60);
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public String getPlayerName() {
        return playerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(playerName);
        parcel.writeLong(timestamp);
        parcel.writeLong(timeLeft);
        parcel.writeInt(score);
        /*boolean collected = powerUp.checkPowerUpCollision(col, row);
                if (collected) {
                    powerUp.applyPowerUp();
                }*/
    }


}