package com.example.msogcardwar.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.msogcardwar.R;
import android.os.Bundle;
import android.widget.FrameLayout;

public class Scoreboard_Activity extends AppCompatActivity {

    FrameLayout sb_LAY_sb_list;
    FrameLayout sb_LAY_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorebaord_activity);

        find_views();

    }

    private void find_views() {
        sb_LAY_sb_list = findViewById(R.id.sb_LAY_sb_list);
        sb_LAY_location = findViewById(R.id.sb_LAY_location);
    }
}