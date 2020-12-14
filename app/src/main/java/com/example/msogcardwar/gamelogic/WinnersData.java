package com.example.msogcardwar.gamelogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.example.msogcardwar.R;
import com.google.gson.Gson;

public class WinnersData {

    String[] scoreArr = new String[10];
    int[] iconsArr = new int[10];
    CustomAdapter customAdapter;
    SharedPreferences mPrefs;
    String json;
    Gson gson;
    Winner[] winners;

    //TODO:switch location to map location
    public void saveWinner(int winner_score, int winner_img_id, int location)  {
        Winner myWinner = new Winner(winner_score, winner_img_id, 22);

        Gson gson = new Gson();
        //TODO: read json file and make it gson. gitString does not work in other classes
//        String json = mPrefs.getString("Winners", "");
//        Winner[] winArray = new Gson().fromJson(json, Winner[].class);
//
//        if(winArray==null){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = gson.toJson(myWinner);
        prefsEditor.putString("Winners", json);
        prefsEditor.commit();
//        }
//        else if (winArray.length<10){
//      //TODO: add winner to the list
//
//        }
//        else if (winArray.length == 10){
//      //TODO: Search if there's a winner with a smaller score in the list. if so, replace.
//
//        }
//
    }


    public CustomAdapter generateWinners(Context applicationContext){
       // mPrefs = getPreferences(MODE_PRIVATE);
       // gson = new Gson();
        // json = mPrefs.getString("Winners", "");
        //winners = gson.fromJson(json, Winner[].class);
        //int temp;


        for (int i = 0; i < 10; i++){
            scoreArr[i] = "score: " + i + "";
            iconsArr[i] = R.drawable.black_cat;
        }
//        if (winners != null) {
//            for (int i = 0; i < winners.length; i++) {
//                scoreArr[i] = "score: " + winners[i].getScore() + "";
//                iconsArr[i] = winners[i].getIcon();
//            }
//        }
        customAdapter = new CustomAdapter(applicationContext, scoreArr, iconsArr);
        return customAdapter;
    }
}
