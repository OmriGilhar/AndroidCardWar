package com.example.msogcardwar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Game_Activity extends AppCompatActivity {
    private final String PLAYER_ONE_SCORE = "P1_SCORE";
    private final String PLAYER_TWO_SCORE = "P2_SCORE";
    private final String WINNER = "WINNER";
    private final String PLAYER_ONE_CURRENT_CARD_IMG = "P1_CARD_IMG";
    private final String PLAYER_ONE_CURRENT_CARD_VALUE = "P1_CARD_VALUE";
    private final String PLAYER_TWO_CURRENT_CARD_IMG = "P2_CARD_IMG";
    private final String PLAYER_TWO_CURRENT_CARD_VALUE = "P2_CARD_VALUE";
    private final String PLAYER_ONE_STACK = "P1_STACK";
    private final String PLAYER_TWO_STACK = "P2_STACK";
    private TextView game_lbl_scorePlayer1;
    private ImageView game_img_card_player1;
    private TextView game_lbl_scorePlayer2;
    private ImageView game_img_card_player2;
    private ImageButton game_btn_play;
    private final ArrayList<CardEntry<String, Integer>> all_card_stack = new ArrayList<>();
    private Stack<CardEntry<String, Integer>> player1_stack = new Stack<>();
    private Stack<CardEntry<String, Integer>> player2_stack = new Stack<>();

    private CardEntry<String, Integer> current_player_one_card;
    private CardEntry<String, Integer> current_player_two_card;

    private int player_one_score = 0;
    private int player_two_score = 0;
    private int winner = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        findViews();
        initViews();

        if(savedInstanceState != null) {
            getScoresFromInstance(savedInstanceState);
            getStacksFromInstance(savedInstanceState);
            getPlayersCardFromInstance(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLAYER_ONE_STACK, player1_stack);
        outState.putSerializable(PLAYER_TWO_STACK, player2_stack);

        // If someone spin the phone when no card as been played yet
        if(current_player_one_card != null ){
            outState.putString(PLAYER_ONE_CURRENT_CARD_IMG, current_player_one_card.getKey());
            outState.putInt(PLAYER_ONE_CURRENT_CARD_VALUE, current_player_one_card.getValue());
            outState.putString(PLAYER_TWO_CURRENT_CARD_IMG, current_player_two_card.getKey());
            outState.putInt(PLAYER_TWO_CURRENT_CARD_VALUE, current_player_two_card.getValue());
        } else {
            outState.putString(PLAYER_ONE_CURRENT_CARD_IMG, getResources().getString(R.string.path_to_default_card));
            outState.putInt(PLAYER_ONE_CURRENT_CARD_VALUE, 0);
            outState.putString(PLAYER_TWO_CURRENT_CARD_IMG, getResources().getString(R.string.path_to_default_card));
            outState.putInt(PLAYER_TWO_CURRENT_CARD_VALUE, 0);
        }




        outState.putInt(PLAYER_ONE_SCORE, player_one_score);
        outState.putInt(PLAYER_TWO_SCORE, player_two_score);
        outState.putInt(WINNER, winner);
    }

    private void getPlayersCardFromInstance(Bundle savedInstanceState) {
        current_player_one_card = new CardEntry<String, Integer>(
                savedInstanceState.getString(PLAYER_ONE_CURRENT_CARD_IMG),
                savedInstanceState.getInt(PLAYER_ONE_CURRENT_CARD_VALUE)
        );
        current_player_two_card = new CardEntry<String, Integer>(
                savedInstanceState.getString(PLAYER_TWO_CURRENT_CARD_IMG),
                savedInstanceState.getInt(PLAYER_TWO_CURRENT_CARD_VALUE)
        );

        refreshCardView(current_player_one_card.getKey(), current_player_two_card.getKey());
    }

    private void getScoresFromInstance(Bundle savedInstanceState) {
        player_one_score = savedInstanceState.getInt(PLAYER_ONE_SCORE);
        player_two_score = savedInstanceState.getInt(PLAYER_TWO_SCORE);
        winner = savedInstanceState.getInt(WINNER);
        refreshScoreView();
    }

    private void getStacksFromInstance(Bundle savedInstanceState) {
        player1_stack = (Stack<CardEntry<String,Integer>>)savedInstanceState.getSerializable(PLAYER_ONE_STACK);
        player2_stack = (Stack<CardEntry<String,Integer>>)savedInstanceState.getSerializable(PLAYER_TWO_STACK);
    }

    private void findViews() {
        game_lbl_scorePlayer1 = findViewById(R.id.game_LBL_scorePlayer1);
        game_img_card_player1 = findViewById(R.id.game_IMG_card_player1);
        game_lbl_scorePlayer2 = findViewById(R.id.game_LBL_scorePlayer2);
        game_img_card_player2 = findViewById(R.id.game_IMG_card_player2);
        game_btn_play = findViewById(R.id.game_BTN_play);
    }

    private void initViews() {
        game_img_card_player1.setImageResource(R.drawable.card_back);
        game_img_card_player2.setImageResource(R.drawable.card_back);
        refreshScoreView();
        createGameStacks();

        game_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextRound();
            }
        });
    }

    private void nextRound() {
        current_player_one_card = player1_stack.pop();
        current_player_two_card = player2_stack.pop();

        refreshCardView(current_player_one_card.getKey(), current_player_two_card.getKey());

        checkWinner(current_player_one_card.getValue(), current_player_two_card.getValue());

        refreshScoreView();

        if (player1_stack.isEmpty() || player2_stack.isEmpty())
        {
            if (player_one_score == player_two_score){
                createGameStacks();
            }else {
                int drawable_id;

                switch (this.winner){
                    case 1:
                        drawable_id = this.getResources().getIdentifier("black_cat", "drawable", this.getPackageName());
                        openWinnerView(player_one_score, drawable_id);
                        break;
                    case 2:
                        drawable_id = this.getResources().getIdentifier("pirate", "drawable", this.getPackageName());
                        openWinnerView(player_two_score, drawable_id);
                        break;
                }

            }
        }

    }

    private void openWinnerView(int winner_score, int drawable_id) {
        Intent winnerView = new Intent(Game_Activity.this, Winner_Activity.class);
        winnerView.putExtra("winner_score", winner_score);
        winnerView.putExtra("winner_image_id", drawable_id);
        Game_Activity.this.startActivity(winnerView);
    }

    private void refreshCardView(String p1_card_image, String p2_card_image) {
        int player_one_drawable = this.getResources().getIdentifier(p1_card_image, "drawable", this.getPackageName());
        int player_two_drawable = this.getResources().getIdentifier(p2_card_image, "drawable", this.getPackageName());

        game_img_card_player1.setImageResource(player_one_drawable);
        game_img_card_player2.setImageResource(player_two_drawable);

    }

    private void checkWinner(Integer p1_card_value, Integer p2_card_value) {
        if(p1_card_value > p2_card_value){
            player_one_score++;
        }else if(p2_card_value > p1_card_value){
            player_two_score++;
        }

        if (player_one_score > player_two_score){
            winner = 1;
        } else if(player_two_score > player_one_score){
            winner = 2;
        }
        else{
            winner = 0;
        }
    }

    private void refreshScoreView() {
        game_lbl_scorePlayer1.setText(String.valueOf(player_one_score));
        game_lbl_scorePlayer2.setText(String.valueOf(player_two_score));
    }

    private void createGameStacks() {
        int card_value;
        int card_shape;
        boolean player_card_flag = true;
        Random random = new Random();

        /*
        Fill overall stack with all the possible cards.
         */
        for(card_shape=1; card_shape<5; card_shape++)
        {
            for(card_value=2; card_value<15; card_value++){
                String card_key = "poker_card_" + CardShapesEnum.valueOf(card_shape).toString().toLowerCase() + "_" + card_value;
                CardEntry<String, Integer> card_entry = new CardEntry<String, Integer>(card_key, card_value);
                all_card_stack.add(card_entry);
            }
        }

        Collections.shuffle(all_card_stack);

        // Divide the cards between the players.
        while(all_card_stack.size() != 0){
            if(player_card_flag){
                insertCardToPlayerOneStack(all_card_stack.get(0));
                player_card_flag = false;
            }else{
                insertCardToPlayerTwoStack(all_card_stack.get(0));
                player_card_flag = true;
            }
            all_card_stack.remove(0);
        }
    }

    private void insertCardToPlayerOneStack(CardEntry<String, Integer> card_entry) {
        player1_stack.push(card_entry);
    }

    private void insertCardToPlayerTwoStack(CardEntry<String, Integer> card_entry) {
        player2_stack.push(card_entry);
    }
}