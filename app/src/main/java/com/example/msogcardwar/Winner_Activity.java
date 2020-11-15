package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity {

    private TextView winner_lbl_winner_name;
    private ImageView winner_img_winner_logo;
    private TextView winner_lbl_winner_score;
    private int winner_score;
    private int winner_img_id;
    private Button start_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);
        this.winner_score = getIntent().getIntExtra("winner_score", -1);
        this.winner_img_id = getIntent().getIntExtra("winner_image_id", -1);


        findViews();
        initViews();

    }

    private void findViews() {
        winner_lbl_winner_name = findViewById(R.id.winner_LBL_winner_title);
        winner_img_winner_logo = findViewById(R.id.winner_IMG_winner);
        winner_lbl_winner_score = findViewById(R.id.winner_LBL_description);
        start_game_btn = findViewById(R.id.startNew_Game_BTN);
    }

    private void initViews() {
        String winner_score_title = "Your Score is :" +this.winner_score;
        Log.println(Log.DEBUG, "winner", winner_score_title);
        winner_lbl_winner_name.setText(R.string.winner_title);
        winner_lbl_winner_score.setText(winner_score_title);
        winner_img_winner_logo.setImageResource(winner_img_id);
        start_game_btn.setOnClickListener(v -> openGameView());


    }
    
    private void openGameView() {
        Log.println(Log.DEBUG, "menu", "Open game");
        Intent gameIntent= new Intent(Winner_Activity.this, Game_Activity.class);
        Winner_Activity.this.startActivity(gameIntent);
    }
}