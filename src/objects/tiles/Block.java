package objects.tiles;

import main.Game;
import main.Panel;
import utilities.TileType;

import java.awt.*;

public class Block extends Tile {
    boolean on;
    Image imageOn, imageOff;

    public Block(Panel panel, Game game, int x, int y) {
        super(panel, game, x, y);

        type = TileType.BLOCK;
        on = true;
        solid = true;

        imageOn = game.images.get("BLOCK WALL");
        imageOff = game.images.get("BLOCK FLOOR");
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);
    }
}
