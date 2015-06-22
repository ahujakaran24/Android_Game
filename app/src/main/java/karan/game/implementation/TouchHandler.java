package karan.game.implementation;

import android.view.View;

import java.util.List;

import personal.framework.Input;

/**
 * Created by karan on 22/6/15.
 */
public interface TouchHandler extends View.OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}