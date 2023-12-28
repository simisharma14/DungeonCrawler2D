package com.example.cs2340project.model;

//import android.content.Context;
import android.widget.ImageView;

//import com.example.cs2340project.viewmodel.RoomViewModel;

public class HealthCreator implements PowerUpCreator {
    @Override
    public PowerUpDecorator createPowerUp(ImageView powerUpImageView, int x, int y, int row, int col, Player player) {
        return new HealthDecorator(powerUpImageView, x, y, row, col, player);
    }
}