package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "com.example.aprendejugando.main_menu.name";
    private TextView dog_dialogue;
    private ImageView dog_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We match the views needed with our variables
        setContentView(R.layout.main_menu);
        dog_dialogue = findViewById(R.id.main_menu_dog_dialogue);
        dog_image = findViewById(R.id.main_menu_img_dog);
    }

    public void pet_blackie(View view) {
        //If the dog image is clicked, we change the dog image and his text
        int action =(int) (Math.random()*2+1);
        if (action==1){
            dog_dialogue.setText("Me haces cosquillas!");
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        else{
            dog_dialogue.setText("Goof Goof");
            dog_image.setImageResource(R.drawable.blackie_talking);
        }
    }

    public void startMemoryGame(View view) {
        //We start "DifficultySelection" and give the name as an argument to start that activity
        Intent intent = new Intent(this, DifficultySelection.class);
        intent.putExtra(ACTIVITY_NAME,"Memoria");
        startActivity(intent);
    }
    public void startBlobGame(View view) {
        //We start "DifficultySelection" and give the name as an argument to start that activity
        Intent intent = new Intent(this, DifficultySelection.class);
        intent.putExtra(ACTIVITY_NAME,"Invasion de Manchas");
        startActivity(intent);
    }
}