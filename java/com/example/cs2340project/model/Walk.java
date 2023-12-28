package com.example.cs2340project.model;

/** @noinspection checkstyle:WhitespaceAround*/
public class Walk implements MovementStrategy {

    @Override
    public void moveUp(Player player) {
        int currY = player.getY();
        int currRow = player.getRow();
        player.setY(currY - 88);
        player.setRow(currRow - 1);
        player.setHealth(Integer.valueOf(player.getHealth()));
    }

    @Override
    public void moveDown(Player player) {
        int currY = player.getY();
        int currRow = player.getRow();
        player.setY(currY + 88);
        player.setRow(currRow + 1);
        player.setHealth(Integer.valueOf(player.getHealth()));
    }

    @Override
    public void moveLeft(Player player) {
        int currX = player.getX();
        int currCol = player.getCol();
        player.setX(currX - 88);
        player.setCol(currCol - 1);
        player.setHealth(Integer.valueOf(player.getHealth()));
    }

    @Override
    public void moveRight(Player player) {
        int currX = player.getX();
        int currCol = player.getCol();
        player.setX(currX + 88);
        player.setCol(currCol + 1);
        player.setHealth(Integer.valueOf(player.getHealth()));
    }
}