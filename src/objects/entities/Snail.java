package objects.entities;

import main.Game;
import utilities.Object;

import javax.swing.*;
import java.awt.*;

public class Snail extends Object {
    Game game;
    Player player;
    boolean visible;
    int speed;
    int[] toTile;

    public Snail(Game game, Player player) {
        super();
        w = player.w;
        h = player.w;
        this.game = game;
        this.player = player;
        speed = 2;

        visible = false;
        image = new ImageIcon("src/assets/game/enemy/snail.png").getImage();
    }

    public void draw(Graphics2D gg, int camX, int camY) {
//        if (game.clicks > 5) visible = true;

        if (!visible) return;

        gg.drawImage(image, x + camX, y + camY, w, h, null);

        move();
    }

    int xVel = 0;
    int yVel = 0;
    void move() {
        int speed = this.speed * (100 / game.tileSize);

        int xDiff = player.x - x;
        int yDiff = player.y - y;

        if (xDiff == 0 && yDiff == 0) return;

        if (toTile == null || (Math.abs(x - toTile[0]) < speed && Math.abs(y - toTile[1]) < speed)) {
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                int xSign = xDiff / Math.abs(xDiff);
                xVel = xSign * speed;
                yVel = 0;
                toTile = new int[]{xSign * game.tileSize + x, y};
            } else {
                int ySign = yDiff / Math.abs(yDiff);
                yVel = ySign * speed;
                xVel = 0;
                toTile = new int[]{x, ySign * game.tileSize + y};
            }
        }

        gotoxy(x + xVel, y + yVel);
    }

    public void on() {
        visible = true;
    }

    public void reset() {
        toTile = null;
        xVel = 0;
        yVel = 0;
        visible = false;
    }
}
