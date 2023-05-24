package com.example.aprendejugando.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.MainMenu;
import com.example.aprendejugando.R;
import com.example.aprendejugando.blob.Blob_list;
import com.example.aprendejugando.blob.BlobTimer;

import java.util.ArrayList;

public class BlobGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView score_text;
    private TextView timer_text;
    private TextView blob_count_text;
    private TextView blackie_dialogue;
    private TextView game_end_text;
    private ImageView blackie_image;
    private ImageView background_image;
    private ImageView blob1;
    private ImageView blob2;
    private ImageView blob3;
    private ImageView blob4;
    private ImageView blob5;
    private ImageView blob6;
    private ImageView blob7;
    private ImageView blob8;
    private ImageView blob9;
    private ImageView blob10;
    private ImageView blob11;
    private ImageView blob12;
    private Button to_menu;
    private ProgressBar time_bar;

    //This array will contain a list of all blobs and if they are show
    private ArrayList<Blob_list> list_of_blobs = new ArrayList<Blob_list>();
    //The Timer will be the timer for the game
    private BlobTimer blobTimer;
    private final Integer GAME_TIME = 50;
    private Integer difficulty_level = 1;
    private Integer max_blob_cuantity;
    private Integer actual_blobs = 0;
    private Integer score = 0;
    private BlobGame this_game;
    private double starter_blob_add_ammount;
    //Media player will be needed to play music and sounds
    private MediaPlayer music;
    private MediaPlayer blob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blob_game);
        //We match the views needed with our variables
        blackie_dialogue = findViewById(R.id.blob_game_dog_dialogue);
        blackie_image = findViewById(R.id.blob_game_img_dog);
        difficulty_text = findViewById(R.id.blob_difficulty_text);

        score_text=findViewById(R.id.blob_game_score);
        String default_score_text = getString(R.string.global_score);
        score_text.setText(String.format(default_score_text,0));

        timer_text = findViewById(R.id.blob_game_timer_text);
        time_bar=findViewById(R.id.blob_game_time_bar);
        blob_count_text = findViewById(R.id.blob_game_blob_counter);
        game_end_text = findViewById(R.id.blob_game_end_text);
        to_menu=findViewById(R.id.blob_game_tomenu_button);
        background_image = findViewById(R.id.blob_game_img_bg);

        //Depending on the difficulty chosen we set the limit of the shown blobs and the
        // starter add_time amount to spawn the blobs ( blob.Timer >> time_to_add_new_blob )
        String default_mode_text = getString(R.string.global_mode);
        Intent intent = getIntent();
        if (intent != null) {
            difficulty_level = intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED, 0);
            max_blob_cuantity = 12;
            starter_blob_add_ammount = 0.8;
        }
        if (difficulty_level == 2) {
            String default_level_text = getString(R.string.difficulty_normal);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
            max_blob_cuantity = 8;
            starter_blob_add_ammount = 1.4;
        } else if (difficulty_level == 3) {
            String default_level_text = getString(R.string.difficulty_hard);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
            max_blob_cuantity = 5;
            starter_blob_add_ammount = 2.5;
        } else {
            String default_level_text = getString(R.string.difficulty_easy);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
            max_blob_cuantity = 12;
            starter_blob_add_ammount = 0.5;
        }
        initalize_blobs();

        //Display the actual score to the user and starts the background image animation
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        background_image.startAnimation(animation);
        time_bar.setMax(GAME_TIME);
        time_bar.setProgress(GAME_TIME);
        game_music();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(music!=null){
            music.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainMenu.global_music){
            if(music==null){
                game_music();
            }
            else{
                music.release();
                game_music();
            }
        }
    }

    private void initalize_blobs() {
        //it matches the views with the blobs and makes them all invisible
        blob1 = findViewById(R.id.blob_game_blob1);
        blob2 = findViewById(R.id.blob_game_blob2);
        blob3 = findViewById(R.id.blob_game_blob3);
        blob4 = findViewById(R.id.blob_game_blob4);
        blob5 = findViewById(R.id.blob_game_blob5);
        blob6 = findViewById(R.id.blob_game_blob6);
        blob7 = findViewById(R.id.blob_game_blob7);
        blob8 = findViewById(R.id.blob_game_blob8);
        blob9 = findViewById(R.id.blob_game_blob9);
        blob10 = findViewById(R.id.blob_game_blob10);
        blob11 = findViewById(R.id.blob_game_blob11);
        blob12 = findViewById(R.id.blob_game_blob12);
        hide_all_blobs();
        for (int i = 0; i < max_blob_cuantity; i++) {
            Blob_list blob = new Blob_list(i);
            list_of_blobs.add(blob);
        }
    }

    private void hide_all_blobs() {
        //It makes all blobs invisible
        blob1.setVisibility(View.INVISIBLE);
        blob2.setVisibility(View.INVISIBLE);
        blob3.setVisibility(View.INVISIBLE);
        blob4.setVisibility(View.INVISIBLE);
        blob5.setVisibility(View.INVISIBLE);
        blob6.setVisibility(View.INVISIBLE);
        blob7.setVisibility(View.INVISIBLE);
        blob8.setVisibility(View.INVISIBLE);
        blob9.setVisibility(View.INVISIBLE);
        blob10.setVisibility(View.INVISIBLE);
        blob11.setVisibility(View.INVISIBLE);
        blob12.setVisibility(View.INVISIBLE);
    }

    public void add_new_blob() {
        //Depending on the difficulty we take from the blob list the desired amount
        // EASY = Position 0 to 11 in the array
        // NORMAL = Position 0 to 7 in the array
        // EXTREME = Position 0 to 4 in the array

        //In a loop we retrieve numbers which will be the array position and check if its shown
        // until we find one that is not shown, then we will make that blob visible

        if (difficulty_level == 3) {
            Integer blob;
            do {
                blob = (int) (Math.random() * 5 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        }
        else if (difficulty_level == 2) {
            Integer blob;
            do {
                blob = (int) (Math.random() * 8 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        }
        else {
            Integer blob;
            do {
                blob = (int) (Math.random() * 12 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        }
    }

    private void make_blob_visible(int blob_id) {
        //It will give the desired blob a random position and make it visible.
        //Will also be set as show in the blob list
        //Since this will be executed from the Timer Thread it needs to be a runnableUI Thread

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (blob_id) {
                    case 1:
                        blob1.setTranslationX((int) (Math.random() * 700 + 1));
                        blob1.setTranslationY((int) (Math.random() * 950 + 350));
                        blob1.setVisibility(View.VISIBLE);
                        View view1 = findViewById(R.id.blob_game_blob1);
                        view1.setAlpha(1);
                        list_of_blobs.get(0).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 2:
                        blob2.setTranslationX((int) (Math.random() * 700 + 1));
                        blob2.setTranslationY((int) (Math.random() * 950 + 350));
                        blob2.setVisibility(View.VISIBLE);
                        View view2 = findViewById(R.id.blob_game_blob2);
                        view2.setAlpha(1);
                        list_of_blobs.get(1).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 3:
                        blob3.setTranslationX((int) (Math.random() * 700 + 1));
                        blob3.setTranslationY((int) (Math.random() * 950 + 350));
                        blob3.setVisibility(View.VISIBLE);
                        View view3 = findViewById(R.id.blob_game_blob3);
                        view3.setAlpha(1);
                        list_of_blobs.get(2).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 4:
                        blob4.setTranslationX((int) (Math.random() * 700 + 1));
                        blob4.setTranslationY((int) (Math.random() * 950 + 350));
                        blob4.setVisibility(View.VISIBLE);
                        View view4 = findViewById(R.id.blob_game_blob4);
                        view4.setAlpha(1);
                        list_of_blobs.get(3).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 5:
                        blob5.setTranslationX((int) (Math.random() * 700 + 1));
                        blob5.setTranslationY((int) (Math.random() * 950 + 350));
                        blob5.setVisibility(View.VISIBLE);
                        View view5 = findViewById(R.id.blob_game_blob5);
                        view5.setAlpha(1);
                        list_of_blobs.get(4).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 6:
                        blob6.setTranslationX((int) (Math.random() * 700 + 1));
                        blob6.setTranslationY((int) (Math.random() * 950 + 350));
                        blob6.setVisibility(View.VISIBLE);
                        View view6 = findViewById(R.id.blob_game_blob6);
                        view6.setAlpha(1);
                        list_of_blobs.get(5).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 7:
                        blob7.setTranslationX((int) (Math.random() * 700 + 1));
                        blob7.setTranslationY((int) (Math.random() * 950 + 350));
                        blob7.setVisibility(View.VISIBLE);
                        View view7 = findViewById(R.id.blob_game_blob7);
                        view7.setAlpha(1);
                        list_of_blobs.get(6).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 8:
                        blob8.setTranslationX((int) (Math.random() * 700 + 1));
                        blob8.setTranslationY((int) (Math.random() * 950 + 350));
                        blob8.setVisibility(View.VISIBLE);
                        View view8 = findViewById(R.id.blob_game_blob8);
                        view8.setAlpha(1);
                        list_of_blobs.get(7).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 9:
                        blob9.setTranslationX((int) (Math.random() * 700 + 1));
                        blob9.setTranslationY((int) (Math.random() * 950 + 350));
                        blob9.setVisibility(View.VISIBLE);
                        View view9 = findViewById(R.id.blob_game_blob9);
                        view9.setAlpha(1);
                        list_of_blobs.get(8).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 10:
                        blob10.setTranslationX((int) (Math.random() * 700 + 1));
                        blob10.setTranslationY((int) (Math.random() * 950 + 350));
                        blob10.setVisibility(View.VISIBLE);
                        View view10 = findViewById(R.id.blob_game_blob10);
                        view10.setAlpha(1);
                        list_of_blobs.get(9).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 11:
                        blob11.setTranslationX((int) (Math.random() * 700 + 1));
                        blob11.setTranslationY((int) (Math.random() * 950 + 350));
                        blob11.setVisibility(View.VISIBLE);
                        View view11 = findViewById(R.id.blob_game_blob11);
                        view11.setAlpha(1);
                        list_of_blobs.get(10).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 12:
                        blob12.setTranslationX((int) (Math.random() * 700 + 1));
                        blob12.setTranslationY((int) (Math.random() * 950 + 350));
                        blob12.setVisibility(View.VISIBLE);
                        View view12 = findViewById(R.id.blob_game_blob12);
                        view12.setAlpha(1);
                        list_of_blobs.get(11).setAdded_already(true);
                        actual_blobs++;
                        break;
                    default:
                        break;
                }

                //It shows the user how many blobs are actually.
                //If the showed blobs reaches the max ( set by difficulty ) will end the game
                blob_count_text.setText(actual_blobs + "/" + max_blob_cuantity);
                if (actual_blobs == max_blob_cuantity) {
                    finish_game();
                }
            }
        });
    }

    private void blackie_action_lose() {
        //When the game ends, it changes dog image and text
        String blackie_game_over_text = getResources().getString(R.string.blackie_lose_default_text);
        blackie_dialogue.setText(String.format(blackie_game_over_text, score));
        blackie_image.setImageResource(R.drawable.blackie_sleep);
    }

    public void clean(View view) {
        //When a blob get clicked it will get their view ID name
        String view_id = getResources().getResourceEntryName(view.getId());

        //If the timer is not over it will make the blob opacity lower
        //If the opacity is lower than 0.2 it will add a point an set the score text so player sees it
        // then it compare the ID name with the blobs in the list until it find their position to set
        // show to false
        if (blobTimer.getTime() > 0) {
            float alpha = view.getAlpha();
            alpha = (float) (alpha - 0.3);
            view.setAlpha(alpha);
            alpha = view.getAlpha();
            if (alpha <= 0.2) {
                blob_sound();
                score++;
                String default_score_text = getString(R.string.global_score);
                score_text.setText(String.format(default_score_text,score));
                view.setVisibility(View.INVISIBLE);
                if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob1.getId()))) {
                    list_of_blobs.get(0).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob2.getId()))) {
                    list_of_blobs.get(1).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob3.getId()))) {
                    list_of_blobs.get(2).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob4.getId()))) {
                    list_of_blobs.get(3).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob5.getId()))) {
                    list_of_blobs.get(4).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob6.getId()))) {
                    list_of_blobs.get(5).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob7.getId()))) {
                    list_of_blobs.get(6).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob8.getId()))) {
                    list_of_blobs.get(7).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob9.getId()))) {
                    list_of_blobs.get(8).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob10.getId()))) {
                    list_of_blobs.get(9).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob11.getId()))) {
                    list_of_blobs.get(10).setAdded_already(false);
                } else if (view_id.equalsIgnoreCase(getResources().getResourceEntryName(blob12.getId()))) {
                    list_of_blobs.get(11).setAdded_already(false);
                }

                //It decrease the actual blobs number by 1 and updates the count text
                actual_blobs--;
                blob_count_text.setText(actual_blobs + "/" + max_blob_cuantity);

                //Trigger a dog action
                blackie_action();
            }
        }
    }

    private void blackie_action() {
        //Whenever you clean a blob it will throw a random number to change the text and
        //image of the dog
        //Action has a larger range that it should, so it will change sometimes you clean a blob
        // and not always
        int action = (int) (Math.random() * 7 + 1);
        if (action == 1) {
            String text = getResources().getString(R.string.blob_game_dog_action1);
            blackie_dialogue.setText(text);
            blackie_image.setImageResource(R.drawable.blackie_impressed);
        }
        if (action == 2) {
            String text = getResources().getString(R.string.blob_game_dog_action2);
            blackie_dialogue.setText(text);
            blackie_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 3) {
            String text = getResources().getString(R.string.blob_game_dog_action3);
            blackie_dialogue.setText(text);
            blackie_image.setImageResource(R.drawable.blackie_front);
        }
        if (action == 4) {
            String text = getResources().getString(R.string.blob_game_dog_action4);
            blackie_dialogue.setText(text);
            blackie_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 5) {
            String text = getResources().getString(R.string.blob_game_dog_action5);
            blackie_dialogue.setText(String.format(text,score));
            blackie_image.setImageResource(R.drawable.blackie_howl);
        }
    }

    public void set_timer_text(Integer time) {
        //It sets the timer text, this will be used for the Timer thread, so it will need a
        // runnableUI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer_text.setText(String.valueOf(time));
                time_bar.setProgress(time);
            }
        });

    }

    public void finish_game() {
        //Interrupts the timer Thread and makes visible game over texts and button to main menu
        synchronized (blobTimer){
            blobTimer.getService().interrupt();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                blackie_action_lose();
                hide_all_blobs();
                String game_over = getResources().getString(R.string.global_game_over);
                game_end_text.setText(String.format(game_over,score));
                to_menu.setVisibility(View.VISIBLE);
                game_end_text.setVisibility(View.VISIBLE);
            }
        });
    }

    public void start_game(View view) {
        //Start the Timer and makes "tap screen" invisible
        this_game = this;
        blobTimer = new BlobTimer(GAME_TIME, this_game, starter_blob_add_ammount, difficulty_level);
        blobTimer.getService().start();
        view.setVisibility(View.INVISIBLE);
    }


    public void finish_game(View view) {
        //Ends this activity
        if(music!=null){
            music.release();
        }
        finish();
    }

    public void game_music(){
        if(MainMenu.global_music){
            music = MediaPlayer.create(this, R.raw.blob_game_music);
            music.setLooping(true);
            music.start();
        }
    }

    public void blob_sound(){
        if(blob==null){
            blob = MediaPlayer.create(this, R.raw.blob_sound);
            blob.setVolume(0.5f,0.5f);
            blob.start();
        }
        else{
            blob.release();
            blob = MediaPlayer.create(this, R.raw.blob_sound);
            blob.setVolume(0.5f,0.5f);
            blob.start();
        }
    }

    @Override
    public void onBackPressed() {
        //If the user press the "back" button in the mobile, it will stop the music and finish the
        // activity
        super.onBackPressed();
        if(music!=null){
            music.release();
        }
        finish();
    }
}