package com.example.cs2340project.model;

import android.widget.ImageView;

public interface MovementObserver {
    public void playerMoved(int x, int y, int row, int col);
    public void animateAttack(ImageView swordImage);
}
