package main;

import objects.entities.Player;
import objects.tiles.Block;
import objects.tiles.Static;
import objects.tiles.Tile;
import utilities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    Panel panel;
    public boolean visible;
    Save save;
    Random random;
    public HashMap<String, Image> images;
    public LinkedList<LinkedList<Tile>> maps;
    public LinkedList<LinkedList<Integer>> mapSizes;
    Player player;
    public int tileSize;
    public int currentMap;
    boolean click, press;
    int pressX, pressY;

    Game(Panel panel) {
        this.panel = panel;
        visible = false;

        tileSize = 70;
        save = new Save();
        random = new Random();

        images = new HashMap<>();
        {
            String imagePath = "src/assets/game/tiles/";
            images.put("PATH", new ImageIcon(imagePath + "path.png").getImage());
            images.put("WALL", new ImageIcon(imagePath + "wall.png").getImage());
            images.put("FLOOR", new ImageIcon(imagePath + "floor.png").getImage());
            images.put("BLOCK FLOOR", new ImageIcon(imagePath + "blockFloor.png").getImage());
            images.put("BLOCK WALL", new ImageIcon(imagePath + "blockWall.png").getImage());
            images.put("EXIT", new ImageIcon(imagePath + "exit.png").getImage());
        }

        int mapsAmount = 3;
        maps = new LinkedList<>();
        mapSizes = new LinkedList<>();
        currentMap = 0;
        for (int i = 0; i < mapsAmount; i++) {
            char[][] map = save.read("src/assets/game/floors/floor " + i + ".txt");
            maps.add(new LinkedList<>());

            mapSizes.add(new LinkedList<>());
            mapSizes.getLast().add(map[0].length);
            mapSizes.getLast().add(map.length);

            for (int j = 0; j < map.length; j++) for (int k = 0; k < map[j].length; k++)
                switch (map[j][k]) {
                    case '.' -> maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.FLOOR));
                    case '0' -> maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.WALL));
                    case '1' -> maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.EXIT));
                    case '2' -> maps.getLast().add(new Block(panel, this, k * tileSize, j * tileSize));
        }}

        player = new Player(panel, this);

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (visible) click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (visible) {
                    press = true;
                    pressX = e.getX();
                    pressY = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (visible) press = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        // Title
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.setColor(new Color(0x000000));
            gg.drawString("main.Game", 0, 50);
        }

        // draw the map
        for (Tile tile : maps.get(currentMap)) tile.draw(gg, panel.camX, panel.camY);

        player.draw(gg, panel.camX, panel.camY);
    }

    public void start() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        visible = true;
        panel.camX = panel.width/2 - tileSize/2 - 13 / 2 * tileSize;
        panel.camY = panel.height/2 - tileSize/2 - 13 / 2 * tileSize;
    }
}
