package com.example.cs2340project.model;

import android.widget.ImageView;

public class DoctorCreator implements EnemyCreator {
    @Override
    public Enemy createEnemy(ImageView enemyImageView, int x, int y, int row, int col) {
        return new Doctor(enemyImageView, x, y, row, col);
    }
}

