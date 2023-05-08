package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aprendejugando.games.MathGame;
import com.example.aprendejugando.games.MemoryGame;
import com.example.aprendejugando.games.BlobGame;

public class DifficultySelection extends AppCompatActivity {

    private TextView game_tittle;
    private TextView dog_dialogue;
    private TextView easy_option;
    private TextView normal_option;
    private TextView expert_option;
    public static String DIFFICULTY_SELECTED = "com.example.aprendejugando.games.MemoryGame.difficulty";
    private MediaPlayer mediaPlayer;
    private String game_name;

    //We set the default difficulty level to easy
    private int difficulty=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_selection);
        //We match the views needed with our variables
        game_tittle= findViewById(R.id.difficulty_selection_game_name);
        dog_dialogue= findViewById(R.id.difficulty_selection_dog_dialogue);
        easy_option= findViewById(R.id.difficulty_selection_easy_option);
        normal_option=findViewById(R.id.difficulty_selection_normal_option);
        expert_option=findViewById(R.id.difficulty_selection_expert_option);

        //The activity catches the name of the wanted game and we set it to the tittle name
        Intent intent = getIntent();
        if(intent!=null){
            game_name=intent.getStringExtra(MainMenu.ACTIVITY_NAME);
            String default_text = getString(R.string.empty_activity_name);
            game_tittle.setText(default_text+" "+game_name);
        }

        dog_dialogue.setText(getString(R.string.difficulty_selection_default_dialogue));
    }


    public void easy_mode(View view) {
        //If user select easy mode, the "difficulty" variable will be set to 1
        //It will also change the easy button color and set the others to the starter color
        sound();
        difficulty=1;
        easy_option.setBackgroundResource(R.drawable.shape_rectangle_option_selected);
        normal_option.setBackgroundResource(R.drawable.shape_rectangle_option);
        expert_option.setBackgroundResource(R.drawable.shape_rectangle_option);

        easy_option.setTextColor(getColor(R.color.white));
        normal_option.setTextColor(getColor(R.color.carolina_blue));
        expert_option.setTextColor(getColor(R.color.carolina_blue));
    }
    public void normal_mode(View view) {
        //If user select easy mode, the "difficulty" variable will be set to 2
        //It will also change the easy button color and set the others to the starter color
        sound();
        difficulty=2;
        normal_option.setBackgroundResource(R.drawable.shape_rectangle_option_selected);
        normal_option.setTextColor(getColor(R.color.white));
        easy_option.setBackgroundResource(R.drawable.shape_rectangle_option);
        expert_option.setBackgroundResource(R.drawable.shape_rectangle_option);

        normal_option.setTextColor(getColor(R.color.white));
        easy_option.setTextColor(getColor(R.color.carolina_blue));
        expert_option.setTextColor(getColor(R.color.carolina_blue));
    }
    public void expert_mode(View view) {
        //If user select easy mode, the "difficulty" variable will be set to 3
        //It will also change the easy button color and set the others to the starter color
        sound();
        difficulty=3;
        expert_option.setBackgroundResource(R.drawable.shape_rectangle_option_selected);
        expert_option.setTextColor(getColor(R.color.white));
        easy_option.setBackgroundResource(R.drawable.shape_rectangle_option);
        normal_option.setBackgroundResource(R.drawable.shape_rectangle_option);

        expert_option.setTextColor(getColor(R.color.white));
        easy_option.setTextColor(getColor(R.color.carolina_blue));
        normal_option.setTextColor(getColor(R.color.carolina_blue));
    }

    public void sound(){
        //It will start a sound whenever an option is choosen
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(this, R.raw.menu_sound);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, R.raw.menu_sound);
            mediaPlayer.start();
        }
    }

    public void start_game(View view) {
        //When the user start the game we check which activity it will start by the name and
        // pass the difficulty value
        MainMenu.menu_music.release();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.start_game_sound);
        mediaPlayer.start();
        if(game_name.equalsIgnoreCase(getResources().getString(R.string.main_menu_memory_name))){
            Intent intent = new Intent(this, MemoryGame.class);
            intent.putExtra(DIFFICULTY_SELECTED, difficulty);
            startActivity(intent);
            finish();
        }
        if(game_name.equalsIgnoreCase(getResources().getString(R.string.main_menu_blob_name))){
            Intent intent = new Intent(this, BlobGame.class);
            intent.putExtra(DIFFICULTY_SELECTED, difficulty);
            startActivity(intent);
            finish();
        }
        if(game_name.equalsIgnoreCase(getResources().getString(R.string.main_menu_math_game))){
            Intent intent = new Intent(this, MathGame.class);
            intent.putExtra(DIFFICULTY_SELECTED, difficulty);
            startActivity(intent);
            finish();
        }
    }
}