package com.example.cs2340project.model;
import android.widget.ImageView;

public class LizardCreator implements EnemyCreator {
    @Override
    public Enemy createEnemy(ImageView enemyImageView, int x, int y, int row, int col) {
        return new Lizard(enemyImageView, x, y, row, col);
    }
}
