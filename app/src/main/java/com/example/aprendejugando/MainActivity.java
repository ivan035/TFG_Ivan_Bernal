package com.example.aprendejugando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void accion(View view) {
        Toast t = Toast.makeText(this,"Iniciando...",Toast.LENGTH_SHORT);
        t.show();
    }
}