package com.example.cs2340project.model;

import android.widget.ImageView;

public class TimeBoostCreator implements PowerUpCreator {
    @Override
    public PowerUpDecorator createPowerUp(ImageView powerUpImageView, int x, int y, int row, int col, Player player) {
        return new TimeBoostDecorator(powerUpImageView, x, y, row, col, player);
    }
}
