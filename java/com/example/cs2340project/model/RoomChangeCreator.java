package com.example.cs2340project.model;

import android.widget.ImageView;


public class RoomChangeCreator implements PowerUpCreator {
    @Override
    public PowerUpDecorator createPowerUp(ImageView powerUpImageView, int x, int y, int row, int col, Player player) {
        return new RoomChangeDecorator(powerUpImageView, x, y, row, col, player);
    }
}