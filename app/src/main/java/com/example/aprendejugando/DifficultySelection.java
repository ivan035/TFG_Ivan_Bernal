package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DifficultySelection extends AppCompatActivity {

    private TextView game_tittle;
    private TextView dog_dialogue;
    private String game_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_selection);
        game_tittle= findViewById(R.id.difficulty_selection_game_name);
        dog_dialogue= findViewById(R.id.difficulty_selection_dog_dialogue);
        Intent intent = getIntent();
        if(intent!=null){
            game_name=intent.getStringExtra(MainMenu.ACTIVITY_NAME);
            String default_text = getString(R.string.empty_activity_name);
            game_tittle.setText(default_text+" "+game_name);
            dog_dialogue.setText("Elige la dificultad del juego");
        }

    }
}