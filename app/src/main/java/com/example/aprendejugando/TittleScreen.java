package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TittleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tittle_screen);

        String actual_language = String.valueOf(getResources().getConfiguration().getLocales().get(0));
        if(actual_language.equalsIgnoreCase("es")){
            setLocal("es");
        }
        else{
            setLocal("en");
        }
    }

    public void StartGame(View view) {
        //When the user click the screen it will start a MainMenu Activity and finish this activity
        //so the user cant go back to the start screen
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void setLocal(String language){
        //This will change the locale language and restart the activity
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(language);
        AppCompatDelegate.setApplicationLocales(appLocale);
    }
}