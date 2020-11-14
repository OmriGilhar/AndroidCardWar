package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button start_game_btn;
    private Button about_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        find_views();
        init_views();
    }

    private void find_views() {
        start_game_btn = findViewById(R.id.startGame_BTN_menu);
        about_btn = findViewById(R.id.about_BTN_menu);
    }

    private void init_views() {
        start_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameView();
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutView();
            }
        });
    }

    private void openAboutView() {
        Log.println(Log.DEBUG, "menu", "Open About");
    }

    private void openGameView() {
        Log.println(Log.DEBUG, "menu", "Open game");
        Intent gameIntent= new Intent(MenuActivity.this, Game_Activity.class);
        MenuActivity.this.startActivity(gameIntent);
    }
}