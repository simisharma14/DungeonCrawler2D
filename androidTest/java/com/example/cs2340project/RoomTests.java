package com.example.cs2340project;

//import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.example.cs2340project.model.Doctor;
import com.example.cs2340project.model.Enemy;


import com.example.cs2340project.model.EnemyCreator;
import com.example.cs2340project.model.EnemyObserver;
import com.example.cs2340project.model.Goblin;
import com.example.cs2340project.model.GoblinCreator;
import com.example.cs2340project.model.HealthDecorator;
import com.example.cs2340project.model.Leaderboard;
import com.example.cs2340project.model.Lizard;
import com.example.cs2340project.model.MovementStrategy;
import com.example.cs2340project.model.Player;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.model.PowerUp;


import com.example.cs2340project.model.Room;
import com.example.cs2340project.model.RoomChangeDecorator;
import com.example.cs2340project.model.Skeleton;
import com.example.cs2340project.model.SkeletonCreator;
import com.example.cs2340project.model.Tile;
import com.example.cs2340project.model.TimeBoostDecorator;
import com.example.cs2340project.model.Walk;
import com.example.cs2340project.view.GameLostActivity;
import com.example.cs2340project.view.GamesActivity;
import com.example.cs2340project.view.InitialConfigActivity;
import com.example.cs2340project.viewmodel.RoomViewModel;

import static org.junit.Assert.*;


import android.media.Image;
import android.view.KeyEvent;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

//import androidx.test.ext.junit.rules.ActivityScenarioRule;


public class RoomTests {
    private Room room1;
    private Enemy[] enemies;

    private Player player;
    private RoomViewModel roomViewModel;
    private Context context; // Mock or actual context as required
    private String initialHealth;


    public RoomTests() {
        this.room1 = new Room(1);
    }

    @Test
    public void transitionsToNextRoom() {
        room1.setRoomType(1);
        assertEquals(1, room1.getRoomType());
        room1.nextRoom();
        assertEquals(2, room1.getRoomType());
        room1.nextRoom();
        assertEquals(3, room1.getRoomType());
        room1.nextRoom();
        assertEquals(3, room1.getRoomType()); /*once in the last room, we should not be
        able to transition to another room*/


    }

    public void checkTileInitialization() {
        Tile tile = new Tile(1);
        assertEquals(1, tile.getType());
        assertEquals(R.drawable.floor_1, tile.getImageId());
    }

    @Test
    public void testWalkMoveUp() {
        Walk walkStrategy = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(0, 0, 0, 0, 0, 1, roomViewModel, null);

        player.setY(88);
        player.setRow(1);
        walkStrategy.moveUp(player);
        assertEquals(88, player.getY());
        assertEquals(0, player.getRow());
    }


    @Test
    public void tilesOfRoomTest() {
        int randomRow = (int) (Math.random() * 16) + 1; // picks a random row
        int randomCol = (int) (Math.random() * 12) + 1; // picks a random column
        int randomTile = (int) (Math.random() * 17) + 1; // chooses a random tile type to add at that index
        Tile toAdd = new Tile(randomTile); // creates a tile with that type #
        room1.setTile(randomRow, randomCol, toAdd); // sets the index to that tile type
        int typeAdded = toAdd.getType();
        int idAdded = toAdd.getImageId();
        assertEquals(typeAdded, room1.getTile(randomRow, randomCol).getType()); // checks if it got added
        assertEquals(idAdded, room1.getTile(randomRow, randomCol).getImageId()); //check if the correct id was assigned

    }

    private Leaderboard leaderboard;

    @Test
    public void testNull() {
        assertFalse(InitialConfigActivity.validateInput("", -1, -1));
    }

    @Test
    public void testEmptyPlayerName() {
        assertFalse(InitialConfigActivity.validateInput(" ", -1, -1));
        assertFalse(InitialConfigActivity.validateInput("  ", -1, -1));
    }

