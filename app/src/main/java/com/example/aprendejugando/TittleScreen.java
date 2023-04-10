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

    public void accion(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}