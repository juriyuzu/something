package objects.tiles;

import main.Game;
import main.Panel;
import utilities.TileType;

public class Static extends Tile {

    public Static(Panel panel, Game game, int x, int y, TileType type) {
        super(panel, game, x, y);

        this.type = type;
        solid = type == TileType.WALL;

        switch (type) {
            case TileType.WALL -> image = game.images.get("WALL");
            case TileType.FLOOR -> image = game.images.get("FLOOR");
            case TileType.EXIT -> image = game.images.get("EXIT");
        }

    }
}
