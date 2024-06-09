package objects.entities;

import main.Game;
import main.HUD;
import main.Panel;
import objects.tiles.Block;
import objects.tiles.Spike;
import objects.tiles.Tile;
import objects.tiles.TileType;
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
    HUD hud;

    public Player(Panel panel, Game game) {
        super();
        tileSize = game.tileSize;
        x = 2 * tileSize;
        y = 5 * tileSize;
        w = tileSize;
        h = tileSize;
        pathImage = new ImageIcon("src/assets/mainMenu/my beloved.png").getImage();
        image = new ImageIcon("src/assets/game/player/player.png").getImage();

        this.panel = panel;
        this.game = game;
        hud = game.hud;
        move = false;

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
        gg.drawString("move: " + move, panel.curX + 10, panel.curY + 45);
        gg.drawString(game.clicks + "", panel.curX + 10, panel.curY + 60);

        move();

        pressFun();
        clickFun();
    }

    void move() {
        if (game.snail.visible && AStar.rectRect(game.snail.x, game.snail.y, game.snail.w/2, game.snail.h/2, x, y, w/2, h/2)) {
            System.out.println("dead");
            dead();
        }

        if (!move || path == null || path.size() < 2) {
            move = false;
            path = null;
            return;
        }

        int speed = 3 * tileSize / 100;
//        int xVel = (path.get(pathIndex).x - path.get(pathIndex - 1).x) * speed;
//        int yVel = (path.get(pathIndex).y - path.get(pathIndex - 1).y) * speed;

        int xDiff = path.get(pathIndex).x - path.get(pathIndex - 1).x;
        int yDiff = path.get(pathIndex).y - path.get(pathIndex - 1).y;
        int xVel = 0;
        int yVel = 0;
        if (xDiff != 0 || yDiff != 0) {
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                int value = (int) ((path.get(pathIndex).x * tileSize - x) * 0.1);
                xVel = value == 0 ? (xDiff > 0 ? 1 : -1) : value;
            } else {
                int value = (int) ((path.get(pathIndex).y * tileSize - y) * 0.1);
                yVel = value == 0 ? (yDiff > 0 ? 1 : -1) : value;
            }
        }

        gotoxy(x + xVel, y + yVel);
        if (Math.abs(path.get(pathIndex).x * tileSize - x) == 0 && Math.abs(path.get(pathIndex).y * tileSize - y) == 0) {
            gotoxy(path.get(pathIndex).x * tileSize, path.get(pathIndex).y * tileSize);

            Tile tileCheck = null;
            boolean pause = false;
            for (Tile tile : game.pauseAble.get(game.currentMap))
                if (tile.pauseAble && tile.x == path.get(pathIndex).x * tileSize && tile.y == path.get(pathIndex).y * tileSize) {
                    if (tile.type == TileType.HAZARD) {
                        Spike spike = (Spike) tile;
                        if (spike.spikeCheck()) continue;
                    }
                    tileCheck = tile;
                    pause = true;
                    break;
            }

            pathIndex++;
            if (pause || pathIndex == path.size() || stopMove) {
                move = false;
                path = null;
                if (pause) {
                    game.pause = true;
                    stopMove = false;
                    switch (tileCheck.type) {
                        case EXIT -> game.nextLevel(game.currentMap + 1);
                        case HAZARD -> dead();
                    }
                }
                if (stopMove) {
                    stopMove = false;
                    findPath();
                }
            }
        }
    }

    void findPath() {
        if (destination == null || !destination.destinationAble) return;

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
        if (game.pause || !click) {
            click = false;
            return;
        }
        panel.clickEffect = 10;
        panel.clicks++;
        game.panel.sound.play("BLIP");
        game.clicks++;

        System.out.print("screen clicked");
        for (Tile tile : game.maps.get(game.currentMap)) if (tile.hovering(panel.curX - panel.camX, panel.curY - panel.camY)) {
            System.out.print("clicked tile at " + tile.getX() + ", " + tile.getY());
            if (move) stopMove = true;
            if (!move) {
                if (tile.type != TileType.HAZARD) tile.clickFun();
                if (tile.type == TileType.BLOCK) {
                    Block block = (Block) tile;
                    if (!block.group.isEmpty()) {
                        block.clickFun();
                        for (int n : block.group) game.groupBlocks.get(game.currentMap).toggle(n);
                        for (int n : block.group) game.groupBlocks.get(game.currentMap).unlock(n);
                    }
                }
            }
            if (!move) {
                destination = tile;
                if (path == null) findPath();
            } else destination = null;
            break;
        }
        System.out.println();

        click = false;
    }

    public void dead() {
        destination = null;
        game.pause = true;
        move = false;
        path = null;
        hud.hearts--;
        if (hud.hearts == 0) game.over();
        else {
            game.start();
        }
    }
}
