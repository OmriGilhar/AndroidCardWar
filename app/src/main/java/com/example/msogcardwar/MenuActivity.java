package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }

    private void openGameView() {
    }
}