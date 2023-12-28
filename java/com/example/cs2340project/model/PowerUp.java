package com.example.cs2340project.model;

import android.widget.ImageView;

public interface PowerUp {
    public void applyPowerUp();
    public int[] getPosition();
    public boolean update();
    public boolean checkPowerUpCollision(int col, int row);
    public ImageView getPowerUpImageView();
    public void playerMoved(int x, int y, int row, int col);
    //public PowerUp getPowerUp();

}