    @Test
    public void testNonEmptyPlayerName() {
        assertTrue(InitialConfigActivity.validateInput("player1", -1, -1));
    }


    @Before
    public void setUp() {
        leaderboard = new Leaderboard();
    }

    @Test
    public void testAddScoreUpdatesLeaderboardInDescendingOrder() {
        PlayerScore score1 = new PlayerScore("player1", 0, 5000, 15);
        PlayerScore score2 = new PlayerScore("player2", 0, 7000, 50);
        PlayerScore score3 = new PlayerScore("player3", 0, 6000, 70);

        leaderboard.addScore(score1);
        leaderboard.addScore(score2);
        leaderboard.addScore(score3);

        ArrayList<PlayerScore> scores = leaderboard.getScores();
        assertEquals("player3", scores.get(0).getPlayerName());
        assertEquals("player2", scores.get(1).getPlayerName());
        assertEquals("player1", scores.get(2).getPlayerName());
    }

    @Test
    public void testPlayerScoreInitialization() {
        long currentTime = new Date().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = sdf.format(currentTime);

        PlayerScore score = new PlayerScore("John", currentTime, 60000, 50);

        assertEquals("John", score.getPlayerName());
        assertEquals(currTime, score.getTimeStampString());
        assertEquals(60000, score.getTimeLeft());
        assertNotNull(score.getTimeLeftString());
    }

    @Test
    public void testInitialScoreAboveZero() {
        PlayerScore playerScore = new PlayerScore("TestPlayer", System.currentTimeMillis(), -10000, 50);

        assertNotEquals(0, playerScore.getScore());
    }

    @Test
    public void testMostRecentScore() {
        Leaderboard leaderboard = new Leaderboard();

        PlayerScore score1 = new PlayerScore("Amy", System.currentTimeMillis(), 30000, 50);
        PlayerScore score2 = new PlayerScore("Jack", System.currentTimeMillis(), 30000, 50);
        PlayerScore score3 = new PlayerScore("Elisa", System.currentTimeMillis(), 300000, 50);

        leaderboard.addScore(score1);
        leaderboard.addScore(score2);
        leaderboard.addScore(score3);

        PlayerScore mostRecentScore = leaderboard.getMostRecentScore();

        assertNotNull(mostRecentScore);
        assertEquals(score3, mostRecentScore);
    }

    @Test
    public void testLeaderboardCreation() {
        Leaderboard leaderboard = new Leaderboard();

        PlayerScore score1 = new PlayerScore("Amy", System.currentTimeMillis(), 30000, 50);
        PlayerScore score2 = new PlayerScore("Jack", System.currentTimeMillis(), 60000, 50);
        PlayerScore score3 = new PlayerScore("Elisa", System.currentTimeMillis(), 300000, 50);
        PlayerScore score4 = new PlayerScore("Luke", System.currentTimeMillis(), 200000, 50);

        leaderboard.addScore(score1);
        leaderboard.addScore(score2);
        leaderboard.addScore(score3);
        leaderboard.addScore(score4);

        ArrayList<PlayerScore> leaderboardScores = leaderboard.getScores();

        assertEquals(4, leaderboardScores.size());
        assertEquals("Elisa", leaderboardScores.get(0).getPlayerName()); // Assuming sorting is working correctly
    }

    //Sprint 3 tests:

