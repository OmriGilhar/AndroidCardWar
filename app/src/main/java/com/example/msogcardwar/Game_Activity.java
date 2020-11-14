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

import java.util.Stack;
import java.util.Random;

public class Game_Activity extends AppCompatActivity {
    private final String PLAYER_ONE_SCORE = "P1_SCORE";
    private final String PLAYER_TWO_SCORE = "P2_SCORE";
    private final String WINNER = "WINNER";
    private final String PLAYER_ONE_STACK = "P1_STACK";
    private final String PLAYER_TWO_STACK = "P2_STACK";
    private TextView game_lbl_scorePlayer1;
    private ImageView game_img_card_player1;
    private TextView game_lbl_scorePlayer2;
    private ImageView game_img_card_player2;
    private ImageButton game_btn_play;
    private final Stack<CardEntry<String, Integer>> all_card_stack = new Stack<>();
    private Stack<CardEntry<String, Integer>> player1_stack = new Stack<>();
    private Stack<CardEntry<String, Integer>> player2_stack = new Stack<>();
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
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLAYER_ONE_STACK, player1_stack);
        outState.putSerializable(PLAYER_TWO_STACK, player2_stack);
        outState.putInt(PLAYER_ONE_SCORE, player_one_score);
        outState.putInt(PLAYER_TWO_SCORE, player_two_score);
        outState.putInt(WINNER, winner);
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
        CardEntry<String, Integer> p1 = player1_stack.pop();
        CardEntry<String, Integer> p2 = player2_stack.pop();

        refreshCardView(p1.getKey(), p2.getKey());

        checkWinner(p1.getValue(), p2.getValue());

        refreshScoreView();

        if (player1_stack.isEmpty() || player2_stack.isEmpty())
        {
            Intent winnerView = new Intent(Game_Activity.this, Winner_Activity.class);
            winnerView.putExtra("winner", this.winner);
            Game_Activity.this.startActivity(winnerView);
        }

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