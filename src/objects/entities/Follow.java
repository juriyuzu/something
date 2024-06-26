package objects.entities;

import main.Game;
import objects.tiles.Tile;
import utilities.AStar;
import utilities.Node;
import utilities.Object;

import java.awt.*;
import java.util.List;

public class Follow {
    List<Node> path;
    int pathIndex;
    Tile destination;
    int tileSize;
    Game game;
    Object object;

    Follow(Game game, Object object, int tileSize) {
        this.game = game;
        this.object = object;
        this.tileSize = tileSize;
        path = null;
        pathIndex = 1;
        destination = null;
    }

    public void refresh(Tile destination) {
        this.destination = destination;
        findPath();
    }

    void findPath() {
        System.out.println("find path");
        if (destination == null || !destination.destinationAble) return;

        path = AStar.findPath(tileSize, game.maps.get(game.currentMap), object, game.mapSizes.get(game.currentMap), destination);
        pathIndex = 1;

        if (path != null) {
            for (Node n : path) System.out.println(n.x + ", " + n.y);
            System.out.println("yes path");
        }
    }
}
