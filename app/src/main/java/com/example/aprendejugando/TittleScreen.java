package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TittleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tittle_screen);
    }

    public void StartGame(View view) {
        //When the user click the screen it will start a MainMenu Activity and finish this activity
        //so the user cant go back to the start screen
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}