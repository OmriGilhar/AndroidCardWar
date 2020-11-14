package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity {

    private TextView winner_title;
    private int winner = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);
        this.winner = getIntent().getIntExtra("winner", -1);

        findViews();
        initViews();

    }

    private void findViews() {
        winner_title = findViewById(R.id.winner_LBL_winner_title);
    }

    private void initViews() {
        winner_title.setText("The Winner is Player Number " + this.winner);
    }
}