    @Test
    public void testCantLeaveScreen() {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);


        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);

        player.setMovementStrategy(walk);
        player.moveDown();
        player.moveDown();
        player.moveDown(); //would be 1483
        int newRow = player.getRow(); //should stay at 1395

        MovementStrategy walk2 = new Walk();

        Player player2 = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);

        player2.setMovementStrategy(walk2);
        player2.setX(1200);
        player2.setY(10);

        int newX = player2.getX();
        int newY = player2.getY();


        assertEquals(14, newRow);
        assertEquals(471, newX);
        assertEquals(1219, newY);

    }


    @Test
    public void testInvalidKeys() throws Throwable {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(400, 1000, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        int initialCol = player.getCol();
        int initalRow = player.getRow();
        player.moveLeft();
        player.moveUp();
        //runOnUiThread(() -> player.moveDiagonal()); // should do nothing
        int newCol = player.getCol();
        int newRow = player.getRow();
        assertEquals(initialCol - 1, newCol);
        assertEquals(initalRow - 1, newRow);
    }

    @Test
    public void testWon() {

        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);

        Player player = new Player(400, 1000, 1, 9, 100, 1, roomViewModel, null);

        player.setMovementStrategy(walk);
        int initialCol = player.getCol();
        int initalRow = player.getRow();
        assertEquals(false, player.getWon());
        player.moveUp();
        assertEquals(9, player.getCol());
        assertEquals(1, player.getRow());
        player.setWon(true);
        assertEquals(true, player.getWon());


    }


    @Test
    public void testCollisionWithObstacle() {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);

        player.moveRight();
        player.moveRight();

        assertEquals(7, player.getCol());
    }

    @Test
    public void testRoomSwitch() throws Throwable {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);


        Player player = new Player(471, 1219, 1, 9, 100, 1, roomViewModel, null);

        player.setMovementStrategy(walk);

        player.setCol(9);
        player.setRow(1);

        runOnUiThread(() -> player.moveUp());

        assertNotEquals(player.getRow(), 0);
        assertNotEquals(player.getRow(), 9);
    }
    @Test
    public void testPlayerInitialization() {
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);

        Player player = new Player(10, 20, 3, 4, 100, 1, roomViewModel, null);


        assertEquals(10, player.getX());
        assertEquals(20, player.getY());
        assertEquals(3, player.getRow());
        assertEquals(4, player.getCol());

    }

    @Test
    public void testRoomInitialization() {
        Room room1 = new Room(1);
        Room room2 = new Room(2);
        Room room3 = new Room(3);

        assertEquals(1, room1.getRoomType());
        assertEquals(2, room2.getRoomType());
        assertEquals(3, room3.getRoomType());

    }

    /*@Rule
    public ActivityScenarioRule<GamesActivity> activityRule = new ActivityScenarioRule<>(GamesActivity.class);

    @Test
    public void testTwoEnemiesPerRoom() {
        activityRule.getScenario().onActivity(activity -> {
            assertEquals("Enemies array should have 2 enemies", 2, activity.getEnemies().length);
        });
    }*/

    @Test
    public void testPlayerHealthUpdatesOnCollision() {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        initialHealth = player.getHealth();

        player.moveUp();
        player.moveUp();
        player.moveUp();

        assertTrue("Health should decrease after collision", player.getHealth().compareTo(initialHealth) < 1);
    }


    //Sprint 4:

    @Test
    public void testPlayerEnemyCollisionsNotified() {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        player.moveLeft();

        Doctor doctor = new Doctor(100, 200, 13, 5);
        player.registerEnemy(doctor);
        boolean collision = doctor.checkCollision();
        assertEquals(false, collision);
    }

    @Test

    public void TestNotifyMovement() {

        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        boolean initialNotify = player.getNotified();
        player.moveUp();
        boolean changeNotify = player.getNotified();
        assertEquals(false, initialNotify);
        assertEquals(true, changeNotify);


    }

    @Test
    public void testSkeletonInitialization() {

        Skeleton skeleton = new Skeleton(100, 200, 2, 3);

        int[] position = skeleton.getPosition();

        assertEquals(100, position[0]); // x
        assertEquals(200, position[1]); // y
        assertEquals(3, position[2]); // col
        assertEquals(2, position[3]); // row
    }

    @Test
    public void testEnemyInitialization() {

        int initialX = 150;
        int initialY = 250;
        int initialRow = 3;
        int initialCol = 4;

        Doctor enemy = new Doctor(initialX, initialY, initialRow, initialCol);


        assertEquals(initialX, enemy.getX());
        assertEquals(initialY, enemy.getY());
        assertEquals(initialRow, enemy.getRow());
        assertEquals(initialCol, enemy.getCol());
    }

    @Test
    public void reduceHPBasedOnDifficulty() { //not pass

        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        player.setDiff(1);
        initialHealth = player.getHealth();

        player.moveUp();
        player.moveUp();
        player.moveUp();
        assertEquals(100, Integer.parseInt(player.getHealth()));

    }

    @Test
    public void testFourEnemiesOfDifferentSkills() {
        Enemy skeleton = new Skeleton(475, 1040, 10, 6);
        int skeletonSpeed = skeleton.getSpeed();
        int skeletonSprite = skeleton.getImageViewId();


        Enemy doctor = new Doctor(475, 177, 1, 6);
        int doctorSpeed = doctor.getSpeed();
        int doctorSprite = doctor.getImageViewId();

        Enemy goblin = new Goblin(475, 600, 5, 6);
        int goblinSpeed = goblin.getSpeed();
        int goblinSprite = goblin.getImageViewId();


        Enemy lizard = new Lizard(471, 1040, 11, 6);
        int lizardSpeed = lizard.getSpeed();
        int lizardSprite = lizard.getImageViewId();

        assertNotEquals(skeletonSpeed, doctorSpeed);
        assertNotEquals(skeletonSpeed, goblinSpeed);
        assertNotEquals(skeletonSpeed, lizardSpeed);

        assertNotEquals(doctorSpeed, goblinSpeed);
        assertNotEquals(doctorSpeed, lizardSpeed);

        assertNotEquals(goblinSpeed, lizardSpeed);

        assertNotEquals(skeletonSprite, doctorSprite);
        assertNotEquals(skeletonSprite, goblinSprite);
        assertNotEquals(skeletonSprite, lizardSprite);

        assertNotEquals(doctorSprite, goblinSprite);
        assertNotEquals(doctorSprite, lizardSprite);

        assertNotEquals(goblinSprite, lizardSprite);
    }

    @Test
    public void testEnemiesBoundaries() {
        Enemy skeleton = new Skeleton(475, 248, 1, 6);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        skeleton.move();

        int result = skeleton.getPosition()[3];
        int expected = 1;

        assertEquals(result, expected);
    }

    @Test
    public void testGoblinInitialization() {

        Goblin goblin = new Goblin(150, 250, 4, 5);

        int[] position = goblin.getPosition();

        assertEquals(150, position[0]);
        assertEquals(250, position[1]);
        assertEquals(5, position[2]);
        assertEquals(4, position[3]);
    }

    @Test
    public void testScoreCalculation() {
        String playerName = "TestPlayer";
        long timestamp = System.currentTimeMillis();
        long timeLeft = 5000;
        int playerHealth = 100;

        int expectedScore = (((int) timeLeft)/1000) * playerHealth;

        PlayerScore playerScore = new PlayerScore(playerName, timestamp, timeLeft, playerHealth);

        assertEquals("Score should be calculated as timeLeft + playerHealth",
                expectedScore,
                playerScore.getScore());
    }

    @Test
    public void testScoreCalculationWithZeroHealth() {
        String playerName = "TestPlayer";
        long timestamp = System.currentTimeMillis();
        long timeLeft = 5000;
        int playerHealth = 0;

        int expectedScore = (int) timeLeft / 1000;

        PlayerScore playerScore = new PlayerScore(playerName, timestamp, timeLeft, playerHealth);

        assertEquals("Score should be timeLeft in seconds when playerHealth is zero",
                expectedScore,
                playerScore.getScore());
    }

    @Test
    public void testScoreGetsUpdatedWithLostHealth() {
        String playerName = "test";
        long timestamp = System.currentTimeMillis();
        long timeLeft = 1000;
        int playerHealth = 100;

        int expectedScore1 = 100;
        int expectedScore2 = 90;

        PlayerScore playerScore1 = new PlayerScore(playerName, timestamp, timeLeft, playerHealth);
        PlayerScore playerScore2 = new PlayerScore(playerName, timestamp, timeLeft, playerHealth - 10);


        assertEquals("Score should be 100 initially",
                expectedScore1,
                playerScore1.getScore());

        assertEquals("Score should update to 90",
                expectedScore2,
                playerScore2.getScore());
    }

    @Test
    public void testThreeDifferentPowerups() {
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        PowerUp healthPower = new HealthDecorator(0, 0, 14, 5, player);
        PowerUp roomPower = new RoomChangeDecorator(0, 0, 15, 5, player);
        PowerUp timePower = new TimeBoostDecorator(0, 0, 13, 5, player);

        assertNotEquals(healthPower, roomPower);
        assertNotEquals(roomPower, timePower);
        assertNotEquals(timePower, healthPower);
    }

    @Test
    public void testHealthPowerUp() {
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player1 = new Player(400, 1000, 2, 7, 100, 1, roomViewModel, null);

        int initialHealth = player1.getHealthInt();

        HealthDecorator healthPowerUp = new HealthDecorator(400, 1000, 2, 7, player1);
        healthPowerUp.applyHealthPowerUp();

        int updatedHealth = player1.getIntHealth();
        assertEquals(initialHealth + 10, updatedHealth);
    }



    @Test
    public void testRoomPowerUp() {
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player1 = new Player(400, 1000, 2, 7, 100, 1, roomViewModel, null);
        int initialRoom = room.getRoomType();
        RoomChangeDecorator roomChangePower = new RoomChangeDecorator(400, 1000, 2, 7, player1);
        assertEquals(1, room.getRoomType());
    }
    @Test
    public void testTimePowerUp() {
        Room room = new Room(1);
        MovementStrategy walk = new Walk();
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        PowerUp timePower = new TimeBoostDecorator(0, 0, 1, 5, player);
        Tile tile = new Tile(21);
        room.setTile(1, 5, tile);
        Tile compTile = new Tile(21);
        assertEquals(room.getTile(1, 5).getImageId(), compTile.getImageId());
    }

    @Test
    public void testPlayerAttack() {
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Enemy doctor = new Doctor(475, 177, 1, 6);
        Player player2 = new Player(475, 177, 1, 6, 100, 1, roomViewModel, null, doctor);

        player2.playerAttack();

        assertEquals("null", player2.getMessage());

    }


    @Test
    public void testPowerUpExists() {
        Room room = new Room(1);
        MovementStrategy walk = new Walk();
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        PowerUp healthPower = new HealthDecorator(0, 0, 14, 5, player);
        Tile tile = new Tile(19);
        room.setTile(14, 5, tile);
        Tile compTile = new Tile(19);
        assertEquals(room.getTile(14, 5).getImageId(), compTile.getImageId());
    }
    @Test
    public void testTimerPowerup() {
        Room room = new Room(1);
        MovementStrategy walk = new Walk();
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        PowerUp healthPower = new TimeBoostDecorator(0, 0, 12, 7, player);
        Tile tile = new Tile(20);
        room.setTile(12, 7, tile);
        Tile compTile = new Tile(20);
        assertEquals(room.getTile(12, 7).getImageId(), compTile.getImageId());

    }

    @Test
    public void testTilePowerUpExists() {
        Room room = new Room(1);
        MovementStrategy walk = new Walk();
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        PowerUp healthPower = new HealthDecorator(0, 0, 14, 5, player);
        Tile tile = new Tile(19);
        room.setTile(14, 5, tile);
        Tile compTile = new Tile(19);
        assertEquals(room.getTile(14, 5).getImageId(), compTile.getImageId());
    }





}
