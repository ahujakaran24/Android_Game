package com.first.androidGame;

import personal.framework.Game;
import personal.framework.Graphics;
import personal.framework.Screen;

/**
 * Created by karan on 22/6/15.
 */
public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {

        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.jpg", Graphics.ImageFormat.RGB565); //best format -fastest
        Assets.click = game.getAudio().createSound("explode.ogg");


        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }

    //Initialize assets


}
