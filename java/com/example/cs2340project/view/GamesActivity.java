package com.example.cs2340project.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cs2340project.model.DoctorCreator;
import com.example.cs2340project.model.Enemy;
import com.example.cs2340project.model.EnemyCreator;
import com.example.cs2340project.model.PowerUpCreator;
import com.example.cs2340project.model.PowerUpDecorator;
import com.example.cs2340project.model.PowerUp;
import com.example.cs2340project.model.HealthCreator;
import com.example.cs2340project.model.RoomChangeCreator;

import com.example.cs2340project.model.EnemyObserver;
import com.example.cs2340project.model.GoblinCreator;
import com.example.cs2340project.model.LizardCreator;
import com.example.cs2340project.model.MovementStrategy;

import com.example.cs2340project.model.Goblin;
import com.example.cs2340project.model.TimeBoostCreator;

import com.example.cs2340project.model.Skeleton;

import com.example.cs2340project.model.Player;
import com.example.cs2340project.model.SkeletonCreator;
import com.example.cs2340project.model.Walk;
import com.example.cs2340project.viewmodel.LeaderboardViewModel;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.R;
import com.example.cs2340project.model.Room;
import com.example.cs2340project.viewmodel.RoomViewModel;
import com.example.cs2340project.model.Tile;

import java.util.ArrayList;
import java.util.Date;

public class GamesActivity extends AppCompatActivity {
    private TextView playerNameTextView;
    private TextView difficultyLevelTextView;
    private TextView playerHealthTextView;
    private ImageView playerSpriteImageView;
    private Button endScreenButton;
    private Button nextButton;
    private RoomViewModel roomViewModel;
    private TextView timerTextView;
    private CountDownTimer timer;
    private long remainingTime = 30000;
    private LeaderboardViewModel leaderViewModel;
    private PlayerScore playerScore;

    private Player player;
    private int tableTop;
    private int tableLeft;
    private String playerName;

    //remove
    private TextView testText;

    private MovementStrategy walk;
    private static GamesActivity instance;

    //new stuff:
    private Enemy[] enemies;
    private PowerUpDecorator[] powerUpDecorators;
    private ConstraintLayout constraintLayout;

    private int selectedDifficulty;

    private ArrayList<EnemyObserver> enemyObserver;
    private ArrayList<PowerUp> powerUp;

    private int difficulty;
    private ImageView sword;

