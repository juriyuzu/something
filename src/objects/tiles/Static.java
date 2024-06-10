package objects.tiles;

import main.Game;
import main.Panel;
import utilities.WeightedRandom;

import java.util.Random;

public class Static extends Tile {
    static int[] weights = {0, 1, 20, 20, 1, 2, 1, 20};

    public Static(Panel panel, Game game, int x, int y, TileType type) {
        super(panel, game, x, y);

        this.type = type;

        solid = type == TileType.WALL;
        destinationAble = type != TileType.WALL;

        switch (type) {
            case WALL -> image = game.images.get("WALL");
                case FLOOR -> image = game.images.get("FLOOR" + WeightedRandom.weightedRandom(weights, weights.length));
            case EXIT -> {
                image = game.images.get("EXIT");
                pauseAble = true;
            }
        }
    }
}
