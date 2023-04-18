package com.example.aprendejugando.sauce;

import android.widget.TextView;

import java.util.TimerTask;

public class Timer implements Runnable{

    private Thread service;
    private int time;
    private TextView timer_text;

    public Timer(int time, TextView timer) {
        this.timer_text= timer;
        this.time = time;
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
        timer. schedule(new TimerTask() {
            @Override
            public void run() {
                time--;
                timer_text.setText("Quedan " + time + " segundos");
                if(time<=0) {

                    timer.cancel();
                }
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)
    }
}
