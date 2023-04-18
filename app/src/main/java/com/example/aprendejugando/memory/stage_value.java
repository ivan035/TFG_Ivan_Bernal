package com.example.aprendejugando.memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class stage_value {

    private Integer[] cards_id_list= new Integer[]{2131296786,2131296787,2131296788,2131296789,2131296790,2131296791,2131296792,2131296793,2131296794};
    public ArrayList<Card> cards;

    public stage_value() {
        cards=create_stage();
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
            int random_value=0;
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
                    random_value=0;
                    break;
                }
            };

            Card new_card=new Card(cards_id_list[i], random_value);
            cards.add(new_card);
        }


        return cards;
    }
}
