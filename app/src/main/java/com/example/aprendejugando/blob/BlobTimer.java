package com.example.aprendejugando.blob;

import com.example.aprendejugando.games.BlobGame;

import java.util.TimerTask;

public class BlobTimer implements Runnable{

    //This will be the timer of the game, it will also care to add more blobs every X seconds

    private BlobGame game;
    private Thread service;
    private int time;
    private double time_to_add_new_blob;
    private Integer count_to_increase_blob_time_spawn=0;
    private Integer difficulty;
    private final double needed_spawn_time=6.0;

    public BlobTimer(int time, BlobGame game, double starter_add_blob_time, Integer difficulty) {
        this.time = time;
        this.game=game;
        this.time_to_add_new_blob=starter_add_blob_time;
        this.difficulty=difficulty;
        this.service=new Thread(this);
    }

    public Thread getService() {
        return service;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void run() {
        //This will start a timer, wich will run every second
        game.add_new_blob();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            double add_blob_timer=0.0;
            @Override
            public void run() {
                synchronized (this){
                    //Set the time in the text so the player can see it
                    game.set_timer_text(time);

                    //Increase the add_blob_timer with the time we want ( time_to_add_new_blob )
                    add_blob_timer += time_to_add_new_blob;

                    //If the timer reaches the spawn time it spawns a blob
                    if(add_blob_timer >=needed_spawn_time){
                        game.add_new_blob();
                        add_blob_timer=0;
                    }

                    //Every spawn increases the time it takes to spawn ( time_to_add_new_blob )
                    if(count_to_increase_blob_time_spawn==5 && time_to_add_new_blob<=needed_spawn_time){
                        if(difficulty==1){
                            time_to_add_new_blob+=0.6;
                        }
                        else if(difficulty==2){
                            time_to_add_new_blob+=0.9;
                        }
                        else if(difficulty==3){
                            time_to_add_new_blob+=0.80;
                        }
                        count_to_increase_blob_time_spawn=0;
                    }

                    //If the time is 0 it will finish the game and finish the runnable
                    if(time<=0) {
                        time=0;
                        game.set_timer_text(time);
                        game.finish_game();
                        timer.cancel();
                    }
                    //Every loop we increase the counter to spawn a blob and decrease the time by 1
                    count_to_increase_blob_time_spawn++;
                    time--;
                }
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it every 1000ms (1second)
    }

}
