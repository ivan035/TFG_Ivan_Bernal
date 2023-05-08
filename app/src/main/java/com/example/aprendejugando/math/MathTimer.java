package com.example.aprendejugando.math;

import com.example.aprendejugando.games.MathGame;
import com.example.aprendejugando.games.MemoryGame;

import java.util.TimerTask;

public class MathTimer implements Runnable{

    private Thread service;
    private int time;
    private MathGame mathGame;

    public MathTimer(int time, MathGame mathgame) {
        this.time = time;
        this.service=new Thread(this);
        this.mathGame=mathgame;
    }

    public Thread getService() {
        return service;
    }
    public void setTime(Integer time) {
        this.time=time;
    }
    public int getTime() {
        return time;
    }

    @Override
    public void run() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if(time<=0) {
                    time=0;

                    timer.cancel();
                    mathGame.finishGame();
                }
                mathGame.updateTimer(time);
                time--;
            }
        }, 0, 1000);
    }
}
