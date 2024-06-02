package main;

import objects.entities.Player;

import javax.swing.*;
import java.awt.*;
public class HUD {
    public int hearts;
    boolean visible;
    int fps, fpsCounter;
    int width, height;

    HUD(int width, int height) {
        this.width = width;
        this.height = height;

        hearts = 3;
        visible = true;

        Timer timer = new Timer(16, e -> {
            fpsCounter++;
        });
        timer.start();

        Timer timer2 = new Timer(1000, e -> {
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer2.start();
    }

    void draw(Graphics2D gg) {
        if (!visible) return;

        gg.setColor(Color.magenta);
        gg.setFont(new Font("Consolas", Font.BOLD, 50));
        gg.drawString(String.valueOf(hearts), 100, 100);

        gg.setColor(Color.orange);
        gg.setFont(new Font("Consolas", Font.BOLD, 50));
        gg.drawString(fps + ": " + fpsCounter, width - 200, 100);
    }
}
