package main;

import objects.entities.Player;

import java.awt.*;

public class HUD {
    public int hearts;
    boolean visible;

    HUD() {
        hearts = 3;
        visible = true;
    }

    void draw(Graphics2D gg) {
        if (!visible) return;

        gg.setColor(Color.magenta);
        gg.setFont(new Font("Consolas", Font.BOLD, 50));
        gg.drawString(String.valueOf(hearts), 100, 100);
    }
}
