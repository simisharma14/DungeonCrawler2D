package com.example.cs2340project.model;

import android.widget.ImageView;

public class SkeletonCreator implements EnemyCreator {
    @Override
    public Enemy createEnemy(ImageView enemyImageView, int x, int y, int row, int col) {
        return new Skeleton(enemyImageView, x, y, row, col);
    }
}