    private Handler handler = new Handler();
    private Runnable moveEnemiesRunnable = new Runnable() {
        @Override
        public void run() {
            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    enemy.move();
                }
            }
            handler.postDelayed(this, 100); // Schedule the next call after 100ms
        }
    };

    public Player getPlayer() {
        return player;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "onCreate");
        setContentView(R.layout.activity_games);

        //added:
        this.powerUpDecorators = new PowerUpDecorator[3];
        this.enemies = new Enemy[2];
        this.constraintLayout = findViewById(R.id.constraintLayout);

        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        roomViewModel.getRoom().observe(this, room -> {
            updateRoom(room);
        });

        playerNameTextView = findViewById(R.id.playerName);
        playerSpriteImageView = findViewById(R.id.playerSprite);
        difficultyLevelTextView = findViewById(R.id.difficultyLevel);
        playerHealthTextView = findViewById(R.id.playerHealth);
        timerTextView = findViewById(R.id.timerTextView);

        this.playerName = getIntent().getStringExtra("playerName");
        this.selectedDifficulty = getIntent().getIntExtra("selectedDifficulty", -1);
        int selectedSprite = getIntent().getIntExtra("selectedSprite", -1);
        int startingHP = getIntent().getIntExtra("startingHP", 100);


        ArrayList<PlayerScore> updatedList =
                getIntent().getParcelableArrayListExtra("playerScoresList");
        leaderViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        leaderViewModel.updateLeaderboard(updatedList);


        if (this.selectedDifficulty == R.id.radioEasy) {
            setDifficulty(1);
            difficulty = 1;
            difficultyLevelTextView.setText("Easy");
        } else if (this.selectedDifficulty == R.id.radioMedium) {
            selectedDifficulty = 2;
            setDifficulty(2);
            difficulty = 2;
            difficultyLevelTextView.setText("Medium");
        } else if (this.selectedDifficulty == R.id.radioHard) {
            selectedDifficulty = 3;
            setDifficulty(3);
            difficulty = 3;
            difficultyLevelTextView.setText("Hard");
        }

        if (selectedSprite == R.id.radioSprite1) {
            playerSpriteImageView.setImageResource(R.drawable.dwarf_sprite);
        } else if (selectedSprite == R.id.radioSprite2) {
            playerSpriteImageView.setImageResource(R.drawable.elf_sprite);
        } else if (selectedSprite == R.id.radioSprite3) {
            playerSpriteImageView.setImageResource(R.drawable.knight_sprite);
        }

        sword = new ImageView(this);
        /*ImageView healthImageView = new ImageView(this);
        ImageView timeBoostImageView = new ImageView(this);
        ImageView roomChangeImageView = new ImageView(this);*/



        //remove
        testText = findViewById(R.id.testText);

        playerSpriteImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int initX = (int) playerSpriteImageView.getX();

                int initY = (int) playerSpriteImageView.getY();

                int initRow = (initY - tableTop) / 88;
                int initCol = ((initX - tableLeft) / 88) + 1;

                player = new Player(initX, initY, initRow, initCol, startingHP, selectedDifficulty, roomViewModel,
                        GamesActivity.this, playerScore);
                MovementStrategy walk = new Walk();
                player.setMovementStrategy(walk);
                playerHealthTextView.setText(player.getHealth());

                PlayerDisplay playerDisplay = new PlayerDisplay(playerSpriteImageView);
                player.registerObserver(playerDisplay);

                for (Enemy enemy: enemies) {
                    player.registerEnemy(enemy);
                }

                for (PowerUpDecorator powerUpDecorator: powerUpDecorators) {
                    player.registerPowerUp(powerUpDecorator);
                }


                player.setSwordImageView(sword);
                constraintLayout.addView(player.getSwordImageView());

                /*PowerUpCreator powerUpCreator1 = new HealthCreator();
                PowerUpDecorator health = powerUpCreator1.createPowerUp(healthImageView,150, 765, 2, 7, player);
                //createPowerUp(health);
                powerUpDecorators[0] = health;
                player.registerPowerUp(health);
                //((HealthDecorator) powerUpDecorators[0]).checkPowerUpCollision();

                PowerUpCreator powerUpCreator2 = new TimeBoostCreator();
                PowerUpDecorator timeBoost = powerUpCreator2.createPowerUp(timeBoostImageView,230, 1350, 3, 14, player);
                //createPowerUp(scoreBoost);
                powerUpDecorators[1] = timeBoost;
                player.registerPowerUp(timeBoost);
                //((ScoreBoostDecorator) powerUpDecorators[1]).checkPowerUpCollision();

                PowerUpCreator powerUpCreator3 = new RoomChangeCreator();
                PowerUpDecorator roomChange = powerUpCreator3.createPowerUp(roomChangeImageView,830, 690, 6, 10, player);
                //createPowerUp(roomChange);
                powerUpDecorators[2] = roomChange;
                player.registerPowerUp(roomChange);
                //((RoomChangeDecorator) powerUpDecorators[2]).checkPowerUpCollision();

                createPowerUps();*/


                playerSpriteImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        instance = this;



        timer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                //long seconds = millisUntilFinished / 1000;
                //timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
                updateTimerDisplay();
            }

            @Override
            public void onFinish() {

            }
        }.start();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(moveEnemiesRunnable); // Stop moving the enemies
        Log.d("Lifecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(moveEnemiesRunnable); // Stop moving the enemies
        Log.d("Lifecycle", "onStop");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "onResume");

        super.onResume();
        Log.d("Lifecycle", "onResume");

        // Delay the check by a short time to allow the activity to fully resume
        handler.postDelayed(() -> checkRoomType(), 500);
    }

    private void checkRoomType() {
        Room gameRoom = roomViewModel.getRoom().getValue();
        Log.d("GameRoom", "Room type: ");

        if (gameRoom != null) {
            int roomType = gameRoom.getRoomType();
            Log.d("RoomType", "Room type: " + roomType);
            if (roomType == 1 || roomType == 2 || roomType == 3) {
                Log.d("MoveEnemies", "Starting enemy movement");
                handler.post(moveEnemiesRunnable);
                Log.d("Resume", "Resuming enemy movement...");

            } else {
                Log.d("MoveEnemies", "Condition not met for enemy movement");
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_LEFT:
            player.moveLeft();
            playerHealthTextView.setText(player.getHealth());
            break;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            player.moveRight();
            playerHealthTextView.setText(player.getHealth());
            break;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            player.moveDown();
            playerHealthTextView.setText(player.getHealth());
            break;
        case KeyEvent.KEYCODE_DPAD_UP:
            player.moveUp();
            playerHealthTextView.setText(player.getHealth());
            break;
        case KeyEvent.KEYCODE_SHIFT_LEFT:
            break;
        case KeyEvent.KEYCODE_SHIFT_RIGHT:
            break;
        case KeyEvent.KEYCODE_SPACE:
            player.attack();
            break;
        }

        return true;
    }

    public void setTestText() {
        testText.setText(Integer.toString(tableTop));
    }

    public static GamesActivity getInstance() {
        return instance;
    }

    public void playerWon() {
        Intent intent = new Intent(GamesActivity.this, EndingActivity.class);
        intent.putExtra("remainingTime", remainingTime);
        timer.cancel();
        leaderViewModel.setPlayerHealth(player.getHealthInt());
        leaderViewModel.setName(playerName);
        leaderViewModel.setTimeLeft(remainingTime);
        leaderViewModel.setTimeStamp();
        leaderViewModel.addToLeaderboard();

        intent.putParcelableArrayListExtra("playerScoresList",
                leaderViewModel.getCurrBoard().getScores());

        startActivity(intent);
    }

    public void playerLost() {
        Log.i("playerLost", "in playerLost()");
        Intent intent = new Intent(GamesActivity.this, GameLostActivity.class);
        intent.putExtra("remainingTime", remainingTime);
        timer.cancel();

        long timeStamp = new Date().getTime();
        int playerHealth = player.getHealthInt();

        PlayerScore tempScore = new PlayerScore(playerName, timeStamp, remainingTime, playerHealth);
        ArrayList<PlayerScore> tempList = new ArrayList<>();
        setPlayerScore(tempScore);
        tempList.add(tempScore);

        intent.putParcelableArrayListExtra("playerScoresList",
                leaderViewModel.getCurrBoard().getScores());

        intent.putParcelableArrayListExtra("tempScore", tempList);

        startActivity(intent);
    }

    public void killEnemy(EnemyObserver enemyObserver) {
        for (int i = 0; i < 2; i++) {
            if (enemies[i] != null) {
                Enemy enemy = enemies[i];
                if (enemy.getEnemyImageView().equals(enemyObserver.getEnemyImageView())) {
                    enemy.getEnemyImageView().setVisibility(View.INVISIBLE);
                    enemies[i] = null;
                }
            }
        }
    }

    public void killPowerUp(PowerUp powerUp) {
        for (int i = 0; i < 3; i++) {
            if (powerUpDecorators[i] != null) {
                PowerUpDecorator powerUpDecorator = powerUpDecorators[i];
                if (powerUpDecorator.getPowerUpImageView().equals(powerUp.getPowerUpImageView())) {
                    powerUpDecorator.getPowerUpImageView().setVisibility(View.INVISIBLE);
                    powerUpDecorators[i] = null;
                }
            }
        }
    }


    private void updateRoom(Room room) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableTop = tableLayout.getTop();
        tableLeft = tableLayout.getLeft();
        tableLayout.removeAllViews();
        removePowerUps();
        clearEnemies();

        for (int i = 0; i < 16; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    0.0f
            ));

            for (int j = 0; j < 13; j++) {
                Tile tile = room.getTile(i, j);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(tile.getImageId());

                int width = imageView.getDrawable().getIntrinsicWidth();
                int height = imageView.getDrawable().getIntrinsicHeight();
                int newWidth = (int) (width * 2);
                int newHeight = (int) (height * 2);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(newWidth, newHeight);
                imageView.setLayoutParams(layoutParams);

                imageView.setElevation(-2f);
                imageView.setPadding(0, 0, 0, 0);

                tableRow.addView(imageView);
            }

            tableLayout.addView(tableRow);
        }
        //Add enemies:

        if (room.getRoomType() == 1) {
            int initX = (int) playerSpriteImageView.getX();
            int initY = (int) playerSpriteImageView.getY();

            int initRow = (initY - tableTop) / 88;
            int initCol = ((initX - tableLeft) / 88) + 1;
            int startingHP = getIntent().getIntExtra("startingHP", 100);
            player = new Player(initX, initY, initRow, initCol, startingHP, selectedDifficulty, roomViewModel,
                    GamesActivity.this, playerScore);

            PowerUpCreator powerUpCreator1 = new HealthCreator();
            ImageView healthImageView = new ImageView(this);
            PowerUpDecorator health = powerUpCreator1.createPowerUp(healthImageView, 150, 765, 2, 7, player);
            powerUpDecorators[0] = health;
            player.registerPowerUp(health);

            PowerUpCreator powerUpCreator2 = new TimeBoostCreator();
            ImageView timeBoostImageView = new ImageView(this);
            PowerUpDecorator timeBoost = powerUpCreator2.createPowerUp(timeBoostImageView, 230, 1350, 3, 14, player);
            powerUpDecorators[1] = timeBoost;
            player.registerPowerUp(timeBoost);

            PowerUpCreator powerUpCreator3 = new RoomChangeCreator();
            ImageView roomChangeImageView = new ImageView(this);
            PowerUpDecorator roomChange = powerUpCreator3.createPowerUp(roomChangeImageView, 830, 690, 6, 10, player);
            powerUpDecorators[2] = roomChange;
            player.registerPowerUp(roomChange);


            Log.d("EnemyCreation", "enemy1");
            EnemyCreator enemyCreator1 = new SkeletonCreator();
            ImageView enemyImageView1 = new ImageView(this);
            enemyImageView1.setElevation(10);
            Enemy skeleton = enemyCreator1.createEnemy(enemyImageView1, 475, 1040, 10, 6);
            enemies[0] = skeleton;
            Log.d("Player", "player");
            player.registerEnemy(skeleton);
            ((Skeleton) enemies[0]).checkCollision();
            Log.d("Player", player.toString());


            EnemyCreator enemyCreator2 = new GoblinCreator();
            ImageView enemyImageView2 = new ImageView(this);
            enemyImageView2.setElevation(5);
            Enemy goblin = enemyCreator2.createEnemy(enemyImageView2, 387, 600, 5, 5);
            enemies[1] = goblin;
            player.registerEnemy(goblin);
            ((Goblin) enemies[1]).checkCollision();
            Log.d("Player1", player.toString());

        } else if (room.getRoomType() == 2) {
            PowerUpCreator powerUpCreator1 = new HealthCreator();
            ImageView healthImageView = new ImageView(this);
            PowerUpDecorator health = powerUpCreator1.createPowerUp(healthImageView, 150, 765, 2, 7, player);
            powerUpDecorators[0] = health;
            player.registerPowerUp(health);

            PowerUpCreator powerUpCreator2 = new TimeBoostCreator();
            ImageView timeBoostImageView = new ImageView(this);
            PowerUpDecorator timeBoost = powerUpCreator2.createPowerUp(timeBoostImageView, 230, 1350, 3, 14, player);
            powerUpDecorators[1] = timeBoost;
            player.registerPowerUp(timeBoost);

            PowerUpCreator powerUpCreator3 = new RoomChangeCreator();
            ImageView roomChangeImageView = new ImageView(this);
            PowerUpDecorator roomChange = powerUpCreator3.createPowerUp(roomChangeImageView, 830, 690, 6, 10, player);
            powerUpDecorators[2] = roomChange;
            player.registerPowerUp(roomChange);

            EnemyCreator enemyCreator1 = new LizardCreator();
            ImageView enemyImageView1 = new ImageView(this);
            enemyImageView1.setElevation(11);
            Enemy lizard = enemyCreator1.createEnemy(enemyImageView1, 471, 1040, 11, 6);
            enemies[0] = lizard;
            player.registerEnemy(lizard);
            Log.d("Player2", player.toString());

            EnemyCreator enemyCreator2 = new GoblinCreator();
            ImageView enemyImageView2 = new ImageView(this);
            enemyImageView2.setElevation(5);
            Enemy goblin = enemyCreator2.createEnemy(enemyImageView2, 475, 600, 5, 6);
            enemies[1] = goblin;
            player.registerEnemy(goblin);
            Log.d("Player3", player.toString());

        } else if (room.getRoomType() == 3) {
            PowerUpCreator powerUpCreator1 = new HealthCreator();
            ImageView healthImageView = new ImageView(this);
            PowerUpDecorator health = powerUpCreator1.createPowerUp(healthImageView, 150, 765, 2, 7, player);
            powerUpDecorators[0] = health;
            player.registerPowerUp(health);

            PowerUpCreator powerUpCreator2 = new TimeBoostCreator();
            ImageView timeBoostImageView = new ImageView(this);
            PowerUpDecorator timeBoost = powerUpCreator2.createPowerUp(timeBoostImageView, 230, 1350, 3, 14, player);
            powerUpDecorators[1] = timeBoost;
            player.registerPowerUp(timeBoost);

            PowerUpCreator powerUpCreator3 = new RoomChangeCreator();
            ImageView roomChangeImageView = new ImageView(this);
            PowerUpDecorator roomChange = powerUpCreator3.createPowerUp(roomChangeImageView, 830, 690, 6, 10, player);
            powerUpDecorators[2] = roomChange;
            player.registerPowerUp(roomChange);

            EnemyCreator enemyCreator1 = new LizardCreator();
            ImageView enemyImageView1 = new ImageView(this);
            enemyImageView1.setElevation(11);
            Enemy lizard = enemyCreator1.createEnemy(enemyImageView1, 471, 776, 8, 6);
            enemies[0] = lizard;
            player.registerEnemy(lizard);
            Log.d("Player4", player.toString());

            EnemyCreator enemyCreator3 = new DoctorCreator();
            ImageView enemyImageView3 = new ImageView(this);
            enemyImageView3.setElevation(5);
            Enemy doctor = enemyCreator3.createEnemy(enemyImageView3, 475, 177, 1, 6);
            enemies[1] = doctor;
            player.registerEnemy(doctor);
            Log.d("Player5", player.toString());
        }
        createPowerUps();
        createEnemies();

        // Start or stop the enemy movement based on the room type
        if (room.getRoomType() == 1 || room.getRoomType() == 2 || room.getRoomType() == 3) {
            // Start moving the enemies
            handler.post(moveEnemiesRunnable);
        } else {
            // Stop moving the enemies if the player is not in the specific room
            handler.removeCallbacks(moveEnemiesRunnable);
        }
    }
    public void createEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy != null) {
                constraintLayout.addView(enemy.getEnemyImageView());
            }
        }
    }


    public void clearEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy != null) {
                constraintLayout.removeView(enemy.getEnemyImageView());
            }
        }
    }

    public RoomViewModel getRoomViewModel() {
        return roomViewModel;
    }

    public void setRoomViewModel(RoomViewModel roomViewModel) {
        this.roomViewModel = roomViewModel;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public PowerUpDecorator[] getPowerUpDecorators() {
        return powerUpDecorators;
    }


    public void createPowerUps() {
        for (PowerUpDecorator powerUpDecorator : powerUpDecorators) {
            if (powerUpDecorator != null) {
                constraintLayout.addView(powerUpDecorator.getPowerUpImageView());
            }
        }
    }

    public void createPowerUp(PowerUpDecorator powerUpDecorator) {
        if (powerUpDecorator != null) {
            constraintLayout.addView(powerUpDecorator.getPowerUpImageView());
        }
    }


    public void removePowerUps() {
        for (PowerUpDecorator powerUpDecorator : powerUpDecorators) {
            if (powerUpDecorator != null) {
                constraintLayout.removeView(powerUpDecorator.getPowerUpImageView());
            }
        }
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    public PlayerScore getPlayerScore() {
        return playerScore;
    }

    public void increaseTimerByMinute() {
        remainingTime += 60000; // 60000 milliseconds is one minute
        timer.cancel(); // Cancel the existing timer
        startTimer(); // Start a new timer with the updated time
        setRemainingTime(remainingTime);
        updateTimerDisplay();
    }

    private void startTimer() {
        timer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateTimerDisplay();
            }

            @Override
            public void onFinish() {
                // Handle timer finish logic here
            }
        }.start();
    }

    private void updateTimerDisplay() {
        long seconds = remainingTime / 1000;
        timerTextView.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }


}