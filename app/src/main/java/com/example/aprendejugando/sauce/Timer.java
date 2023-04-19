package com.example.aprendejugando.sauce;

import android.widget.TextView;

import com.example.aprendejugando.games.SauceGame;

import java.util.TimerTask;

public class Timer implements Runnable{

    private TextView timer_text;
    private SauceGame game;
    private Thread service;
    private int time;
    private double add_blob_time=0;
    private Integer count_to_increase_blob_time_spawn=0;
    private Integer difficulty;

    public Timer(int time, TextView timer,SauceGame game, double starter_add_blob_time, Integer difficulty) {
        this.timer_text= timer;
        this.time = time;
        this.game=game;
        this.add_blob_time=starter_add_blob_time;
        this.difficulty=difficulty;
        this.service=new Thread(this);
    }

    public Thread getService() {
        return service;
    }

    public void setService(Thread service) {
        this.service = service;
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
            double add_blob_count=0.0;
            @Override
            public void run() {
                synchronized (this){

                    timer_text.setText("Quedan " + time + " segundos");
                    add_blob_count=add_blob_count+add_blob_time;
                    time--;
                    count_to_increase_blob_time_spawn++;
                    if(add_blob_count>=6.0){
                        game.add_new_blob();
                        add_blob_count=0;
                    }
                    if(count_to_increase_blob_time_spawn==5 && add_blob_time<=6.0){
                        if(difficulty==1){
                            add_blob_time=add_blob_time+0.6;
                        }
                        else if(difficulty==2){
                            add_blob_time=add_blob_time+0.9;
                        }
                        else if(difficulty==3){
                            add_blob_time=add_blob_time+0.80;
                        }
                        count_to_increase_blob_time_spawn=0;
                    }
                    if(time<=0) {
                        timer.cancel();
                        time=0;
                        timer_text.setText("Quedan " + time + " segundos");
                        game.finish_game();
                    }
                }
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)
    }

    public void finish_game(){
        synchronized (this){
            time=0;
        }
    }

}
