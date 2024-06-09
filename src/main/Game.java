package main;

import objects.entities.Player;
import objects.entities.Snail;
import objects.entities.Wandering;
import objects.tiles.*;
import utilities.*;
import utilities.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class Game {
    public Panel panel;
    public boolean visible;
    Save save;
    public Random random;
    public HashMap<String, Image> images;
    public LinkedList<LinkedList<Tile>> maps;
    public LinkedList<LinkedList<Integer>> playerPos;
    public LinkedList<LinkedList<Wandering>> wanderings;
    public LinkedList<GroupBlock> groupBlocks;
    public LinkedList<LinkedList<Integer>> mapSizes;
    public Player player;
    public Snail snail;
    public int tileSize;
    public int currentMap;
    boolean click, press;
    int pressX, pressY;
    public boolean pause;
    public LinkedList<LinkedList<Tile>> pauseAble;
    public HUD hud;
    public int clicks;
    Key key;
    Object bg;
//    Sound2 bgm;

    Game(Panel panel, Main main) {
        this.panel = panel;
        visible = false;

        tileSize = 75;
        save = new Save();
        random = new Random();
        key = new Key(main);
//        bgm = new Sound2("src/assets/sounds/Shikanokonokonokokoshitantan.wav");
//        bgm.playOnLoop();
        panel.sound.playOnLoop("YES");

        images = new HashMap<>();
        {
            String imagePath = "src/assets/game/tiles/";
            images.put("PATH", new ImageIcon(imagePath + "path.png").getImage());
            images.put("WALL", new ImageIcon(imagePath + "wall.png").getImage());
            for (int i = 1; i < 11; i++) images.put("FLOOR" + i, new ImageIcon(imagePath + "floor" + i + ".png").getImage());
            images.put("BLOCK FLOOR", new ImageIcon(imagePath + "blockFloor.png").getImage());
            images.put("BLOCK WALL", new ImageIcon(imagePath + "blockWall.png").getImage());
            images.put("EXIT", new ImageIcon(imagePath + "exit.png").getImage());
            images.put("BG", new ImageIcon(imagePath + "bg.png").getImage());
        }
        bg = new Object(images.get("BG"), 0, 0, panel.width, panel.height);

        int mapsAmount = 6;
        maps = new LinkedList<>();
        playerPos = new LinkedList<>();
        wanderings = new LinkedList<>();
        groupBlocks = new LinkedList<>();
        mapSizes = new LinkedList<>();
        pauseAble = new LinkedList<>();
        currentMap = 0;
        for (int i = 0; i < mapsAmount; i++) {
            char[][] map = save.read("src/assets/game/floors/maps/floor " + i + ".txt");
            LinkedList<char[][]> groupBlocksMaps = new LinkedList<>();
            for (int j = 0; j < 3; j++) groupBlocksMaps.add(save.read("src/assets/game/floors/groupBlocks/floor " + i + "." + j + ".txt"));
            maps.add(new LinkedList<>());

            mapSizes.add(new LinkedList<>());
            mapSizes.getLast().add(map[0].length);
            mapSizes.getLast().add(map.length);
            playerPos.add(new LinkedList<>());
            wanderings.add(new LinkedList<>());
            groupBlocks.add(new GroupBlock());
            pauseAble.add(new LinkedList<>());

            for (int j = 0; j < map.length; j++) for (int k = 0; k < map[j].length; k++)
                switch (map[j][k]) {
                    case '.', ',', 'A' -> {
                        maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.FLOOR));
                        switch (map[j][k]) {
                            case ',' -> {
                                playerPos.getLast().add(j);
                                playerPos.getLast().add(k);
                            }
                            case 'A' -> {
                                wanderings.getLast().add(new Wandering(this, new int[]{k, j}));
                            }
                        }
                    }
                    case '0' -> maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.WALL));
                    case '1' -> {
                        maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.EXIT));
                        pauseAble.getLast().add(maps.getLast().getLast());
                    }
                    case '2' -> {
                        Block block = new Block(panel, this, k * tileSize, j * tileSize);
                        maps.getLast().add(block);
                        if (!groupBlocksMaps.isEmpty()) {
                            for (char[][] m : groupBlocksMaps) {
                                if (m != null && m[j][k] != '.') {
                                    block.addGroup(m[j][k]);
                                    groupBlocks.getLast().add(m[j][k], block);
                                }
                            }
                        }
                    }
                    case '3', '4' -> {
                        maps.getLast().add(new Static(panel, this, k * tileSize, j * tileSize, TileType.FLOOR));
                        Spike spike = new Spike(panel, this, k * tileSize, j * tileSize, map[j][k] != '3');
                        maps.getLast().add(spike);
                        pauseAble.getLast().add(spike);
                        if (!groupBlocksMaps.isEmpty()) {
                            for (char[][] m : groupBlocksMaps) {
                                if (m != null && m[j][k] != '.') {
                                    spike.addGroup(m[j][k]);
                                    groupBlocks.getLast().add(m[j][k], spike);
                                }
                            }
                        }
        }}}
        System.out.println(playerPos.size() + " " + playerPos.getFirst().size());

        hud = new HUD(panel.width, panel.height);
        player = new Player(panel, this);
        snail = new Snail(this, player);

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

        if (key.space && !key.spaceL) {
            nextLevel(currentMap + 1);
            key.spaceL = true;
        } else key.spaceL = false;

        // Title
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.setColor(new Color(0x000000));
            gg.drawString("main.Game", 0, 50);
        }

        bg.draw(gg, 0, 0);

        // draw the map
        for (Tile tile : maps.get(currentMap)) tile.draw(gg, panel.camX, panel.camY);

        snail.draw(gg, panel.camX, panel.camY);
        player.draw(gg, panel.camX, panel.camY);
        for (Wandering w : wanderings.get(currentMap)) w.draw(gg, panel.camX, panel.camY);

        hud.draw(gg);

    }

    public void start() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        visible = true;
        panel.camX = panel.width/2 - tileSize/2 - mapSizes.get(currentMap).getLast() / 2 * tileSize;
        panel.camY = panel.height/2 - tileSize/2 - mapSizes.get(currentMap).getLast() / 2 * tileSize;
        player.x = playerPos.get(currentMap).getLast() * tileSize;
        player.y = playerPos.get(currentMap).getFirst() * tileSize;

        pause = false;

        snail.gotoxy(player.x, player.y);
        clicks = 0;
        snail.reset();
    }

    public void nextLevel(int next) {
        currentMap = next;
        for (Wandering w : wanderings.get(currentMap)) w.setPlayer(player);
        start();
    }

    public void over() {
        visible = false;
        currentMap = 0;
        panel.camX = 0;
        panel.camY = 0;
        hud.hearts = 3;
        panel.gameOver.visible = true;
        panel.sound.stop("YES");
    }
}
