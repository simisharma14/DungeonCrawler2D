package com.example.cs2340project.model;

public class Room {
    private int roomType;
    private Tile[][] tileRoom;
    private final int[][] rOOM1 = {
            {7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 7, 7},
            {9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10},
            {9, 13, 11, 1, 2, 1, 1, 1, 2, 1, 13, 1, 10},
            {9, 1, 11, 1, 2, 1, 12, 1, 2, 1, 1, 1, 10},
            {9, 1, 11, 1, 2, 1, 1, 1, 2, 1, 14, 1, 10},
            {9, 11, 11, 1, 2, 1, 1, 1, 2, 1, 1, 1, 10},
            {9, 1, 1, 1, 1, 12, 12, 12, 12, 1, 1, 1, 10},
            {9, 1, 1, 1, 14, 11, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 1, 1, 2, 11, 1, 1, 14, 1, 1, 1, 10},
            {9, 1, 1, 11, 11, 11, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 1, 1, 2, 1, 1, 1, 11, 1, 1, 1, 10},
            {9, 1, 1, 1, 2, 1, 1, 1, 11, 1, 1, 1, 10},
            {9, 1, 1, 13, 2, 1, 1, 1, 11, 1, 1, 13, 10},
            {9, 14, 1, 1, 2, 1, 1, 1, 11, 11, 11, 11, 10},
            {9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10},
            {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}
    };
    private final int[][] rOOM2 = {
            {7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 7, 7},
            {9, 2, 2, 2, 2, 2, 2, 17, 2, 2, 2, 2, 10},
            {9, 11, 11, 11, 11, 1, 1, 1, 2, 1, 4, 1, 10},
            {9, 1, 1, 1, 2, 1, 4, 1, 1, 14, 1, 1, 10},
            {9, 17, 17, 17, 17, 17, 1, 1, 1, 1, 1, 4, 10},
            {9, 1, 1, 1, 18, 17, 1, 1, 1, 13, 1, 1, 10},
            {9, 1, 17, 17, 17, 17, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 10},
            {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 1, 1, 11, 1, 4, 17, 1, 13, 1, 1, 10},
            {9, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 1, 1, 11, 1, 1, 1, 1, 14, 1, 1, 10},
            {9, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 17, 1, 1, 2, 1, 1, 1, 1, 13, 1, 1, 10},
            {9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10},
            {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}
    };
    private final int[][] rOOM3 = {
            {7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 7, 7, 7},
            {9, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 17, 1, 11, 1, 1, 14, 2, 13, 1, 14, 10},
            {9, 1, 17, 1, 11, 1, 4, 1, 2, 1, 1, 1, 10},
            {9, 1, 17, 1, 11, 12, 12, 1, 2, 11, 1, 1, 10},
            {9, 1, 17, 1, 2, 1, 1, 1, 2, 11, 1, 1, 10},
            {9, 1, 17, 1, 14, 1, 4, 1, 1, 11, 1, 1, 10},
            {9, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 10},
            {9, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 17, 1, 11, 1, 1, 4, 14, 14, 4, 1, 10},
            {9, 1, 17, 1, 2, 1, 1, 1, 1, 1, 1, 1, 10},
            {9, 1, 17, 1, 2, 1, 12, 12, 12, 12, 11, 1, 10},
            {9, 1, 17, 1, 2, 1, 1, 1, 1, 1, 11, 18, 10},
            {9, 18, 17, 1, 12, 12, 2, 12, 1, 1, 11, 1, 10},
            {9, 2, 1, 2, 2, 2, 2, 2, 2, 2, 11, 1, 10},
            {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}

    };

    public Room(int roomType) {
        this.roomType = roomType;
        this.tileRoom = new Tile[16][13];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 13; j++) {
                switch (roomType) {
                case 1:
                    tileRoom[i][j] = new Tile(rOOM1[i][j]);
                    break;
                case 2:
                    tileRoom[i][j] = new Tile(rOOM2[i][j]);
                    break;
                case 3:
                    tileRoom[i][j] = new Tile(rOOM3[i][j]);
                    break;
                default:
                    tileRoom[i][j] = new Tile(rOOM1[i][j]);
                    break;

                }
            }
        }
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 13; j++) {
                switch (roomType) {
                case 1:
                    tileRoom[i][j] = new Tile(rOOM1[i][j]);
                    break;
                case 2:
                    tileRoom[i][j] = new Tile(rOOM2[i][j]);
                    break;
                case 3:
                    tileRoom[i][j] = new Tile(rOOM3[i][j]);
                    break;
                default:
                    tileRoom[i][j] = new Tile(rOOM1[i][j]);
                    break;

                }
            }
        }
    }

    public int getRoomType() {
        return this.roomType;
    }

    public Tile getTile(int row, int col) {
        return tileRoom[row][col];
    }

    public void setTile(int row, int col, Tile tileToAdd) {
        if (tileToAdd.getType() < 1 || tileToAdd.getType() > 21) {
            throw new IllegalArgumentException("Tile type unavailable.");
        }
        tileRoom[row][col] = new Tile(tileToAdd.getType());

    }

    public void nextRoom() {
        if (this.roomType < 3) {
            setRoomType(this.roomType + 1);
        }
    }
}