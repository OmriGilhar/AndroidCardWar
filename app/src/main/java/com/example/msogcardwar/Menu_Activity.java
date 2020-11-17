package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Menu_Activity extends AppCompatActivity {

    private Button start_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        find_views();
        init_views();
    }

    private void find_views() {
        start_game_btn = findViewById(R.id.startGame_BTN_menu);
    }

    private void init_views() {
        start_game_btn.setOnClickListener(v -> openGameView());
    }

    private void openGameView() {
        Intent gameIntent= new Intent(Menu_Activity.this, Game_Activity.class);
        Menu_Activity.this.startActivity(gameIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}