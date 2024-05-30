package objects.entities;

import main.Game;
import main.Panel;
import objects.tiles.Tile;
import utilities.AStar;
import utilities.Node;
import utilities.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Player extends Object {
    Game game;
    Panel panel;
    Image image;
    int tileSize;
    boolean move;
    List<Node> path;
    boolean click, press;
    int pressX, pressY;

    public Player(Panel panel, Game game) {
        super();
        tileSize = game.tileSize;
        x = 2 * tileSize;
        y = 5 * tileSize;
        w = tileSize;
        h = tileSize;
        image = new ImageIcon("src/assets/game/player/player.png").getImage();

        this.panel = panel;
        this.game = game;

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (game.visible) click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (game.visible) {
                    press = true;
                    pressX = e.getX();
                    pressY = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (game.visible) press = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void draw(Graphics2D gg, int camX, int camY) {
        gg.drawImage(image, x + camX, y + camY, w, h, null);

        move();

        pressFun();
        clickFun();
    }

    void move() {
        if (!move || path == null || path.size() < 2) return;

        int speed = 5;
        int xVel = (path.get(1).x - path.get(0).x) * speed;
        int yVel = (path.get(1).y - path.get(0).y) * speed;

        gotoxy(x + xVel, y + yVel);
    }

    void findPath(Tile tile) {
        path = AStar.findPath(tileSize, game.maps.get(game.currentMap), this, game.mapSizes.get(game.currentMap), tile);

        if (path != null) {
            for (Node n : path) System.out.println(n.x + ", " + n.y);
            move = true;
        }
    }

    private void pressFun() {
//        if (press && objects.get("START BUTTON").hovering(panel.curX, panel.curY) && objects.get("START BUTTON").hovering(pressX, pressY)) {
//        } else {
//        }
    }

    private void clickFun() {
        if (!click) return;

        System.out.print("screen clicked");
        for (Tile tile : game.maps.get(game.currentMap)) if (tile.hovering(panel.curX - panel.camX, panel.curY - panel.camY)) {
            System.out.print("clicked tile at " + tile.getX() + ", " + tile.getY());
            tile.image = new ImageIcon("src/assets/game/player/player.png").getImage();
            findPath(tile);
            break;
        }
        System.out.println();

        click = false;
    }
}
