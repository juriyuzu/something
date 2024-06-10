package main;

import java.awt.*;

public class Pause {
    Game game;
    int width, height;

    Pause(Game game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
    }

    void draw(Graphics2D gg) {
        if (game.pause || !game.visible) return;

        gg.setColor(Color.ORANGE);
        gg.setFont(new Font("Consolas", Font.BOLD, 50));
        gg.drawString("PAUSED", 100, height/2);
    }
}
