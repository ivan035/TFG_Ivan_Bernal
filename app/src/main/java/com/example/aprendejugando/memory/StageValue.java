package com.example.aprendejugando.memory;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StageValue {

    private ArrayList<ImageView> cards_list;
    private Integer[] cards_id_list= new Integer[9];
    public ArrayList<Card> cards;

    public StageValue(ArrayList<ImageView> cards_view) {
        this.cards_list=cards_view;
        makeidlist();
        cards=create_stage();
    }

    private void makeidlist() {
        Integer i=0;
        for (ImageView card: cards_list) {
            cards_id_list[i]=card.getId();
            i++;
        }
    }

    public ArrayList<Card> create_stage(){
        ArrayList<Card> cards=new ArrayList<>();

        List<Integer> intList = Arrays.asList(cards_id_list);
        Collections.shuffle(intList);
        intList.toArray(cards_id_list);

        int one=2;
        int two=2;
        int three=2;
        int four=2;
        for (int i = 0; i < cards_id_list.length; i++) {
            int random_value;
            if(cards_id_list[i]==null){
                Card new_card=new Card(cards_id_list[i], 0);
                cards.add(new_card);
            }
            else{
                while(true){
                    random_value=(int)(Math.random()*4+1);
                    if(random_value==1 && one!=0){
                        one=one-1;
                        break;
                    }
                    else if(random_value==2 && two!=0){
                        two=two-1;
                        break;
                    }
                    else if(random_value==3 && three!=0){
                        three=three-1;
                        break;
                    }
                    else if(random_value==4 && four!=0){
                        four=four-1;
                        break;
                    }
                    else if((random_value==1||random_value==2||random_value==3||random_value==4) && (one==0 && two==0 && three==0 && four==0)){
                        break;
                    }
                }
                Card new_card=new Card(cards_id_list[i], random_value);
                cards.add(new_card);
            }



        }


        return cards;
    }
}
