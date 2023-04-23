package com.example.aprendejugando.games;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.R;
import com.example.aprendejugando.memory.Card;
import com.example.aprendejugando.memory.StageValue;
import com.example.aprendejugando.memory.CardsManagment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MemoryGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView score_text;
    private Integer difficulty_level=1;
    private Integer score=0;
    private ArrayList<ImageView> cards_view;
    private StageValue table;
    private ArrayList<Card> currentStage;
    private CardsManagment card_manager= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);
        //We match the views needed with our variables
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
        cards_view=getCurrentCardsView();
        table=new StageValue(cards_view);
        currentStage=table.create_stage();
        score_text.setText("Puntuacion: "+ score);
        card_manager= new CardsManagment(null, null, getApplicationContext(),this);
    }

    private ArrayList<ImageView> getCurrentCardsView() {
        ArrayList<ImageView> cards= new ArrayList<>();
        ImageView card1 = findViewById(R.id.memory_card1);
        ImageView card2 = findViewById(R.id.memory_card2);
        ImageView card3 = findViewById(R.id.memory_card3);
        ImageView card4 = findViewById(R.id.memory_card4);
        ImageView card5 = findViewById(R.id.memory_card5);
        ImageView card6 = findViewById(R.id.memory_card6);
        ImageView card7 = findViewById(R.id.memory_card7);
        ImageView card8 = findViewById(R.id.memory_card8);
        ImageView card9 = findViewById(R.id.memory_card9);
            if(difficulty_level==1) {
                cards.add(card1);
                cards.add(card2);
                cards.add(card3);
                cards.add(card4);
                cards.add(card6);
                cards.add(card7);
                cards.add(card8);
                cards.add(card9);
                card5.setVisibility(View.INVISIBLE);
            }
            if(difficulty_level==2){
                cards.add(card1);
                cards.add(card2);
                cards.add(card3);
                cards.add(card4);
                cards.add(card5);
                cards.add(card6);
                cards.add(card7);
                cards.add(card8);
                cards.add(card9);
                }
            if(difficulty_level==3){
                cards.add(card1);
                cards.add(card2);
                cards.add(card3);
                cards.add(card4);
                cards.add(card6);
                cards.add(card7);
                cards.add(card8);
                cards.add(card9);
            }
        return cards;
    }


    public void flip(View card){
        for (Card card_1:currentStage) {
            System.out.println(card_1.toString());

        }
        Integer value= 0;
        if(card_manager.getCard1()==null){

            for (Card card_value : currentStage) {
                if (String.valueOf(card_value.getId()).equalsIgnoreCase(String.valueOf(card.getId()))) {
                        System.out.println(card_value.getValue());
                        value=card_value.getValue();
                }
            }
            card_manager.setCard1_value(value);
            card_manager.setCard1(card);
        }

        else if(card_manager.getCard2()==null && card!=card_manager.getCard1()){

            for (Card card_value:currentStage) {
                    if (String.valueOf(card_value.getId()).equalsIgnoreCase(String.valueOf(card.getId()))) {
                        System.out.println(card_value.getValue());
                        value = card_value.getValue();
                    }
            }
            card_manager.setCard2_value(value);
            card_manager.setCard2(card);
        }


    }




}