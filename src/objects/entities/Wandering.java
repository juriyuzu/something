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
    Follow follow;

    public Wandering(Game game, int[] pos) {
        super();
        x = pos[0] * game.tileSize;
        y = pos[1] * game.tileSize;
        w = game.tileSize;
        h = game.tileSize;
        this.game = game;
        speed = 2;
        visible = true;
        follow = new Follow(game, this, game.tileSize);

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
        int tileSize = game.tileSize;

        if (follow.path == null || follow.path.size() < 2) {
            return;
        }

        int xVel = (follow.path.get(1).x - follow.path.get(0).x) * speed;
        int yVel = (follow.path.get(1).y - follow.path.get(0).y) * speed;

        gotoxy(x + xVel, y + yVel);
        if (Math.abs(follow.path.get(1).x * tileSize - x) < speed && Math.abs(follow.path.get(1).y * tileSize - y) < speed) {
            gotoxy(follow.path.get(1).x * tileSize, follow.path.get(1).y * tileSize);
            follow.refresh(player.location);
        }
    }
}
