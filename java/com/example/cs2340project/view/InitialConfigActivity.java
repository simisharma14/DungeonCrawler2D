package com.example.cs2340project.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cs2340project.viewmodel.LeaderboardViewModel;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.R;

import java.util.ArrayList;


public class InitialConfigActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private RadioGroup difficultyRadioGroup;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private RadioButton sprite1;
    private RadioButton sprite2;
    private RadioButton sprite3;
    private RadioGroup spriteRadioGroup;
    private Button startGameButton;
    private int selectedSprite;
    private LeaderboardViewModel leaderViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_config);
        playerNameEditText = findViewById(R.id.playerNameEditText);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        easy = findViewById(R.id.radioEasy);
        medium = findViewById(R.id.radioMedium);
        hard = findViewById(R.id.radioHard);
        sprite1 = findViewById(R.id.radioSprite1);
        sprite2 = findViewById(R.id.radioSprite2);
        sprite3 = findViewById(R.id.radioSprite3);
        spriteRadioGroup = findViewById(R.id.spriteRadioGroup);
        ArrayList<PlayerScore> updatedList =
                getIntent().getParcelableArrayListExtra("playerScoresList");
        leaderViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        leaderViewModel.updateLeaderboard(updatedList);

        startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = playerNameEditText.getText().toString().trim();
                int selectedDifficulty = difficultyRadioGroup.getCheckedRadioButtonId();
                int startingHP = determineStartingHP();

                if (validateInput(playerName, selectedDifficulty, selectedSprite)) {
                    Intent gameIntent = new Intent(InitialConfigActivity.this, GamesActivity.class);
                    gameIntent.putExtra("playerName", playerName);
                    gameIntent.putExtra("selectedDifficulty", selectedDifficulty);
                    gameIntent.putExtra("selectedSprite", selectedSprite);
                    gameIntent.putExtra("startingHP", startingHP);
                    gameIntent.putParcelableArrayListExtra("playerScoresList",
                            leaderViewModel.getCurrBoard().getScores());
                    startActivity(gameIntent);
                } else {
                    Toast.makeText(InitialConfigActivity.this, "Please enter valid input",
                            Toast.LENGTH_SHORT).show();
                }




            }
        });
        CompoundButton.OnCheckedChangeListener spriteCheckedChangeListener =
                new CompoundButton.OnCheckedChangeListener() {
            @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (buttonView.getId() == R.id.radioSprite1) {
                            sprite1.setChecked(true);
                            sprite2.setChecked(false);
                            sprite3.setChecked(false);
                            selectedSprite = R.id.radioSprite1;

                        }
                        if (buttonView.getId() == R.id.radioSprite2) {
                            sprite1.setChecked(false);
                            sprite2.setChecked(true);
                            sprite3.setChecked(false);
                            selectedSprite = R.id.radioSprite2;
                        }
                        if (buttonView.getId() == R.id.radioSprite3) {
                            sprite1.setChecked(false);
                            sprite2.setChecked(false);
                            sprite3.setChecked(true);
                            selectedSprite = R.id.radioSprite3;
                        }
                    }
                }
            };

        sprite1.setOnCheckedChangeListener(spriteCheckedChangeListener);
        sprite2.setOnCheckedChangeListener(spriteCheckedChangeListener);
        sprite3.setOnCheckedChangeListener(spriteCheckedChangeListener);

    }

    public static boolean validateInput(String playerName, int selectedDifficulty,
                                        int selectedSprite) {
        return !playerName.trim().isEmpty();
    }

    private int determineStartingHP() {
        int checkedId = difficultyRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.radioEasy) {
            return 150;
        } else if (checkedId == R.id.radioMedium) {
            return 100;
        } else if (checkedId == R.id.radioHard) {
            return 50;
        } else {
            return 100;
        }
    }
}