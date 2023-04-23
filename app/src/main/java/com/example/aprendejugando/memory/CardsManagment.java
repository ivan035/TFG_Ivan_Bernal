package com.example.aprendejugando.memory;

import static java.lang.Thread.sleep;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.example.aprendejugando.R;
import com.example.aprendejugando.games.MemoryGame;

public class CardsManagment implements Runnable{
    private MemoryGame memorygame;
    private View card1;
    private View card2;

    private int card1_value;
    private int card2_value;
    private Context context;
    private Thread service;

    public CardsManagment(View card1, View card2, Context context, MemoryGame memorygame) {
        this.card1 = card1;
        this.card2 = card2;
        this.memorygame=memorygame;
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

    public int getCard1_value() {
        return card1_value;
    }

    public void setCard1_value(int card1_value) {
        this.card1_value = card1_value;
    }

    public int getCard2_value() {
        return card2_value;
    }

    public void setCard2_value(int card2_value) {
        this.card2_value = card2_value;
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

    public void makeCardsInvisible(){
        card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(cardPairs()) {
            memorygame.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    makeCardsInvisible();
                    setCard1(null);
                    setCard2(null);
                }
            });
        }
        else{
            setCard1(null);
            setCard2(null);
        }

    }

    private boolean cardPairs() {
        if(card1_value==card2_value){
            return true;
        }
        return false;
    }
}
