package utilities;

import java.awt.*;

public class Object {
    public Image image;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int priorityZ;

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
    }

    public Object(Image image, int x, int y, int w, int h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Object() {
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(image, x + camX, y + camY, w, h, null);
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
