package karan.game.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by karan on 22/6/15.
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable {

    //Different thread

    AndroidGame game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {

        super(game);
        this.game = game;
        this.framebuffer = framebuffer;

        /*Generally speaking, anything having to do with a View will involve a SurfaceHolder. */
        this.holder = getHolder(); //Holder is needed to return canvas

    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();

    }


    //Different thread - renderThread
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();

        //Game loop
        while (running) {
            if (!holder.getSurface().isValid())
                continue;

            float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
            startTime = System.nanoTime();

            if (deltaTime > 3.15) {

                /*In our game from Units 2 and 3, we had a frame rate dependent movement. Although we fixed the frame rate at 60, if the game slowed down due to heavier load on our CPU and dropped the frame rate to 30, our character's movement speed would have been halved. This was fine for our computers, which could easily handle the simple applet without slowing down. However, Android devices are rarely powerful enough to maintain 60fps indefinitely. The CPU will be burdened with incoming messages, internal changes, and much more. That means that the frame rate will fluctuate.

                    To prevent movement speed from depending on frame rate, we use this deltaTime variable to check how much time elapsed since the last update. If the update took twice as long (i.e. frame rate was halved), then deltatime would be doubled. We multiply this deltaTime throughout our game's update methods to ensure that no matter what the frame rate is, our character will move by the same amount given the same time period.

                        Of course, this means that our speed could go from 1 pixel per second to 10 pixels per second. If we have a thin wall, this sudden increase in deltaTime could mean that our collision detection system will break. That is why we cap the deltaTime (it is capped at 3.15 in the above example) so that if the game slows down too much, then we will let movement depend on frame rate so that we do not break our entire game trying to maintain consistent movement. This is prioritization at work.
                * */
                deltaTime = (float) 3.15;
            }

            //Update and Paint Calls! DeltaTime is max permissed time elapsed

            if(game.getCurrentScreen()!=null) {
                game.getCurrentScreen().update(deltaTime);
                game.getCurrentScreen().paint(deltaTime);
            }


            /* If you want to draw a rectangle, you make a library call, and it sets bytes in a buffer appropriately.
            To ensure that a buffer isn't updated by two clients at once
            , or written to while being displayed, you have to lock
            the buffer to access it*/

            /* lockCanvas() locks the buffer and returns a Canvas to use for drawing,
            and unlockCanvasAndPost() unlocks the buffer and sends it to the compositor.*/
            Canvas canvas = holder.lockCanvas();

            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }

        }
    }

}
