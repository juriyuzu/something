package objects.tiles;

import main.Game;
import main.Panel;
import utilities.Object;
import utilities.TileType;

import javax.swing.*;

public class Tile extends Object {
    Panel panel;
    Game game;
    public TileType type;
    public int size;
    public boolean solid;

    public Tile(Panel panel, Game game, int x, int y) {
        super(new ImageIcon("").getImage(), x, y, game.tileSize, game.tileSize);
        this.panel = panel;
        this.game = game;
        this.x = x;
        this.y = y;
        size = game.tileSize;
    }
}
