package objects.tiles;

import main.Game;
import main.Panel;

import java.awt.*;

public class Block extends Tile {
    boolean on;
    Image imageOn, imageOff;
    public int group;

    public Block(Panel panel, Game game, int x, int y) {
        super(panel, game, x, y);

        type = TileType.BLOCK;
        on = true;
        solid = true;
        destinationAble = false;
        group = -1;

        imageOn = game.images.get("BLOCK WALL");
        imageOff = game.images.get("BLOCK FLOOR");
    }

    public void setGroup(int n) {group = n;}

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);
    }

    public void clickFun() {
        on = !on;
        solid = on;
    }
}
