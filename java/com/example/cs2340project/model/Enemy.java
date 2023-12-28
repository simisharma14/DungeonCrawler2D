package com.example.cs2340project.model;

import android.widget.ImageView;

public interface Enemy extends EnemyObserver {
    public void move();

    public ImageView getEnemyImageView();

    public int[] getPosition();

    public boolean update();

    public int getSpeed();
    public int getImageViewId();
    public boolean checkBounds(int row, int col);

}