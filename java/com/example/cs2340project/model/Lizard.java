
package com.example.cs2340project.model;
import android.util.Log;
import android.widget.ImageView;

import com.example.cs2340project.R;

import java.util.ArrayList;

public class Lizard implements Enemy, EnemyObserver {
    private ImageView enemyImageView;
    private int health;
    private int row;
    private int col;
    private int x;
    private int y;
    private final int SPEED = 6;
    private int startTime;
    private boolean movingUp = true;
    private int playerX;
    private int playerY;
    private int playerRow;

    private int playerCol;

    private ArrayList<EnemyObserver> enemyObservers = new ArrayList<>();

    public Lizard(ImageView enemyImageView, int x, int y, int row, int col) {
        this.enemyImageView = enemyImageView;
        this.enemyImageView.setImageResource(R.drawable.lizard);
        this.enemyImageView.setX(x);
        this.enemyImageView.setY(y);
        this.enemyImageView.setElevation(row);
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.startTime = (int) System.currentTimeMillis();
    }
    public Lizard(int x, int y, int row, int col) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.startTime = (int) System.currentTimeMillis();
    }
    @Override
    public void move() {
        int time = (10 - SPEED) * 500;
        if (((int) System.currentTimeMillis()) - startTime >= time) {
            if (movingUp) {
                if (checkBounds(col + 1, row)) { //added bounds check
                    this.x -= 88;
                    this.col += 1;
                    movingUp = false;
                }

            } else {
                if (checkBounds(col - 1, row)) {
                    this.x += 88;
                    this.col -= 1;
                    movingUp = true;
                }

            }

            // Update the position of the ImageView
            enemyImageView.setX(this.x);
            Log.d("MoveENEMY", "Enemy Moved.");
            Log.d("EnemyPos", "Enemy position: " + col + ", " + row);

            this.startTime = (int) System.currentTimeMillis();
        }

    }

    public ImageView getEnemyImageView() {
        return this.enemyImageView;
    }

    public int[] getPosition() {
        int[] arr = new int[4];
        arr[0] = this.x;
        arr[1] = this.y;
        arr[2] = this.col;
        arr[3] = this.row;
        return arr;
    }


    public boolean checkCollision() {
        int[] enemyPos = getPosition();
        Log.d("CollisionCheck", "Player position: " + playerCol + ", " + playerRow);
        Log.d("CollisionCheck", "Enemy position: " + enemyPos[2] + ", " + enemyPos[3]);
        if (playerCol == enemyPos[2]
                && playerRow == enemyPos[3]) {
            return true;
        }
        return false;
    }

    public boolean update() {
        Log.d("Update", "updated");
        return checkCollision();
    }

    public void playerMoved(int x, int y, int row, int col) {
        playerX = x;
        playerY = y;
        playerRow = row;
        playerCol = col;
    }

    public int getSpeed() {
        return this.SPEED;
    }

    public int getImageViewId() {
        return R.drawable.lizard;
    }
    public boolean checkBounds(int newRow, int newCol) {
        if (newRow == 0 || newRow == 15) {
            return false;
        } else if (newCol == 0 || newCol == 12) {
            return false;
        }
        return true;
    }

}