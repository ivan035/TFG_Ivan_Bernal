package com.example.aprendejugando.memory;

import com.example.aprendejugando.games.MemoryGame;

import java.util.TimerTask;

public class MemoryTimer implements Runnable{
    //This class will be the Memory game timer

    private Thread service;
    private int time;
    private MemoryGame memoryGame;

    public MemoryTimer(int time, MemoryGame memorygame) {
        this.time = time;
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
                //Every loop decrease the time by 1
                time--;
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it every 1000ms (1second)
    }

}
