package com.example.cs2340project.model;

import com.example.cs2340project.R;

public class Tile {
    private int type;
    private int imageId;

    public Tile(int tileType) {
        this.type = tileType;
        switch (tileType) {
        case 1:
            this.imageId = R.drawable.floor_1;
            break;
        case 2:
            this.imageId = R.drawable.floor_2;
            break;
        case 3:
            this.imageId = R.drawable.wall_mid;
            break;
        case 4:
            this.imageId = R.drawable.hole;
            break;
        case 5:
            this.imageId = R.drawable.wall_hole_1;
            break;
        case 6:
            this.imageId = R.drawable.crate;
            break;
        case 7:
            this.imageId = R.drawable.horiztopwall;
            break;
        case 8:
            this.imageId = R.drawable.horizbottomwall;
            break;
        case 9:
            this.imageId = R.drawable.verticalleftwall;
            break;
        case 10:
            this.imageId = R.drawable.verticalrightwall;
            break;
        case 11:
            this.imageId = R.drawable.verticalblock;
            break;
        case 12:
            this.imageId = R.drawable.horizblock;
            break;
        case 13:
            this.imageId = R.drawable.waterblock;
            break;
        case 14:
            this.imageId = R.drawable.fire_block;
            break;
        case 15:
            this.imageId = R.drawable.acid_block;
            break;
        case 16:
            this.imageId = R.drawable.jewels_block;
            break;
        case 17:
            this.imageId = R.drawable.bomb_block;
            break;
        case 18:
            this.imageId = R.drawable.gold_block;
            break;
        case 19:
            this.imageId = R.drawable.health;
            break;
        case 20:
            this.imageId = R.drawable.timeboost;
            break;
        case 21:
            this.imageId = R.drawable.roomchange;
            break;
        default:

        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public int getImageId() {
        return this.imageId;
    }


}