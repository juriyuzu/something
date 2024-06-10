package utilities;

import java.awt.*;

public class Object {
    public Image image;
    public int x;
    public int y;
    public int w;
    public int h;
    protected int priorityZ;
    public float opacity;

    /**
     * A basic Graphics 2D instance that holds an Image, coordinates, and size.
     * Can be drawn using the public draw function.
     */
    Object(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = this.image.getWidth(null);
        this.h = this.image.getHeight(null);
        opacity = 1.0f;
    }

    public Object(Image image, int x, int y, int w, int h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        opacity = 1.0f;
    }

    public Object() {
        opacity = 1.0f;
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        gg.drawImage(image, x + camX, y + camY, w, h, null);
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public int getY() {return y;}

    public int getX() {return x;}

    public int getPriorityZ() {return priorityZ;}

    public boolean hovering(int x, int y) {
        return x < this.x + w &&
                x > this.x &&
                y < this.y + h &&
                y > this.y;
    }

    public void gotoxy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
