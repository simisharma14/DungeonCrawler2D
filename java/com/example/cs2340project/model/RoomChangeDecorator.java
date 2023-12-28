package com.example.cs2340project.model;

import android.util.Log;
import android.widget.ImageView;
import com.example.cs2340project.R;
import com.example.cs2340project.viewmodel.RoomViewModel;

public class RoomChangeDecorator extends PowerUpDecorator {
    private ImageView powerUpImageView;
    private int health;
    private int row;
    private int col;
    private int x;
    private int y;
    //private final int SPEED = 1;
    private int startTime;

    //private boolean movingUp = true;

    private Player player;

    private int playerX;

    private int playerY;
    private int playerRow;

    private int playerCol;

    //private final Context context;
    private RoomViewModel roomViewModel;

    private Room room;

    /*public RoomChangeDecorator(Player player) {
        this.player = player;
    }*/

    public RoomChangeDecorator(ImageView powerUpImageView, int x, int y, int row, int col, Player player) {
        this.powerUpImageView = powerUpImageView;
        this.powerUpImageView.setImageResource(R.drawable.roomchange_two);
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
    public RoomChangeDecorator(int x, int y, int row, int col, Player player) {
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
        return this.powerUpImageView;
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

    @Override
    public void playerMoved(int x, int y, int row, int col) {
        playerX = x;
        playerY = y;
        playerRow = row;
        playerCol = col;
    }

    /*@Override
    public boolean update() {
        Log.d("Update", "updated");
        return checkPowerUpCollision();
    }*/

    @Override
    public boolean checkPowerUpCollision(int playerCol, int playerRow) {
        int[] powerUpPos = getPosition();
        //int[] playerPos = player.getPlayerPos();
        Log.d("PowerUpCollisionCheck", "Player position: " + playerCol + ", " + playerRow);
        Log.d("PowerUpCollisionCheck", "PowerUp position: " + powerUpPos[2] + ", " + powerUpPos[3]);
        if (playerCol == powerUpPos[2] && playerRow == powerUpPos[3]) {
            return true;
        }
        return false;
    }

    @Override
    public void applyPowerUp() {
        Log.d("RoomChange", "PowerUp colleted!");
        roomViewModel = player.getGameRoomViewModel();
        roomViewModel.nextRoom();
    }

}