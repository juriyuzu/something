package objects.entities;

import main.Game;
import utilities.AStar;
import utilities.Node;
import utilities.Object;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Player extends Object {
    Image image;
    int tileSize;

    public Player(Game game) {
        super();
        tileSize = game.tileSize;
        x = 3 * tileSize;
        y = 6 * tileSize;
        w = tileSize;
        h = tileSize;
        image = new ImageIcon("src/assets/game/player/player.png").getImage();

        List<Node> path = AStar.findPath(tileSize, game.maps.get(game.currentMap), this, game.mapSizes.get(game.currentMap));

        if (path != null) for (Node n : path) System.out.println(n.x + ", " + n.y);
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(image, x + camX, y + camY, w, h, null);
    }
}
