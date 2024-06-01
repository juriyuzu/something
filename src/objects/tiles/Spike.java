package objects.tiles;

import main.Game;
import main.Panel;

import javax.swing.*;

public class Spike extends Tile {
    boolean on;

    public Spike(Panel panel, Game game, int x, int y) {
        super(panel, game, x, y);

        type = TileType.HAZARD;
        on = true;
        image = new ImageIcon("src/assets/game/tiles/spike.png").getImage();
        destinationAble = true;
        pauseAble = true;
    }
}
