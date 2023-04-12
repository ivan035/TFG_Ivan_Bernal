package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DifficultySelection extends AppCompatActivity {

    private TextView game_tittle;
    private TextView dog_dialogue;
    private TextView easy_option;
    private TextView normal_option;
    private TextView expert_option;
    public static String DIFFICULTY_SELECTED = "com.example.aprendejugando.MemoryGame.difficulty";
    private String game_name;

    //We set the default difficulty level to easy
    private int difficulty=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_selection);
        game_tittle= findViewById(R.id.difficulty_selection_game_name);
        dog_dialogue= findViewById(R.id.difficulty_selection_dog_dialogue);
        easy_option= findViewById(R.id.difficulty_selection_easy_option);
        normal_option=findViewById(R.id.difficulty_selection_normal_option);
        expert_option=findViewById(R.id.difficulty_selection_expert_option);

        Intent intent = getIntent();
        if(intent!=null){
            game_name=intent.getStringExtra(MainMenu.ACTIVITY_NAME);
            String default_text = getString(R.string.empty_activity_name);
            game_tittle.setText(default_text+" "+game_name);
        }

        dog_dialogue.setText(getString(R.string.difficulty_selection_default_dialogue));

    }

    public void easy_mode(View view) {
        difficulty=1;
        System.out.println(difficulty);
        easy_option.setBackgroundColor(getResources().getColor(R.color.selected, null));
        normal_option.setBackgroundColor(getResources().getColor(R.color.white, null));
        expert_option.setBackgroundColor(getResources().getColor(R.color.white, null));
    }
    public void normal_mode(View view) {
        difficulty=2;
        System.out.println(difficulty);
        normal_option.setBackgroundColor(getResources().getColor(R.color.selected, null));
        easy_option.setBackgroundColor(getResources().getColor(R.color.white, null));
        expert_option.setBackgroundColor(getResources().getColor(R.color.white, null));
    }
    public void expert_mode(View view) {
        difficulty=3;
        System.out.println(difficulty);
        expert_option.setBackgroundColor(getResources().getColor(R.color.selected, null));
        easy_option.setBackgroundColor(getResources().getColor(R.color.white, null));
        normal_option.setBackgroundColor(getResources().getColor(R.color.white, null));
    }
    public void start_game(View view) {
        if(game_name.equalsIgnoreCase(getResources().getString(R.string.main_menu_memory_name))){
            Intent intent = new Intent(this, MemoryGame.class);
            intent.putExtra(DIFFICULTY_SELECTED, difficulty);
            startActivity(intent);
            finish();
        }
    }
}