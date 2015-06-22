package com.first.androidGame;

import karan.game.implementation.AndroidGame;
import personal.framework.Screen;

/**
 * Created by karan on 22/6/15.
 */
public class SampleGame extends AndroidGame {

    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

    

}
