package com.example.msogcardwar.gamelogic;

import android.widget.ImageView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class Winner {
    private int score;
    private int winner_img_id;
    private int location;

    public Winner(int score, int winner_img_id, int location) {
        this.score = score;
        this.winner_img_id = winner_img_id;
        this.location = location;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIcon() {
        return winner_img_id;
    }

    public void setIcon(int icon) {
        this.winner_img_id = icon;
    }

    public int  getLocation() {
        return location;
    }

    public void setLocation(int  location) {
        this.location = location;
    }

}
