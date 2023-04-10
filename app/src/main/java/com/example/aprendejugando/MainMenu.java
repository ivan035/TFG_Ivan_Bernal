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
        setContentView(R.layout.main_menu);
        dog_dialogue = findViewById(R.id.main_menu_dog_dialogue);
        dog_image = findViewById(R.id.main_menu_img_dog);
    }

    public void pet_blackie(View view) {
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
        Intent intent = new Intent(this, DifficultySelection.class);
        intent.putExtra(ACTIVITY_NAME,"Memoria");
        startActivity(intent);
    }
}