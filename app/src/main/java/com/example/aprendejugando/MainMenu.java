package com.example.aprendejugando;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainMenu extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "com.example.aprendejugando.main_menu.name";
    private TextView dog_dialogue;
    private ImageView dog_image;
    private ImageView music_option;
    private ImageView language_option;
    public static MediaPlayer menu_music;
    public static Boolean global_music=true;
    private String language="en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We match the views needed with our variables
        setContentView(R.layout.main_menu);
        dog_dialogue = findViewById(R.id.main_menu_dog_dialogue);
        dog_image = findViewById(R.id.main_menu_img_dog);
        music_option=findViewById(R.id.main_menu_option_sound);
        language_option = findViewById(R.id.main_menu_lenguage_option_image);



        if(!global_music){
            music_option.setBackgroundResource(R.drawable.option_sound_background_disabled);
            music_option.setImageDrawable(getDrawable(R.drawable.option_sound_disabled));
        }
        else{
            music();
        }
        String actual_language = String.valueOf(getResources().getConfiguration().getLocales().get(0));
        if(actual_language.equalsIgnoreCase("es")){
            language="es";
            language_option.setImageDrawable(getDrawable(R.drawable.option_spanish));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(global_music){
            if(menu_music==null){
                music();
            }
            else{
                menu_music.release();
                music();
            }
        }
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
        String game = getResources().getString(R.string.main_menu_memory_name);
        intent.putExtra(ACTIVITY_NAME,game);
        startActivity(intent);
    }
    public void startBlobGame(View view) {
        //We start "DifficultySelection" and give the name as an argument to start that activity
        Intent intent = new Intent(this, DifficultySelection.class);
        String game = getResources().getString(R.string.main_menu_blob_name);
        intent.putExtra(ACTIVITY_NAME,game);
        startActivity(intent);
    }
    public void startMathGame(View view) {
        //We start "DifficultySelection" and give the name as an argument to start that activity
        Intent intent = new Intent(this, DifficultySelection.class);
        String game = getResources().getString(R.string.main_menu_math_game);
        intent.putExtra(ACTIVITY_NAME,game);
        startActivity(intent);
    }

    public void music(){
        //If the music is not loaded it will load it and start it, otherwise it
        // will release it and start it again

        if(menu_music==null){
            menu_music = MediaPlayer.create(this, R.raw.menu_music);
            menu_music.setLooping(true);
            menu_music.start();
        }
        else{
            menu_music.release();
            menu_music = MediaPlayer.create(this, R.raw.menu_music);
            menu_music.setLooping(true);
            menu_music.start();
        }
    }

    public void mute_music(View view) {
        //When the sound button is clicked, it will turn the global config music off, but if
        // its turned off already it will turn on

        if(MainMenu.global_music==true){
            MainMenu.global_music=false;
            music_option.setBackgroundResource(R.drawable.option_sound_background_disabled);
            music_option.setImageDrawable(getDrawable(R.drawable.option_sound_disabled));

            menu_music.release();
        }
        else{
            MainMenu.global_music=true;
            music_option.setBackgroundResource(R.drawable.option_sound_background_enabled);
            music_option.setImageDrawable(getDrawable(R.drawable.option_sound_enabled));
            music();
        }

    }

    public void change_language(View view) {
        //When the language button is clicked, it will call setLocal with the desired language
        if(language.equalsIgnoreCase("en")){
            setLocal("es");
        }
        else{
            setLocal("en");
        }

    }

    public void setLocal(String language){
        //This will change the locale language and restart the activity
        menu_music.release();
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(language);
        AppCompatDelegate.setApplicationLocales(appLocale);
    }
}