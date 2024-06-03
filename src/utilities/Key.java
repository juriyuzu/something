package utilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key {
    double curX, curY;
    public boolean w, a, s, d, space, left, right, esc;
    public boolean wL, aL, sL, dL, spaceL, leftL, rightL, escL;
    int frameX, frameY;

    public Key(Container c) {
        c.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = true;
                    case KeyEvent.VK_A -> a = true;
                    case KeyEvent.VK_S -> s = true;
                    case KeyEvent.VK_D -> d = true;
                    case KeyEvent.VK_SPACE -> space = true;
                    case KeyEvent.VK_LEFT-> left = true;
                    case KeyEvent.VK_RIGHT -> right = true;
                    case KeyEvent.VK_ESCAPE -> esc = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = false;
                    case KeyEvent.VK_A -> a = false;
                    case KeyEvent.VK_S -> s = false;
                    case KeyEvent.VK_D -> d = false;
                    case KeyEvent.VK_SPACE -> space = false;
                    case KeyEvent.VK_LEFT-> left = false;
                    case KeyEvent.VK_RIGHT -> right = false;
                    case KeyEvent.VK_ESCAPE -> esc = false;
                }
            }
        });
    }

    double getCurXY(boolean x) {
        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX();
        curY = cursor.getY();
        return (x ? curX : curY);
    }

    double getCurXY(boolean x, Container c) {
        frameX = c.getX();
        frameY = c.getY();

        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX() - frameX - 8;
        curY = cursor.getY() - frameY - 31;
        return (x ? curX : curY);
    }
}
