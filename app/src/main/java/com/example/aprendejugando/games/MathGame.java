package com.example.aprendejugando.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aprendejugando.MainMenu;
import com.example.aprendejugando.R;
import com.example.aprendejugando.DifficultySelection;
import com.example.aprendejugando.math.LifeManger;
import com.example.aprendejugando.math.MathTimer;
import com.example.aprendejugando.math.Question;

import java.util.ArrayList;


public class MathGame extends AppCompatActivity {

    private TextView difficulty_text;
    private TextView time_text;
    private TextView question_text;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;
    private TextView score_text;
    private TextView dog_dialogue;
    private TextView game_end_text;
    private ImageView life_1;
    private ImageView life_2;
    private ImageView life_3;
    private ImageView life_4;
    private ImageView dog_image;
    private Button back_to_menu_button;
    private ArrayList<TextView> answer_positions= new ArrayList<>();
    private ArrayList<LifeManger> lifes = new ArrayList<>();
    private Integer lifes_lost=0;
    private ProgressBar time_bar;

    private Integer difficulty_level=0;
    private Integer score=0;

    private MathTimer timer;
    private Question actual_question=new Question(null,null);

    private MediaPlayer music;
    private MediaPlayer correct;
    private MediaPlayer wrong;

    private Integer GAME_TIME=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_game);
        difficulty_text = findViewById(R.id.math_difficulty_text);

        time_text = findViewById(R.id.math_game_time_text);
        time_bar = findViewById(R.id.math_game_time_bar);

        question_text = findViewById(R.id.math_game_question_text);

        String default_score_text = getString(R.string.global_score);
        score_text = findViewById(R.id.math_game_score);
        score_text.setText(String.format(default_score_text,score));

        answer1 = findViewById(R.id.math_game_answer_1);
        answer2 = findViewById(R.id.math_game_answer_2);
        answer3 = findViewById(R.id.math_game_answer_3);
        answer4 = findViewById(R.id.math_game_answer_4);

        dog_dialogue = findViewById(R.id.math_game_dog_dialogue);
        dog_image = findViewById(R.id.math_game_img_dog);

        game_end_text = findViewById(R.id.math_game_end_text);
        back_to_menu_button = findViewById(R.id.math_game_tomenu_button);
        life_1 = findViewById(R.id.math_game_life_1);
        life_2 = findViewById(R.id.math_game_life_2);
        life_3 = findViewById(R.id.math_game_life_3);
        life_4 = findViewById(R.id.math_game_life_4);

        lifes.add(new LifeManger(life_1,false));
        lifes.add(new LifeManger(life_2,false));
        lifes.add(new LifeManger(life_3,false));
        lifes.add(new LifeManger(life_4,false));

        String default_mode_text = getString(R.string.global_mode);
        Intent intent = getIntent();
        if(intent!=null){
            difficulty_level = intent.getIntExtra(DifficultySelection.DIFFICULTY_SELECTED,0);
        }
        if(difficulty_level == 2){
            String default_level_text = getString(R.string.difficulty_normal);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
        }
        else if(difficulty_level == 3){
            String default_level_text = getString(R.string.difficulty_hard);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
            lifes.get(3).setLife_lost(true);
        }
        else{
            String default_level_text = getString(R.string.difficulty_easy);
            difficulty_text.setText(default_mode_text + " " + default_level_text);
        }
        timer = new MathTimer(GAME_TIME,this);
        time_bar.setMax(GAME_TIME);
        time_bar.setProgress(GAME_TIME);

        answer_positions.add(answer1);
        answer_positions.add(answer2);
        answer_positions.add(answer3);
        answer_positions.add(answer4);

        game_music();
    }

    public void updateTimer(int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                time_bar.setProgress(time);
                time_text.setText(String.valueOf(time));
            }
        });
    }


    public void start_game(View view) {
        timer.getService().start();
        view.setVisibility(View.INVISIBLE);
        nextQuestion();
    }

    public void finishGame() {
        timer.setTime(0);
        timer.getService().interrupt();
        String game_over = getResources().getString(R.string.global_game_over);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                game_end_text.setText(String.format(game_over,score));
                game_end_text.setVisibility(View.VISIBLE);
                back_to_menu_button.setVisibility(View.VISIBLE);
                blackie_action_lose();
            }
        });
    }
    private void nextQuestion() {
        updateLifes();
        String default_text = getResources().getString(R.string.math_game_question_text);
        Question question = generateQuestion(difficulty_level);
        actual_question=question;
        question_text.setText(String.format(default_text,actual_question.getText()));
    }

    private void updateLifes(){
        for (LifeManger life :lifes) {
            if(life.getLife_lost()){
                life.getLife_id().setVisibility(View.INVISIBLE);
                lifes_lost++;
            }
            else{
                life.getLife_id().setVisibility(View.VISIBLE);
            }
        }
        if(lifes_lost == 4){
            finishGame();
        }
        else{
            lifes_lost = 0;
        }
    }


    private Question generateQuestion(Integer difficulty){

        Integer number1;
        Integer number2;
        Integer answer;
        String type;

        if(difficulty==1){
            number1 = (int) (Math.random()*25);
            number2 = (int) (Math.random()*25);
            type=" + ";
            answer = number1+number2;
        }
        else{
            number1 = (int) (Math.random()*50);
            number2 = (int) (Math.random()*49);
            Integer randomType = (int) (Math.random()*2);
            if(randomType == 1){
                type = " - ";
                do{
                    number1 = (int) (Math.random()*50);
                }while(number2 > number1);
                answer = number1 - number2;
            }
            else{
                type = " + ";
                answer = number1+number2;
            }
        }


        String question_value = (number1 + type + number2);
        Question question = new Question(question_value,String.valueOf(answer));

        Integer correct_answer_position = (int) (Math.random()*4);
        answer_positions.get(correct_answer_position).setText(question.getAnswer());
        answer_positions.get(correct_answer_position).setTextColor(getColor(R.color.selected));
        fill_random_answers(correct_answer_position,Integer.valueOf(answer));

        return question;
    }



    private void fill_random_answers(Integer correct_answer_position, Integer correct_answer){
        Integer first_number = 0;
        Integer second_number = 0;

        for (int i = 0; i < answer_positions.size(); i++) {
            if(correct_answer_position != i){
                Integer random_number;
                if(difficulty_level == 1){
                    do {
                        random_number = (int) (Math.random()*50);
                    }while( (random_number==correct_answer) || (random_number == first_number) ||
                            (random_number == second_number) );
                }
                else{
                    do {
                        random_number = (int) (Math.random()*99);
                    }while( (random_number == correct_answer) || (random_number == first_number) ||
                            (random_number == second_number) );
                }

                answer_positions.get(i).setText(String.valueOf(random_number));
                answer_positions.get(i).setTextColor(getColor(R.color.black)); //SOLO PARA TESTEAR
                if(first_number!=0){
                    second_number=random_number;
                }
                else{
                    first_number = random_number;
                }
            }
        }
    }

    public void check_answer(View answer_card) {
        if(timer.getTime()>0){
            TextView answer_clicked = (TextView) answer_card;
            if(answer_clicked.getText()==actual_question.getAnswer()){
                correct_sound();
                score++;
                updateScore();
                nextQuestion();
            }
            else{
                Integer heart_position = 0;
                for (LifeManger life:lifes) {
                    if(!life.getLife_lost()){
                        heart_position++;
                    }
                }
                blackie_bad_action();
                wrong_sound();
                lifes.get(heart_position - 1).setLife_lost(true);
                nextQuestion();
            }
        }
    }

    private void updateScore() {
        String score_default_text = getResources().getString(R.string.global_score);
        blackie_good_action();
        score_text.setText(String.format(score_default_text,score));
    }

    private void blackie_good_action() {
        //Whenever you match a pair it will throw a random number to change the text and
        //image of the dog
        //Action has a larger range that it should, so it will change sometimes you clean a blob
        // and not always
        int action = (int) (Math.random() * 7 + 1);
        if (action == 1) {
            dog_dialogue.setTextSize(20f);
            String text = getResources().getString(R.string.math_game_dog_action1);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_impressed);
        }
        if (action == 2) {
            dog_dialogue.setTextSize(20f);
            String text = getResources().getString(R.string.math_game_dog_action2);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 3) {
            dog_dialogue.setTextSize(20f);
            String text = getResources().getString(R.string.math_game_dog_action3);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_front);
        }
        if (action == 4) {
            dog_dialogue.setTextSize(20f);
            String text = getResources().getString(R.string.math_game_dog_action4);
            dog_dialogue.setText(text);
            dog_image.setImageResource(R.drawable.blackie_happy);
        }
        if (action == 5) {
            dog_dialogue.setTextSize(20f);
            String text = getResources().getString(R.string.math_game_dog_action5);
            dog_dialogue.setText(String.format(text,score));
            dog_image.setImageResource(R.drawable.blackie_howl);
        }
    }
    private void blackie_bad_action() {
        //When the game ends, it changes dog image and text
        dog_dialogue.setTextSize(20f);
        String text = getResources().getString(R.string.math_game_dog_bad_action);
        dog_dialogue.setText(text);
        dog_image.setImageResource(R.drawable.blackie_sad);
    }

    private void blackie_action_lose() {
        //When the game ends, it changes dog image and text
        String blackie_game_over_text = getResources().getString(R.string.blackie_lose_default_text);
        dog_dialogue.setTextSize(16f);
        dog_dialogue.setText(String.format(blackie_game_over_text, score));
        dog_image.setImageResource(R.drawable.blackie_sleep);
    }

    public void game_music(){
        if(MainMenu.global_music){
            music = MediaPlayer.create(this, R.raw.math_game_music);
            music.setLooping(true);
            music.start();
        }
    }

    public void correct_sound(){
        if(correct==null){
            correct = MediaPlayer.create(this, R.raw.math_correct_answer);
            correct.setVolume(0.3f,03f);
            correct.start();
        }
        else{
            correct.release();
            correct = MediaPlayer.create(this, R.raw.math_correct_answer);
            correct.setVolume(0.3f,03f);
            correct.start();
        }
    }

    public void wrong_sound(){
        if(wrong==null){
            wrong = MediaPlayer.create(this, R.raw.math_wrong_answer);
            wrong.setVolume(0.3f,03f);
            wrong.start();
        }
        else{
            wrong.release();
            wrong = MediaPlayer.create(this, R.raw.math_wrong_answer);
            wrong.setVolume(0.3f,03f);
            wrong.start();
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

    public void finish_game(View view){
        if(music!=null){
            music.release();
        }
        finish();
    }
}