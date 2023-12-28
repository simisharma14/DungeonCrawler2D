package com.example.cs2340project.model;

import android.widget.ImageView;

public interface EnemyCreator {
    public Enemy createEnemy(ImageView enemyImageView, int x, int y, int row, int col);
}
