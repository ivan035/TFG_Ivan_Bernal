package com.example.aprendejugando.memory;

import com.example.aprendejugando.games.MemoryGame;

import java.util.TimerTask;

public class MemoryTimer implements Runnable{

    private Thread service;
    private int time;
    private Integer difficulty;
    private MemoryGame memoryGame;

    public MemoryTimer(int time, Integer difficulty, MemoryGame memorygame) {
        this.time = time;
        this.difficulty = difficulty;
        this.service=new Thread(this);
        this.memoryGame=memorygame;
    }

    public Thread getService() {
        return service;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                memoryGame.updateTimer(time);
                if(time<=0) {
                    time=0;

                    timer.cancel();
                    memoryGame.finish_game();
                }
                time--;
            }
        }, 0, 1000);
    }

}
