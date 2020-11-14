package com.example.msogcardwar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Stack;
import java.util.Random;

public class Game_Activity extends AppCompatActivity {
    private TextView game_lbl_scorePlayer1;
    private ImageView game_img_card_player1;
    private TextView game_lbl_scorePlayer2;
    private ImageView game_img_card_player2;
    private Button game_btn_play;
    private Stack<CardEntry<String, Integer>> all_card_stack = new Stack<CardEntry<String, Integer>>();
    private Stack<CardEntry<String, Integer>> player1_stack = new Stack<CardEntry<String, Integer>>();
    private Stack<CardEntry<String, Integer>> player2_stack = new Stack<CardEntry<String, Integer>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        findViews();
        initViews();
    }

    private void findViews() {
        game_lbl_scorePlayer1 = findViewById(R.id.game_LBL_scorePlayer1);
        game_img_card_player1 = findViewById(R.id.game_IMG_card_player1);
        game_lbl_scorePlayer2 = findViewById(R.id.game_LBL_scorePlayer2);
        game_img_card_player2 = findViewById(R.id.game_IMG_card_player2);
        game_btn_play = findViewById(R.id.game_BTN_play);
    }

    private void initViews() {
        int START_SCORE = 0;
        game_lbl_scorePlayer1.setText(String.valueOf(START_SCORE));
        game_img_card_player1.setImageResource(R.drawable.card_back);

        game_lbl_scorePlayer2.setText(String.valueOf(START_SCORE));
        game_img_card_player2.setImageResource(R.drawable.card_back);

        createGameStacks();

        game_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextRound();
            }
        });
    }

    private void nextRound() {
        
    }

    private void createGameStacks() {
        int cardSize = 52;
        int card_value;
        int card_shape;
        boolean player_card_flag = true;
        Random random = new Random();

        /*
        Fill overall stack with all the possible cards.
         */
        while(cardSize != 0){
            card_shape = random.nextInt(4 - 1) + 1;
            card_value = random.nextInt(14 - 2) + 2;

            String card_key = "poker_card_" + CardShapesEnum.valueOf(card_shape).toString().toLowerCase() + "_" + card_value;
            CardEntry<String, Integer> card_entry = new CardEntry<String, Integer>(card_key, card_value);
            if (all_card_stack.search(card_entry) == -1){
                all_card_stack.push(card_entry);
                cardSize--;
            }
        }

        // Divide the cards between the players.
        while(!all_card_stack.empty()){
            if(player_card_flag){
                insertCardToPlayerOneStack(all_card_stack.pop());
                player_card_flag = false;
            }else{
                insertCardToPlayerTwoStack(all_card_stack.pop());
                player_card_flag = true;
            }
        }
    }

    private void insertCardToPlayerOneStack(CardEntry<String, Integer> card_entry) {
        Log.println(Log.DEBUG, "player_1_card_info", "" + card_entry.getKey());
        player1_stack.push(card_entry);
    }

    private void insertCardToPlayerTwoStack(CardEntry<String, Integer> card_entry) {
        Log.println(Log.DEBUG, "player_2_card_info", "" + card_entry.getKey());
        player2_stack.push(card_entry);
    }
}