package personal.framework;

/**
 * Created by karan on 22/6/15.
 */
public interface Image {
    public int getWidth();
    public int getHeight();
    public Graphics.ImageFormat getFormat();
    public void dispose();
}