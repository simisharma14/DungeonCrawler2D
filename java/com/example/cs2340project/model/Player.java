package com.example.cs2340project.model;

import android.content.Context;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs2340project.R;
import com.example.cs2340project.view.GamesActivity;
import com.example.cs2340project.viewmodel.RoomViewModel;

import java.util.ArrayList;

import java.util.List;

public class Player implements Subject {
    private int initX;
    private int initY;
    private int health;
    private int row;
    private int col;
    private int initRow;
    private int initCol;
    private int x;
    private int y;
    private String message;
    private ArrayList<MovementObserver> observers = new ArrayList<>();
    private TextView playerHealthTextView;

    private ArrayList<EnemyObserver> enemiesObservers = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private PowerUp powerUp;

    private final Context context;
    private RoomViewModel roomViewModel;
    private MovementStrategy movementStrategy;
    //private TextView playerHealthTextView;
    private boolean won;

    private int selectedDifficulty;

    private boolean notified;
    private Enemy enemy;
    private ImageView sword;


    private List<PowerUp> powerUpList = new ArrayList<>();
    private PlayerScore playerScore;



    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public Player(int x, int y, int row, int col, int health, int selectedDifficulty, RoomViewModel roomViewModel, Context context, PlayerScore playerScore) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.row = row;
        this.col = col;
        this.initRow = row;
        this.initCol = col;
        this.health = health;
        this.roomViewModel = roomViewModel;
        this.context = context;
        this.selectedDifficulty = selectedDifficulty;
        this.playerScore = playerScore;
        //setPowerUp(powerUp);
    }
    public Player(int x, int y, int row, int col, int health, int selectedDifficulty, RoomViewModel roomViewModel, Context context) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.row = row;
        this.col = col;
        this.initRow = row;
        this.initCol = col;
        this.health = health;
        this.roomViewModel = roomViewModel;
        this.context = context;
        this.selectedDifficulty = selectedDifficulty;
    }

    public Player(int x, int y, int row, int col, int health, Context context) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.row = row;
        this.col = col;
        this.initRow = row;
        this.initCol = col;
        this.health = health;
        this.context = context;
    }



    public Player(int x, int y, int row, int col, int health, int selectedDifficulty, RoomViewModel roomViewModel, Context context, Enemy enemy) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.row = row;
        this.col = col;
        this.initRow = row;
        this.initCol = col;
        this.health = health;
        this.roomViewModel = roomViewModel;
        this.context = context;
        this.selectedDifficulty = selectedDifficulty;
        this.enemy = enemy;
    }

    public void setSwordImageView(ImageView swordImageView) {
        Log.i("ImageView sword", "in Player.java");
        this.sword = swordImageView;
        Log.i("ImageView sword", "set this.sword = swordImageView");
        this.sword.setImageResource(R.drawable.sword);
        this.sword.setX(this.x + 10);
        this.sword.setY(this.y + 10);
        this.sword.setElevation(14);
    }

    public ImageView getSwordImageView() {
        return this.sword;
    }

    public void moveSword() {
        this.sword.setX(this.x + 10);
        this.sword.setY(this.y + 3);
    }

    public void attack() {
        Log.i("attack()", "attacking now");
        this.sword.setImageResource(R.drawable.sword2);
        for (int i = 0; i < enemiesObservers.size(); i++) {
            EnemyObserver observer = enemiesObservers.get(i);
            if ((observer.getPosition()[3] == this.row && (observer.getPosition()[2] == this.col + 1 || observer.getPosition()[2] == this.col - 1)) || (observer.getPosition()[2] == this.col && (observer.getPosition()[3] == this.row + 1 || observer.getPosition()[3] == this.row - 1))) {
                Log.i("Sword Player Pos", Integer.toString(this.row));
                Log.i("Sword Enemy Pos", Integer.toString(observer.getPosition()[3]));
                if (this.context != null) {
                    GamesActivity.getInstance().killEnemy(observer);
                }
                enemiesObservers.remove(i);
            }
        }

        if (this.context != null) {
            observers.get(0).animateAttack(sword);
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void playerAttack() {
        enemy = null;
        setMessage("null");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (31 <= x && x <= 911) {
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (75 <= y && y <= 1307) {
            this.y = y;
        }
    }

    public boolean getWon() {
        return won;
    }
    public void setWon(boolean wonUpdate) {
        this.won = wonUpdate;
    }


    public void moveUp() {
        if (checkBounds(row - 1, col)) {
            int newTile = roomViewModel.getRoomTileType(row - 1, col);
            if (newTile == 1 || newTile == 2) {
                movementStrategy.moveUp(this);
                Log.d("MoveUP", "Player Moved Up.");
                Log.d("PlayerPos", "Player position: " + col + ", " + row);
                notifyObservers();
                if (this.context != null) {
                    moveSword();
                }
                notifyEnemy();
                notified = true;
                for (EnemyObserver enemy : enemiesObservers) {
                    boolean collided =  enemy.checkCollision();
                    if (collided) {
                        reduceHealth();
                        Log.d("Player", "Collision detected! Health reduced.");
                    }
                }
                checkPowerUpCollision();
            }
        } else if (checkDoor(row - 1, col)) {
            if (roomViewModel.getRoomNumber() < 3) {
                roomViewModel.nextRoom();
                this.x = initX;
                this.y = initY;
                this.row = initRow;
                this.col = initCol;
                Log.d("MoveUP", "Player Moved Up.");
                Log.d("PlayerPos", "Player position: " + col + ", " + row);
                notifyObservers();
                if (this.context != null) {
                    moveSword();
                }
                notifyEnemy();
                for (EnemyObserver enemy : enemiesObservers) {
                    boolean collided =  enemy.checkCollision();
                    if (collided) {
                        reduceHealth();
                        Log.d("Player", "Collision detected! Health reduced.");
                    }
                }

                checkPowerUpCollision();
            } else {
                GamesActivity.getInstance().playerWon();

            }
        }
    }

    public void moveDown() {
        if (checkBounds(row + 1, col)) {
            int newTile = roomViewModel.getRoomTileType(row + 1, col);
            if (newTile == 1 || newTile == 2) {
                movementStrategy.moveDown(this);
                Log.d("MoveDOWN", "Player Moved Down.");
                Log.d("PlayerPos", "Player position: " + col + ", " + row);
                notifyObservers();
                if (this.context != null) {
                    moveSword();
                }
                notifyEnemy();
                for (EnemyObserver enemyObserver : enemiesObservers) {
                    boolean collided = enemyObserver.update();
                    if (collided) {
                        reduceHealth();
                        Log.d("Player", "Collision detected! Health reduced.");
                    }
                }
                checkPowerUpCollision();
            }
        }
    }

    public void moveLeft() {
        if (checkBounds(row, col - 1)) {
            int newTile = roomViewModel.getRoomTileType(row, col - 1);
            if (newTile == 1 || newTile == 2) {
                movementStrategy.moveLeft(this);
                Log.d("MoveLEFT", "Player Moved Left.");
                Log.d("PlayerPos", "Player position: " + col + ", " + row);
                notifyObservers();
                if (this.context != null) {
                    moveSword();
                }
                notifyEnemy();
                for (EnemyObserver enemyObserver : enemiesObservers) {
                    boolean collided = enemyObserver.update();
                    if (collided) {
                        reduceHealth();
                        Log.d("Player", "Collision detected! Health reduced.");
                    }
                }
                checkPowerUpCollision();
            }
        }
    }

    public void moveRight() {
        if (checkBounds(row, col + 1)) {
            int newTile = roomViewModel.getRoomTileType(row, col + 1);
            if (newTile == 1 || newTile == 2) {
                movementStrategy.moveRight(this);
                Log.d("MoveRIGHT", "Player Moved Right.");
                Log.d("PlayerPos", "Player position: " + col + ", " + row);
                notifyObservers();
                if (this.context != null) {
                    moveSword();
                }
                notifyEnemy();
                for (EnemyObserver enemyObserver : enemiesObservers) {
                    System.out.println(enemyObserver);
                    boolean collided = enemyObserver.update();
                    if (collided) {
                        reduceHealth();
                        Log.d("Player", "Collision detected! Health reduced.");
                    }
                }

                checkPowerUpCollision();
            }
        }
    }

    public void moveDiagonal() {
        return;
    }

    public boolean checkBounds(int newRow, int newCol) {
        if (newRow == 0 || newRow == 15) {
            return false;
        } else if (newCol == 0 || newCol == 12) {
            return false;
        }
        return true;
    }

    public boolean checkDoor(int newRow, int newCol) {
        if (newRow == 0 && newCol == 9) {
            return true;
        }
        return false;
    }

    @Override
    public void registerObserver(MovementObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(MovementObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (MovementObserver observer : observers) {
            observer.playerMoved(x, y, row, col);
        }
    }

    public void registerEnemy(EnemyObserver enemy) {
        enemiesObservers.add(enemy);
    }

    public void unregisterEnemy(EnemyObserver  observer) {
        enemiesObservers.remove(observer);
    }

    public void registerPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void unregisterPowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }


    public void notifyEnemy() {
        for (EnemyObserver enemy : enemiesObservers) {
            enemy.playerMoved(x, y, row, col);
        }
    }

    public void notifyPowerUp() {
        for (PowerUp powerUp : powerUps) {
            powerUp.playerMoved(x, y, row, col);
        }
    }

    private void checkPowerUpCollision() {
        Log.d("PowerUpCollision", "checkPowerUpCollision() called.");
        Log.d("PowerUp", "PowerUps:" + powerUps);
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            Log.d("PowerUp", "PowerUp:" + powerUp);
            boolean collected =  powerUp.checkPowerUpCollision(getCol(), getRow());
            Log.d("Check", "Collected:" + collected);
            if (collected) {
                applyPowerUp(powerUp);
                removePowerUp(powerUp);
                powerUps.remove(i);
            }
        }

    }

    private void applyPowerUp(PowerUp powerUp) {
        if (powerUp != null) {
            powerUp.applyPowerUp();
        }
    }

    private void removePowerUp(PowerUp powerUp) {
        if (powerUp != null) {
            if (this.context != null) {
                GamesActivity.getInstance().killPowerUp(powerUp);
            }
        }
    }


    public int[] getPlayerPos() {
        int[] arr = new int[4];
        arr[0] = this.x;
        arr[1] = this.y;
        arr[2] = this.col;
        arr[3] = this.row;
        return arr;
    }

    public void playerMoved() {
        for (EnemyObserver observer : enemiesObservers) {
            observer.playerMoved(x, y, row, col);
        }
    }

    public void reduceHealth() {
        int diff = ((GamesActivity) context).getDifficulty();

        if (diff == 1) {
            health -= 2;
        } else if (diff == 2) {
            health -= 5;
        } else {
            health -= 10;
        }
        setHealth(health);

        if (this.health <= 0) {
            Log.i("healthCheck", "calling playerLost()");
            GamesActivity.getInstance().playerLost();
        }
    }

    public void reduceHP() {
        int difficultyLevel = selectedDifficulty;

        if (difficultyLevel == 1) {
            health -= 2;
        } else if (difficultyLevel == 2) {
            health -= 5;
        } else {
            health -= 10;
        }
        setHealth(health);

    }

    public void setHealth(int health) {
        this.health = health;

    }

    public int getDiff() {
        return selectedDifficulty;
    }
    public void setDiff(int diff) {
        selectedDifficulty = diff;
    }


    public String getHealth() {
        return "" + health;
    }
    public int getHealthInt() {
        return health;
    }


    public int getIntHealth() {
        return health;
    }

    public Context getContext() {
        return context;
    }

    public boolean getNotified() {
        return notified;
    }


    public RoomViewModel getGameRoomViewModel() {
        return ((GamesActivity) context).getRoomViewModel();
    }

    public PlayerScore getPlayerScore() {
        return ((GamesActivity) context).getPlayerScore();
    }

    public void setGameRoomViewModel(RoomViewModel roomViewModel) {
        ((GamesActivity) context).setRoomViewModel(roomViewModel);
    }

    public void increaseTimer() {
        ((GamesActivity) context).increaseTimerByMinute();
    }

    public Player getGamePlayer() {
        return ((GamesActivity) context).getPlayer();
    }

}