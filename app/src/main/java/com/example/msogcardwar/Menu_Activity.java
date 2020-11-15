package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class Menu_Activity extends AppCompatActivity {

    private Button start_game_btn;
    private MediaPlayer backgroundSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        backgroundSong = MediaPlayer.create(Menu_Activity.this, R.raw.loyalty_freak_music04hello_regan);
        backgroundSong.start();

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
        backgroundSong.release();
        Log.println(Log.DEBUG, "menu", "Open game");
        Intent gameIntent= new Intent(Menu_Activity.this, Game_Activity.class);
        Menu_Activity.this.startActivity(gameIntent);
    }
}