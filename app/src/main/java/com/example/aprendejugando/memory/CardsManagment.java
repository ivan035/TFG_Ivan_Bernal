package com.example.aprendejugando.memory;

import static java.lang.Thread.sleep;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.aprendejugando.R;
import com.example.aprendejugando.games.MemoryGame;

import java.util.TimerTask;

public class CardsManagment implements Runnable{
    //This class will manage the cards and is the class which match them

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

    public View getCard1() {
        return card1;
    }

    public void setCard1(View card1) {
        //When we set a card, it will call showCard1
        this.card1 = card1;
        if(card1!=null){
            showCard1();
        }
    }

    public View getCard2() {
        return card2;
    }

    public void setCard2(View card2) {
        //When we set a card, it will call showCard2
        this.card2 = card2;
        if(card2!=null){
            showCard2();
        }
    }

    public void setCard1_value(int card1_value) {
        this.card1_value = card1_value;
    }

    public void setCard2_value(int card2_value) {
        this.card2_value = card2_value;
    }

    public void showCard1(){
        //The card 1 will play the animation and reveal the card Image, it will also make the card
        // flip sound
        ObjectAnimator show_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_show);
        show_card.setTarget(card1);
        show_card.setDuration(800);
        show_card.start();
        showImageAnimation(card1,card1_value);
        memorygame.flip_sound();
    }

    public void showCard2(){
        //The card 2 will play the animation and reveal the card Image, it will also make the card
        // flip sound. Then it will start the runnable to check if the card matches
        ObjectAnimator show_card = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_show);
        show_card.setTarget(card2);
        show_card.setDuration(800);
        show_card.start();
        showImageAnimation(card2,card2_value);
        memorygame.flip_sound();
        this.service=new Thread(this);
        service.start();

    }

    public void hideCards(){
        //We start the hide animation of both cards and start it. Then we will check if the cards
        // matches, if it does, both cards will go Invisible, make the correct sound and update
        // the score, if the Stage is empty ( 4 matches have been made ) it will create a new one

        ObjectAnimator hide_card1 = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_hide);
        hide_card1.setDuration(800);
        hide_card1.setTarget(card1);

        ObjectAnimator hide_card2 = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.memory_animation_hide);
        hide_card2.setDuration(800);
        hide_card2.setTarget(card2);

        hide_card1.start();
        hide_card2.start();
        if(cardPairs()) {
            memorygame.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    card1.setVisibility(View.INVISIBLE);
                    card2.setVisibility(View.INVISIBLE);
                    memorygame.score+=2;
                    memorygame.correct_sound();
                    memorygame.updateScore();
                    if(memorygame.pairs_found==4){
                        memorygame.stages_completed++;
                        memorygame.createNewStage();
                        memorygame.pairs_found=0;
                    }
                }
            });
        }
        hideImageAnimation(card1);
        hideImageAnimation(card2);

        //If the cards dosnt match they will be set as null, so a new card can be set
        setCard1(null);
        setCard2(null);
    }

    @Override
    public void run() {
        //this will run hide cards method

        try {
            sleep(1000);
            memorygame.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideCards();
                }
            });
            sleep(180);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cardPairs() {
        //We check if the card values matches

        if(card1_value==card2_value){
            return true;
        }
        return false;
    }

    private Drawable getImageValue(Integer value){
        //This method will set the image displayed on every stage

        Drawable img=null;
        if(memorygame.stages_completed%2==0){
            switch (value){
                case 0:
                    img=memorygame.getDrawable(R.drawable.blob_yellow);
                    break;
                case 1:
                    img=memorygame.getDrawable(R.drawable.blob_red);
                    break;
                case 2:
                    img=memorygame.getDrawable(R.drawable.blob_blue);
                    break;
                case 3:
                    img=memorygame.getDrawable(R.drawable.blob_purple);
                    break;
                case 4:
                    img=memorygame.getDrawable(R.drawable.blob_green);
                    break;
                default:
                    break;
            }
        }
        else {
            switch (value){
                case 0:
                    img=memorygame.getDrawable(R.drawable.tree_apple);
                    break;
                case 1:
                    img=memorygame.getDrawable(R.drawable.tree_pink);
                    break;
                case 2:
                    img=memorygame.getDrawable(R.drawable.tree_green);
                    break;
                case 3:
                    img=memorygame.getDrawable(R.drawable.tree_orange);
                    break;
                case 4:
                    img=memorygame.getDrawable(R.drawable.tree_cyan);
                    break;
                default:
                    break;
            }
        }

        return img;
    }

    private void showImageAnimation(View card, Integer card_value){
        //The card will show the image when the animation goes on half to make it more interesting
        // visually
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                memorygame.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Drawable img = getImageValue(card_value);
                        card.setForeground(img);
                    }
                });
                timer.cancel();
            }
        }, 0, 1000);
    }

    private void hideImageAnimation(View card){
        //The card will play the show animation in reverse to hide the card

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                memorygame.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        card.setForeground(null);
                    }
                });
                timer.cancel();
            }
        }, 0, 1000);
    }
}
