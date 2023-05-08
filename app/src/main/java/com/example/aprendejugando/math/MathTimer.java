package com.example.aprendejugando.math;

import com.example.aprendejugando.games.MathGame;
import com.example.aprendejugando.games.MemoryGame;

import java.util.TimerTask;

public class MathTimer implements Runnable{
    //This will be the timer of the math game

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
                //Every loop it updates the timer and decrease the time by 1
                time--;
                mathGame.updateTimer(time);
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it every 1000ms (1second)
    }
}
