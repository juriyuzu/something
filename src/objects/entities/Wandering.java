package objects.entities;

import main.Game;
import utilities.Object;

import javax.swing.*;
import java.awt.*;

public class Wandering extends Object {
    Game game;
    Player player;
    int speed;
    boolean visible;

    public Wandering(Game game, int[] pos) {
        super();
        x = pos[0] * game.tileSize;
        y = pos[1] * game.tileSize;
        w = game.tileSize;
        h = game.tileSize;
        this.game = game;
        speed = 2;
        visible = true;

        image = new ImageIcon("src/assets/game/enemy/wandering.png").getImage();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void draw(Graphics2D gg, int camX, int camY) {
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

        gotoxy(x + xVel, y + yVel);
    }
}
