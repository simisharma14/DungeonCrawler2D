package com.example.cs2340project.model;

import android.util.Log;
import android.widget.ImageView;

import com.example.cs2340project.R;

public class HealthDecorator extends PowerUpDecorator {
    private static ImageView powerUpImageView;
    //private int health;
    private int row;
    private int col;
    private int x;
    private int y;
    private int startTime;

    //private boolean movingUp = true;

    private Player player;

    private int playerX;

    private int playerY;
    private int playerRow;

    private int playerCol;

    //private final Context context;
    //private RoomViewModel roomViewModel;

    /*public HealthDecorator(Player player) {
        this.player = player;
    }*/


    public HealthDecorator(ImageView powerUpImageView, int x, int y, int row, int col, Player player) {
        this.powerUpImageView = powerUpImageView;
        this.powerUpImageView.setImageResource(R.drawable.health_two);
        this.powerUpImageView.setX(x);
        this.powerUpImageView.setY(y);
        this.powerUpImageView.setElevation(row);
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.player = player;
        //this.roomViewModel = roomViewModel;
        //this.context = context;
        this.startTime = (int) System.currentTimeMillis();
    }

    public HealthDecorator(int x, int y, int row, int col, Player player) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.player = player;
        //this.roomViewModel = roomViewModel;
        //this.context = context;
        this.startTime = (int) System.currentTimeMillis();
    }


    public ImageView getPowerUpImageView() {
        return powerUpImageView;
    }

    @Override
    public int[] getPosition() {
        int[] arr = new int[4];
        arr[0] = this.x;
        arr[1] = this.y;
        arr[2] = this.col;
        arr[3] = this.row;
        return arr;
    }

    /*@Override
    public boolean update() {
        Log.d("Update", "updated");
        return checkPowerUpCollision();
    }*/

    @Override
    public void playerMoved(int x, int y, int row, int col) {
        playerX = x;
        playerY = y;
        playerRow = row;
        playerCol = col;
    }

    @Override
    public boolean checkPowerUpCollision(int playerCol, int playerRow) {
        int[] powerUpPos = getPosition();
        Log.d("PowerUpCollisionCheck", "Player position: " + playerCol + ", " + playerRow);
        Log.d("PowerUpCollisionCheck", "PowerUp position: " + powerUpPos[3] + ", " + powerUpPos[2]);
        if (playerCol == powerUpPos[3] && playerRow == powerUpPos[2]) {
            return true;
        }
        return false;
    }

    @Override
    public void applyPowerUp() {
        /*if (checkPowerUpCollision()) {
            player.setHealth(Integer.parseInt(player.getHealth() + 10));
        }*/
        Player new_player = player.getGamePlayer();
        new_player.setHealth(new_player.getIntHealth() + 10);
    }

    public void applyHealthPowerUp() {
        player.setHealth(player.getIntHealth() + 10);
    }



}