package com.example.aprendejugando.games;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.MainMenu;
import com.example.aprendejugando.R;
import com.example.aprendejugando.memory.Card;
import com.example.aprendejugando.memory.MemoryTimer;
import com.example.aprendejugando.memory.StageValue;
import com.example.aprendejugando.memory.CardsManagment;

import java.util.ArrayList;

public class MemoryGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView score_text;
    private TextView game_end_text;
    private TextView dog_dialogue;
    private ImageView dog_image;
    private ProgressBar time_bar;
    private TextView time_text;
    private TextView stages_completed_text;
    private Button to_menu;

    private Integer difficulty_level=1;
    public Integer score=0;
    private ArrayList<ImageView> cards_view;
    private StageValue table;
    public Integer stages_completed=0;
    private ArrayList<Card> currentStage;
    public Integer pairs_found=0;
    private CardsManagment card_manager= null;
    public MemoryTimer timer;
    //Media player will be needed to play music and sounds
    private MediaPlayer music;
    private MediaPlayer mediaPlayer;
    private MediaPlayer correct;
    private final Integer GAME_TIME=60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_game);
        //We match the views needed with our variables
        difficulty_text=findViewById(R.id.memory_difficulty_text);

        time_text=findViewById(R.id.memory_game_time_text);
        time_bar=findViewById(R.id.memory_game_time_bar);

        stages_completed_text=findViewById(R.id.memory_game_stages_completed);
        String default_stages_text = getString(R.string.memory_game_table_stage);
        stages_completed_text.setText(String.format(default_stages_text,0));

        score_text=findViewById(R.id.memory_game_score);
        String default_score_text = getString(R.string.global_score);
        score_text.setText(String.format(default_score_text,0));

        dog_dialogue=findViewById(R.id.memory_game_dog_dialogue);
        dog_image=findViewById(R.id.memory_game_img_dog);
        game_end_text = findViewById(R.id.memory_game_end_text);
        to_menu=findViewById(R.id.memory_game_tomenu_button);
        time_bar.setMax(GAME_TIME);
        time_bar.setProgress(GAME_TIME,true);

        String default_mode_text = getString(R.string.global_mode);
        Intent intent = getIntent();
        if(intent!=null){
            difficulty_level=intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED,0);
        }
        if(difficulty_level==2){
            String default_level_text = getString(R.string.difficulty_normal);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
        }
        else if(difficulty_level==3){
            String default_level_text = getString(R.string.difficulty_hard);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
        }
        else{
            String default_level_text = getString(R.string.difficulty_easy);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
        }
        card_manager= new CardsManagment(null, null, getApplicationContext(),this);
        timer=new MemoryTimer(GAME_TIME, this);
        game_music();
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
                cards.add(card5);
                cards.add(card6);
                cards.add(card7);
                cards.add(card8);
                cards.add(card9);
            }
        return cards;
    }


    public void flip(View card){
        if(timer.getTime()<=0){
            return;
        }
        Integer value= 0;
        if(card_manager.getCard1()==null){

            for (Card card_value : currentStage) {
                if (String.valueOf(card_value.getId()).equalsIgnoreCase(String.valueOf(card.getId()))) {
                        value=card_value.getValue();
                }
            }
            card_manager.setCard1_value(value);
            card_manager.setCard1(card);
        }

        else if(card_manager.getCard2()==null && card!=card_manager.getCard1()){

            for (Card card_value:currentStage) {
                    if (String.valueOf(card_value.getId()).equalsIgnoreCase(String.valueOf(card.getId()))) {
                        value = card_value.getValue();
                    }
            }
            card_manager.setCard2_value(value);
            card_manager.setCard2(card);
        }


    }

    public void updateScore() {
        //It will update the score text
        String default_score_text = getString(R.string.global_score);
        score_text.setText(String.format(default_score_text,score));
        pairs_found++;
        blackie_action();
    }

    public void updateTimer(Integer time){
        //It will update the timer text and the timer bar
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                time_bar.setProgress(time);
                time_text.setText(String.valueOf(time));
            }
        });
    }


    private void blackie_action() {
        //Whenever you match a pair it will throw a random number to change the text and
        //image of the dog
        //Action has a larger range that it should, so it will change just sometimes
        // and not always
        int action = (int) (Math.random() * 7 + 1);
        if (action == 1) {
            String text = getResources().getString(R.string.memory_game_dog_action1);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_impressed);
        }
        if (action == 2) {
            String text = getResources().getString(R.string.memory_game_dog_action2);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 3) {
            String text = getResources().getString(R.string.memory_game_dog_action3);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_front);
        }
        if (action == 4) {
            String text = getResources().getString(R.string.memory_game_dog_action4);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 5) {
            String text = getResources().getString(R.string.memory_game_dog_action5);
            dog_dialogue.setText(String.format(text,score));
            dog_image.setImageResource(R.drawable.blackie_howl);
        }
    }

    public void createNewStage() {
        cards_view=getCurrentCardsView();
        table=new StageValue(cards_view);
        currentStage=table.create_stage();
        if(difficulty_level == 1){
            for (ImageView card_view: cards_view) {
                card_view.setVisibility(View.VISIBLE);
            }
        }
        if(difficulty_level == 2){
            for (ImageView card_view: cards_view) {
                card_view.setVisibility(View.VISIBLE);
            }
        }
        if(difficulty_level == 3){
            for (ImageView card_view: cards_view) {
                card_view.setVisibility(View.VISIBLE);
            }
        }
        String default_stages_text = getString(R.string.memory_game_table_stage);
        stages_completed_text.setText(String.format(default_stages_text,stages_completed));
    }

    public void start_game(View view){
        createNewStage();
        stages_completed=0;
        timer.getService().start();
        view.setVisibility(View.INVISIBLE);
    }

    private void blackie_action_lose() {
        //When the game ends, it changes dog image and text
        String blackie_game_over_text = getResources().getString(R.string.blackie_lose_default_text);
        dog_dialogue.setText(String.format(blackie_game_over_text, score));
        dog_image.setImageResource(R.drawable.blackie_sleep);
    }

    public void finish_game() {
        //Interrupts the timer Thread and makes visible game over texts and button to main menu
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                to_menu.setVisibility(View.VISIBLE);
                String game_over = getResources().getString(R.string.global_game_over);
                game_end_text.setText(String.format(game_over, score));
                blackie_action_lose();
                game_end_text.setVisibility(View.VISIBLE);
            }
        });
    }

    public void finish_game(View view) {
        //Ends this activity
        if(music!=null){
            music.release();
        }
        finish();
    }

    public void game_music(){
        if(MainMenu.global_music){
            music = MediaPlayer.create(this, R.raw.memory_game_music);
            music.setLooping(true);
            music.start();
        }
    }

    public void flip_sound() {
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(this, R.raw.card_sound);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, R.raw.card_sound);
            mediaPlayer.start();
        }
    }

    public void correct_sound(){
        if(correct==null){
            correct = MediaPlayer.create(this, R.raw.memory_correct_sound);
            correct.setVolume(0.4f,04f);
            correct.start();
        }
        else{
            correct.release();
            correct = MediaPlayer.create(this, R.raw.memory_correct_sound);
            correct.setVolume(0.4f,04f);
            correct.start();
        }
    }

    @Override
    public void onBackPressed() {
        //If the user press the "back" button in the mobile, it will stop the music and finish the
        // activity
        super.onBackPressed();
        if(music!=null){
            music.release();
        }
        finish();
    }
}