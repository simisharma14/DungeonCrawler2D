package com.example.cs2340project.view;

import android.os.Handler;
import android.widget.ImageView;

import com.example.cs2340project.R;
import com.example.cs2340project.model.MovementObserver;

public class PlayerDisplay implements MovementObserver {

    private ImageView playerSpriteImageView;

    public PlayerDisplay(ImageView playerSpriteImageView) {
        this.playerSpriteImageView = playerSpriteImageView;
    }
    @Override
    public void playerMoved(int x, int y, int row, int col) {
        playerSpriteImageView.setX(x);
        playerSpriteImageView.setY(y);
        playerSpriteImageView.setElevation(row);
    }

    public void animateAttack(ImageView swordImage) {
        Handler attackHandler = new Handler();
        attackHandler.postDelayed(() -> {
            swordImage.setImageResource(R.drawable.sword);
        }, 400);
    }
}
