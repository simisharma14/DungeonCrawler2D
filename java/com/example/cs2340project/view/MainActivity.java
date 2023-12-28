package com.example.cs2340project.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.cs2340project.viewmodel.LeaderboardViewModel;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private LeaderboardViewModel leaderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("SCORES", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("scoreEntries");
        editor.apply();
        ArrayList<PlayerScore> updatedList;

        if (getIntent().hasExtra("playerScoresList")) {

            updatedList = getIntent().getParcelableArrayListExtra("playerScoresList");
        } else {

            updatedList = new ArrayList<>();
        }
        leaderViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        leaderViewModel.updateLeaderboard(updatedList);


        Button btn = findViewById(R.id.button);
        btn.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, InitialConfigActivity.class);
            intent.putParcelableArrayListExtra("playerScoresList",
                    leaderViewModel.getCurrBoard().getScores());
            startActivity(intent);


        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener((view) -> {
            finish();
            System.exit(0);
        });
    }
}
