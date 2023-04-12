package com.example.aprendejugando;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aprendejugando.memory.cards_managment;

import java.util.ArrayList;

public class MemoryGame extends AppCompatActivity {


    private TextView difficulty_text;
    private TextView score_text;
    private Integer difficulty_level=1;
    private Integer score=0;
    private ArrayList<Integer> flipped_card_id= new ArrayList<Integer>();
    private Integer flipped_cards_count=0;
    private cards_managment card_manager= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);
        difficulty_text=findViewById(R.id.memory_difficulty_text);
        score_text=findViewById(R.id.memory_game_score);
        Intent intent = getIntent();
        if(intent!=null){
            difficulty_level=intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED,0);
        }
        if(difficulty_level==2){
            difficulty_text.setText("Dificultad: Normal");
        }
        else if(difficulty_level==3){
            difficulty_text.setText("Dificultad: Experto");
        }
        else{
            difficulty_text.setText("Dificultad: Facil");
        }

        score_text.setText("Puntuacion: "+ score);
        card_manager= new cards_managment(null, null, getApplicationContext());
    }

    public void flip(View card){
        System.out.println(card.getId());
        if(card_manager.getCard1()==null){
            card_manager.setCard1(card);
        }
        else if(card_manager.getCard2()==null && card!=card_manager.getCard1()){
            card_manager.setCard2(card);
        }


    }
}