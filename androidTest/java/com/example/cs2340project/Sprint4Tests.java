package com.example.cs2340project;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.example.cs2340project.model.Doctor;
import com.example.cs2340project.model.Enemy;
import com.example.cs2340project.model.EnemyCreator;
import com.example.cs2340project.model.GoblinCreator;
import com.example.cs2340project.model.Leaderboard;
import com.example.cs2340project.model.MovementStrategy;
import com.example.cs2340project.model.Player;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.model.Room;
import com.example.cs2340project.model.SkeletonCreator;
import com.example.cs2340project.model.Tile;
import com.example.cs2340project.model.Walk;
import com.example.cs2340project.view.GamesActivity;
import com.example.cs2340project.view.InitialConfigActivity;
import com.example.cs2340project.viewmodel.RoomViewModel;

import static org.junit.Assert.*;
import android.view.KeyEvent;
import android.content.Context;
import android.widget.ImageView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import android.widget.ImageView;
import static org.junit.Assert.assertNotNull;

public class Sprint4Tests {
    private Room room1;
    private Enemy[] enemies;

    private Player player;
    private RoomViewModel roomViewModel;
    private Context context; // Mock or actual context as required
    private String initialHealth;


    @Test
    public void testHealthReductionOnCollision() {
        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        initialHealth = player.getHealth();

        player.moveUp();
        player.moveUp();
        player.moveUp();

        assertTrue("Health should decrease after collision", player.getHealth().compareTo(initialHealth)<1);
    }

    @Test
    public void testEnemyInitialization() {
        // Arrange
        int initialX = 150;
        int initialY = 250;
        int initialRow = 3;
        int initialCol = 4;

        // Act
        Doctor enemy = new Doctor(initialX, initialY, initialRow, initialCol);

        // Assert
        assertEquals(initialX, enemy.getX());
        assertEquals(initialY, enemy.getY());
        assertEquals(initialRow, enemy.getRow());
        assertEquals(initialCol, enemy.getCol());
    }

    @Test
    public void reduceHPBasedOnDifficulty () {

        // EASY
        int initialX = 475;
        int initialY = 1040;
        int initialRow = 10;
        int initialCol = 6;
        Doctor enemy = new Doctor(initialX, initialY, initialRow, initialCol);

        MovementStrategy walk = new Walk();
        Room room = new Room(1);
        RoomViewModel roomViewModel = new RoomViewModel(room);
        Player player = new Player(471, 1219, 13, 6, 100, 1, roomViewModel, null);
        player.setMovementStrategy(walk);
        initialHealth = player.getHealth();

        player.moveUp();
        player.moveUp();
        player.moveUp();

        if (enemy.checkCollision()) {
            player.reduceHP();
        }

        assertEquals(98, player.getHealth());

        // MEDIUM
        int initialX2 = 475;
        int initialY2 = 1040;
        int initialRow2 = 10;
        int initialCol2 = 6;
        Doctor enemy2 = new Doctor(initialX2, initialY2, initialRow2, initialCol2);

        MovementStrategy walk2 = new Walk();
        Room room2 = new Room(1);
        RoomViewModel roomViewModel2 = new RoomViewModel(room2);
        Player player2 = new Player(471, 1219, 13, 6, 100, 2, roomViewModel2, null);
        player2.setMovementStrategy(walk2);
        initialHealth = player2.getHealth();

        player2.moveUp();
        player2.moveUp();
        player2.moveUp();

        if (enemy2.checkCollision()) {
            player2.reduceHP();
        }

        assertEquals(95, player2.getHealth());

        // HARD
        int initialX3 = 475;
        int initialY3 = 1040;
        int initialRow3 = 10;
        int initialCol3 = 6;
        Doctor enemy3 = new Doctor(initialX3, initialY3, initialRow3, initialCol3);

        MovementStrategy walk3 = new Walk();
        Room room3 = new Room(1);
        RoomViewModel roomViewModel3 = new RoomViewModel(room3);
        Player player3 = new Player(471, 1219, 13, 6, 100, 3, roomViewModel3, null);
        player3.setMovementStrategy(walk3);
        initialHealth = player3.getHealth();

        player3.moveUp();
        player3.moveUp();
        player3.moveUp();

        if (enemy3.checkCollision()) {
            player3.reduceHP();
        }

        assertEquals(90, player3.getHealth());

    }

}
