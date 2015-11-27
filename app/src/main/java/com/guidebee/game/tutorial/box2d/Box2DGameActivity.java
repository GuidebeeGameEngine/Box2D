package com.guidebee.game.tutorial.box2d;

import android.os.Bundle;

import com.guidebee.game.Configuration;
import com.guidebee.game.activity.GameActivity;


public class Box2DGameActivity extends GameActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = new Configuration();

        config.useAccelerometer = false;
        config.useCompass = false;

        initialize(new Box2DGamePlay(), config);
    }
}