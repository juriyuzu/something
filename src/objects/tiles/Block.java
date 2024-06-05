package objects.tiles;

import main.Game;
import main.Panel;
import utilities.AStar;

import java.awt.*;
import java.util.LinkedList;

public class Block extends Tile {
    public boolean on;
    Image imageOn, imageOff;
    public LinkedList<Integer> group;
    public boolean lock;
    Game game;

    public Block(Panel panel, Game game, int x, int y) {
        super(panel, game, x, y);

        type = TileType.BLOCK;
        on = true;
        solid = true;
        destinationAble = false;
        group = new LinkedList<>();
        lock = false;
        this.game = game;

        imageOn = game.images.get("BLOCK WALL");
        imageOff = game.images.get("BLOCK FLOOR");
    }

    public void addGroup(int n) {group.add(n);}

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);
    }

    public void clickFun() {
        on = !on;
        solid = on;

        if (on && AStar.rectRect(game.player.x, game.player.y, game.player.w/2, game.player.h/2, x, y, w/2, h/2))
            game.player.dead();
    }
}
