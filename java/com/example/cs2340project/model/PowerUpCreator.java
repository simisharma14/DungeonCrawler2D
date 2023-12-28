package com.example.cs2340project.model;


import android.widget.ImageView;


public interface PowerUpCreator {
    public PowerUpDecorator createPowerUp(ImageView powerUpImageView, int x, int y, int row, int col, Player player);
}