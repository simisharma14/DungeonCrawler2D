package com.example.cs2340project.model;

import android.widget.ImageView;

public interface EnemyObserver {

    void playerMoved(int x, int y, int row, int col);
    boolean update();

    int[] getPosition();

    public boolean checkCollision();
    public ImageView getEnemyImageView();
}