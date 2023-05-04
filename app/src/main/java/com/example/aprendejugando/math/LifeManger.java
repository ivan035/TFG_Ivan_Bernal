package com.example.aprendejugando.math;

import android.view.View;
import android.widget.ImageView;

public class LifeManger {
    private ImageView life_id;
    private Boolean life_lost;

    public LifeManger(ImageView life_id, Boolean life_lost) {
        this.life_id = life_id;
        this.life_lost = life_lost;
    }

    public ImageView getLife_id() {
        return life_id;
    }

    public void setLife_id(ImageView life_id) {
        this.life_id = life_id;
    }

    public Boolean getLife_lost() {
        return life_lost;
    }

    public void setLife_lost(Boolean life_lost) {
        this.life_lost = life_lost;
    }
}
