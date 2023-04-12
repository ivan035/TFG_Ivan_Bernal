package com.example.aprendejugando.memory;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.aprendejugando.R;

import java.util.ArrayList;

public class cards_managment implements Runnable{
    private View card1;
    private View card2;

    private Context context;
    private Thread service;

    public cards_managment(View card1, View card2,Context context) {
        this.card1 = card1;
        this.card2 = card2;
        this.context=context;
    }

    public Thread getService() {
        return service;
    }

    public View getCard1() {
        return card1;
    }

    public void setCard1(View card1) {
        this.card1 = card1;
        if(card1!=null){
            showCard1();
        }
    }

    public View getCard2() {
        return card2;
    }

    public void setCard2(View card2) {
        this.card2 = card2;
        if(card2!=null){
            showCard2();
        }
    }

    public void showCard1(){
        ObjectAnimator show_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_show);
        show_card.setTarget(card1);
        show_card.setDuration(1000);
        show_card.start();
    }

    public void showCard2(){
        ObjectAnimator show_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_show);
        show_card.setTarget(card2);
        show_card.setDuration(1000);
        show_card.start();
        this.service=new Thread(this);
        service.start();

    }

    public void hideCard1(){
        ObjectAnimator hide_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_hide);
        hide_card.setDuration(1000);
        hide_card.setTarget(card1);
        hide_card.start();

    }

    public void hideCard2(){
        ObjectAnimator hide_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_hide);
        hide_card.setDuration(1000);
        hide_card.setTarget(card2);
        hide_card.start();

    }

    @Override
    public void run() {

        try {
            sleep(1000);
            card1.post(new Runnable() {
                public void run() {
                    hideCard1();
                }
            });
            card2.post(new Runnable() {
                public void run() {
                    hideCard2();
                }
            });
            sleep(200);
            setCard1(null);
            setCard2(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
