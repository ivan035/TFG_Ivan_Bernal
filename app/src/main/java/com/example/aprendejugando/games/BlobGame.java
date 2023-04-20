package com.example.aprendejugando.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.R;
import com.example.aprendejugando.sauce.Blob_list;
import com.example.aprendejugando.sauce.Timer;

import java.util.ArrayList;

public class BlobGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView score_text;
    private TextView timer_text;
    private TextView blob_count_text;
    private TextView dog_dialogue;
    private TextView game_end_score;
    private TextView game_end_text;
    private ImageView dog_image;
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
    private ArrayList<Blob_list> list_of_blobs = new ArrayList<Blob_list>();
    private Timer timer;
    private Integer difficulty_level = 1;
    private Integer max_blob_cuantity;
    private Integer actual_blobs = 0;
    private Integer score = 0;
    private BlobGame this_game;
    private final Integer GAME_TIME = 55;
    private double starter_blob_add_ammount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blob_game);
        dog_dialogue = findViewById(R.id.blob_game_dog_dialogue);
        dog_image = findViewById(R.id.blob_game_img_dog);
        difficulty_text = findViewById(R.id.blob_difficulty_text);
        score_text = findViewById(R.id.blob_game_score);
        timer_text = findViewById(R.id.blob_game_timer_text);
        blob_count_text = findViewById(R.id.blob_game_blob_counter);
        game_end_score = findViewById(R.id.blob_game_end_score);
        game_end_text = findViewById(R.id.blob_game_end_text);
        to_menu=findViewById(R.id.blob_game_tomenu_button);
        background_image = findViewById(R.id.blob_game_img_bg);
        Intent intent = getIntent();
        if (intent != null) {
            difficulty_level = intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED, 0);
            max_blob_cuantity = 12;
            starter_blob_add_ammount = 0.8;
        }
        if (difficulty_level == 2) {
            difficulty_text.setText("Dificultad: Normal");
            max_blob_cuantity = 8;
            starter_blob_add_ammount = 1.4;
        } else if (difficulty_level == 3) {
            difficulty_text.setText("Dificultad: Experto");
            max_blob_cuantity = 5;
            starter_blob_add_ammount = 2.5;
        } else {
            difficulty_text.setText("Dificultad: Facil");
            max_blob_cuantity = 12;
            starter_blob_add_ammount = 0.5;
        }
        initalizeblobs();
        score_text.setText("Puntuacion: " + score);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        background_image.startAnimation(animation);
    }

    private void initalizeblobs() {
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
        if (difficulty_level == 3) {
            Integer blob;
            do {
                blob = (int) (Math.random() * 5 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        } else if (difficulty_level == 2) {
            Integer blob;
            do {
                blob = (int) (Math.random() * 8 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        } else {
            Integer blob;
            do {
                blob = (int) (Math.random() * 12 + 1);
            } while (list_of_blobs.get(blob - 1).isAdded_already());
            make_blob_visible(blob);
        }
    }

    private void make_blob_visible(int blob_id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (blob_id) {
                    case 1:
                        blob1.setTranslationX((int) (Math.random() * 700 + 1));
                        blob1.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob1.setVisibility(View.VISIBLE);
                        View view1 = findViewById(R.id.blob_game_blob1);
                        view1.setAlpha(1);
                        list_of_blobs.get(0).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 2:

                        blob2.setTranslationX((int) (Math.random() * 700 + 1));
                        blob2.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob2.setVisibility(View.VISIBLE);
                        View view2 = findViewById(R.id.blob_game_blob2);
                        view2.setAlpha(1);
                        list_of_blobs.get(1).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 3:

                        blob3.setTranslationX((int) (Math.random() * 700 + 1));
                        blob3.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob3.setVisibility(View.VISIBLE);
                        View view3 = findViewById(R.id.blob_game_blob3);
                        view3.setAlpha(1);
                        list_of_blobs.get(2).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 4:

                        blob4.setTranslationX((int) (Math.random() * 700 + 1));
                        blob4.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob4.setVisibility(View.VISIBLE);
                        View view4 = findViewById(R.id.blob_game_blob4);
                        view4.setAlpha(1);
                        list_of_blobs.get(3).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 5:

                        blob5.setTranslationX((int) (Math.random() * 700 + 1));
                        blob5.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob5.setVisibility(View.VISIBLE);
                        View view5 = findViewById(R.id.blob_game_blob5);
                        view5.setAlpha(1);
                        list_of_blobs.get(4).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 6:
                        blob6.setTranslationX((int) (Math.random() * 700 + 1));
                        blob6.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob6.setVisibility(View.VISIBLE);
                        View view6 = findViewById(R.id.blob_game_blob6);
                        view6.setAlpha(1);
                        list_of_blobs.get(5).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 7:
                        blob7.setTranslationX((int) (Math.random() * 700 + 1));
                        blob7.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob7.setVisibility(View.VISIBLE);
                        View view7 = findViewById(R.id.blob_game_blob7);
                        view7.setAlpha(1);
                        list_of_blobs.get(6).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 8:
                        blob8.setTranslationX((int) (Math.random() * 700 + 1));
                        blob8.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob8.setVisibility(View.VISIBLE);
                        View view8 = findViewById(R.id.blob_game_blob8);
                        view8.setAlpha(1);
                        list_of_blobs.get(7).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 9:
                        blob9.setTranslationX((int) (Math.random() * 700 + 1));
                        blob9.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob9.setVisibility(View.VISIBLE);
                        View view9 = findViewById(R.id.blob_game_blob9);
                        view9.setAlpha(1);
                        list_of_blobs.get(8).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 10:
                        blob10.setTranslationX((int) (Math.random() * 700 + 1));
                        blob10.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob10.setVisibility(View.VISIBLE);
                        View view10 = findViewById(R.id.blob_game_blob10);
                        view10.setAlpha(1);
                        list_of_blobs.get(9).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 11:
                        blob11.setTranslationX((int) (Math.random() * 700 + 1));
                        blob11.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob11.setVisibility(View.VISIBLE);
                        View view11 = findViewById(R.id.blob_game_blob11);
                        view11.setAlpha(1);
                        list_of_blobs.get(10).setAdded_already(true);
                        actual_blobs++;
                        break;
                    case 12:
                        blob12.setTranslationX((int) (Math.random() * 700 + 1));
                        blob12.setTranslationY((int) (Math.random() * 1000 + 80));
                        blob12.setVisibility(View.VISIBLE);
                        View view12 = findViewById(R.id.blob_game_blob12);
                        view12.setAlpha(1);
                        list_of_blobs.get(11).setAdded_already(true);
                        actual_blobs++;
                        break;
                    default:
                        break;
                }
                blob_count_text.setText(actual_blobs + "/" + max_blob_cuantity);
                if (actual_blobs == max_blob_cuantity) {
                    finish_game();
                }
            }
        });
    }

    private void blackie_action_lose() {
        dog_dialogue.setText("!Oh no!, se acabo la partida, no te procupes, has conseguido " + score + " puntos, lo hiciste muy bien");
        dog_image.setImageResource(R.drawable.blackie_sleep);
    }

    public void clean(View view) {
        String view_id = getResources().getResourceEntryName(view.getId());
        if (timer.getTime() > 0) {
            float alpha = view.getAlpha();
            alpha = (float) (alpha - 0.2);
            view.setAlpha(alpha);
            alpha = view.getAlpha();
            if (alpha <= 0.2) {
                score++;
                score_text.setText("Puntuacion: " + score);
                view.setVisibility(View.INVISIBLE);
                view.setTranslationX((int) (Math.random() * 700 + 1));
                view.setTranslationY((int) (Math.random() * 1000 + 30));
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

                actual_blobs--;
                blob_count_text.setText(actual_blobs + "/" + max_blob_cuantity);
                blackie_action();
            }
        }
    }

    private void blackie_action() {
        int action = (int) (Math.random() * 7 + 1);
        if (action == 1) {
            dog_dialogue.setText("!! Bien hecho !!");
            dog_image.setImageResource(R.drawable.blackie_impressed);
        }
        if (action == 2) {
            dog_dialogue.setText("Se te da muy bien");
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 3) {
            dog_dialogue.setText("Lo tienes todo controlado");
            dog_image.setImageResource(R.drawable.blackie_front);
        }
        if (action == 4) {
            dog_dialogue.setText("! Animo, sigue asi !");
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 5) {
            dog_dialogue.setText("Wow, ya tienes " + score + " puntos");
            dog_image.setImageResource(R.drawable.blackie_howl);
        }
    }

    public void set_timer_text(Integer time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer_text.setText("Tiempo restante: " + time);
            }
        });
    }

    public void finish_game() {
        timer.getService().interrupt();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.finish_game();
                blackie_action_lose();
                hide_all_blobs();
                to_menu.setVisibility(View.VISIBLE);
                game_end_score.setText("PUNTUACION FINAL: " + score + " PUNTOS");
                game_end_score.setVisibility(View.VISIBLE);
                game_end_text.setVisibility(View.VISIBLE);
            }
        });
    }

    public void start_game(View view) {
        this_game = this;
        timer = new Timer(GAME_TIME, this_game, starter_blob_add_ammount, difficulty_level);
        timer.getService().start();
        view.setVisibility(View.INVISIBLE);
    }


    public void finish_game(View view) {
        finish();
    }
}