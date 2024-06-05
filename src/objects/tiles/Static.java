package objects.tiles;

import main.Game;
import main.Panel;

import java.util.Random;

public class Static extends Tile {

    public Static(Panel panel, Game game, int x, int y, TileType type) {
        super(panel, game, x, y);

        this.type = type;

        solid = type == TileType.WALL;
        destinationAble = type != TileType.WALL;

        switch (type) {
            case WALL -> image = game.images.get("WALL");
            case FLOOR -> image = game.images.get("FLOOR" + game.random.nextInt(0, 12));
            case EXIT -> {
                image = game.images.get("EXIT");
                pauseAble = true;
            }
        }

    }
}
