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
    int pathIndex;
    boolean stopMove;
    Tile destination;
    Image pathImage;

    public Player(Panel panel, Game game) {
        super();
        tileSize = game.tileSize;
        x = 2 * tileSize;
        y = 5 * tileSize;
        w = tileSize;
        h = tileSize;
        image = new ImageIcon("src/assets/game/player/player.png").getImage();
        pathImage = new ImageIcon("src/assets/mainMenu/my beloved.png").getImage();

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
        if (path != null) for (Node node : path)
            gg.drawImage(pathImage, node.x * tileSize + camX, node.y * tileSize + camY, tileSize / 2, tileSize / 2, null);

        gg.drawImage(image, x + camX, y + camY, w, h, null);

        gg.setColor(Color.black);
        gg.setFont(new Font("Consolas", Font.BOLD, 15));
        gg.drawString(String.valueOf(pathIndex), panel.curX + 10, panel.curY + 30);

        move();

        pressFun();
        clickFun();
    }

    void move() {
        if (!move || path == null || path.size() < 2) return;

        int speed = 2;
        int xVel = (path.get(pathIndex).x - path.get(pathIndex - 1).x) * speed;
        int yVel = (path.get(pathIndex).y - path.get(pathIndex - 1).y) * speed;

        gotoxy(x + xVel, y + yVel);
        if (Math.abs(path.get(pathIndex).x * tileSize - x) < speed && Math.abs(path.get(pathIndex).y * tileSize - y) < speed) {
            gotoxy(path.get(pathIndex).x * tileSize, path.get(pathIndex).y * tileSize);
            pathIndex++;

            if (pathIndex == path.size() || stopMove) {
                move = false;
                path = null;
                if (stopMove) {
                    stopMove = false;
                    findPath();
                }
            }
        }
    }

    void findPath() {
        path = AStar.findPath(tileSize, game.maps.get(game.currentMap), this, game.mapSizes.get(game.currentMap), destination);
        pathIndex = 1;

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
        panel.clickEffect = 10;
        panel.clicks++;

        System.out.print("screen clicked");
        for (Tile tile : game.maps.get(game.currentMap)) if (tile.hovering(panel.curX - panel.camX, panel.curY - panel.camY)) {
            stopMove = true;
            System.out.print("clicked tile at " + tile.getX() + ", " + tile.getY());
            destination = tile;
            if (path == null) findPath();
            break;
        }
        System.out.println();

        click = false;
    }
}
