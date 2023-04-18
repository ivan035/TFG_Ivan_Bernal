package com.example.aprendejugando.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.R;
import com.example.aprendejugando.sauce.Timer;

public class SauceGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView score_text;
    private TextView timer_text;
    private Timer timer;
    private Integer difficulty_level=1;
    private Integer score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sauce_game);
        difficulty_text=findViewById(R.id.sauce_difficulty_text);
        score_text=findViewById(R.id.sauce_game_score);
        timer_text=findViewById(R.id.sauce_game_timer_text);

        Intent intent = getIntent();
        if(intent!=null){
            difficulty_level=intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED,0);
        }
        if(difficulty_level==2){
            difficulty_text.setText("Dificultad: Normal");

        }
        else if(difficulty_level==3){
            difficulty_text.setText("Dificultad: Experto");
        }
        else{
            difficulty_text.setText("Dificultad: Facil");
        }
        score_text.setText("Puntuacion: "+score);
        timer = new Timer(10,timer_text);
    }

    public void clean(View view) {
        if(timer.getService().getState().equals(Thread.State.NEW)){
            timer.getService().start();
        }
        if(timer.getTime()!=0){
            float alpha=view.getAlpha();
            alpha= (float) (alpha-0.5);
            view.setAlpha(alpha);
            alpha=view.getAlpha();
            if(alpha<=0.2){
                score++;
                score_text.setText("Puntuacion: "+score);
                view.setVisibility(View.INVISIBLE);
                view.setTranslationX((int) (Math.random()*700+1));
                view.setTranslationY((int) (Math.random()*1000+30));
                System.out.println("x: "+view.getTranslationX()+ " y: "+view.getTranslationY());
                view.setVisibility(View.VISIBLE);
                view.setAlpha(1);
            }
        }
    }
}