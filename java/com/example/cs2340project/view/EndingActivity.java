package com.example.cs2340project.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cs2340project.model.Leaderboard;
import com.example.cs2340project.viewmodel.LeaderboardViewModel;
import com.example.cs2340project.model.PlayerScore;
import com.example.cs2340project.R;

import java.util.ArrayList;

public class EndingActivity extends AppCompatActivity {
    private LeaderboardViewModel leaderViewModel;
    private Button restartButton;
    private TextView mostRecentScoreTextView;
    private TextView mostRecentScoreTitleTextView;
    private LinearLayout leaderboardLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        leaderboardLinearLayout = findViewById(R.id.leaderboardLinearLayout);
        mostRecentScoreTextView = findViewById(R.id.mostRecentScoreTextView);
        mostRecentScoreTitleTextView = findViewById(R.id.mostRecentScoreTitleTextView);

        boolean playerWon = getIntent().getBooleanExtra("playerWon", true); // Default to true

        if (!playerWon) {
            mostRecentScoreTitleTextView.setText("You Win! Recent Score:");
        } else {
            mostRecentScoreTitleTextView.setText("Game Over! Recent Score:");
        }

        ArrayList<PlayerScore> updatedList =
                getIntent().getParcelableArrayListExtra("playerScoresList");
        leaderViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        leaderViewModel.updateLeaderboard(updatedList);

        leaderViewModel.getLeaderboardData().observe(this, leaderboard -> {
            populateLeaderboardView(leaderboard);
            displayMostRecentScore(leaderboard);
        });

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> {
            Intent restartIntent  = new Intent(EndingActivity.this, MainActivity.class);
            restartIntent.putParcelableArrayListExtra("playerScoresList",
                    leaderViewModel.getCurrBoard().getScores());
            startActivity(restartIntent);
            finish();
        });
    }

    private void populateLeaderboardView(Leaderboard leaderboard) {
        ArrayList<PlayerScore> entries = leaderboard.getScores();
        int count = Math.min(entries.size(), 15);

        for (int i = 0; i < count; i++) {
            PlayerScore entry = entries.get(i);
            TextView textView = new TextView(this);
            textView.setText(String.format("%s - %d - %s", entry.getPlayerName(),
                    entry.getScore(), entry.getTimeStampString()));
            textView.setTextColor(Color.LTGRAY);
            textView.setGravity(Gravity.CENTER);

            leaderboardLinearLayout.addView(textView);
        }

        if (entries.size() == 0) {
            TextView textView = new TextView(this);
            textView.setText("rip");
            leaderboardLinearLayout.addView(textView);
        }
    }

    private void displayMostRecentScore(Leaderboard leaderboard) {
        PlayerScore mostRecentScore = leaderboard.getMostRecentScore();
        if (mostRecentScore != null) {
            ArrayList<PlayerScore> entries = leaderboard.getScores();
            PlayerScore entry = entries.get(0);
            mostRecentScoreTextView.setText(String.format("%s - %d - %s",
                    entry.getPlayerName(), entry.getScore(), entry.getTimeStampString()));
            mostRecentScoreTextView.setTextColor(Color.LTGRAY);
            mostRecentScoreTextView.setGravity(Gravity.CENTER);
        } else {
            mostRecentScoreTextView.setText("No recent scores available");
        }
    }
}
