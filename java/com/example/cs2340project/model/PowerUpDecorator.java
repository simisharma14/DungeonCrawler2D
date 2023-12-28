package com.example.cs2340project.model;

import android.widget.ImageView;

public class PowerUpDecorator implements PowerUp {
    protected PowerUp wrappee;
    //private ImageView powerUpImageView;

    public void applyPowerUp() {
        wrappee.applyPowerUp();
    }

    public int[] getPosition() {
        return wrappee.getPosition();
    }

    public boolean checkPowerUpCollision(int col, int row) {
        return wrappee.checkPowerUpCollision(col, row);
    }

    public ImageView getPowerUpImageView() {
        return wrappee.getPowerUpImageView();
    }

    public void playerMoved(int x, int y, int row, int col) {
        wrappee.playerMoved(x, y, row, col);
    }

    public boolean update() {
        return wrappee.update();
    }

}



