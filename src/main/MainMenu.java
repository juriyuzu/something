package main;

import utilities.Object;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MainMenu {
    Panel panel;
    boolean visible;
    HashMap<String, Object> objects, objectsMain;
    HashMap<String, Object> objectsAbout, objectsSettings;
    HashMap<String, Image> imageStock;
    boolean click, press;
    int pressX, pressY;
    float leaderBoards;
    boolean about, settings;

    MainMenu(Panel panel, int width, int height) {
        this.panel = panel;
        visible = true;
        leaderBoards = 1;
        about = false;
        settings = false;

        // Initialize imageStock
        {
            imageStock = new HashMap<>();
            String imagePath = "src/assets/mainMenu/";
            imageStock.put("1", new ImageIcon(imagePath + "my beloved.png").getImage());
            imageStock.put("2", new ImageIcon(imagePath + "my beloved2.png").getImage());
            imageStock.put("3", new ImageIcon(imagePath + "Title.png").getImage());
            imageStock.put("4", new ImageIcon(imagePath + "MainMenuBG.png").getImage());
            imageStock.put("5", new ImageIcon(imagePath + "StartOff.png").getImage());
            imageStock.put("6", new ImageIcon(imagePath + "StartOn.png").getImage());
            imageStock.put("7", new ImageIcon(imagePath + "SettingsOff.png").getImage());
            imageStock.put("8", new ImageIcon(imagePath + "SettingsOn.png").getImage());
            imageStock.put("9", new ImageIcon(imagePath + "AboutOff.png").getImage());
            imageStock.put("10", new ImageIcon(imagePath + "AboutOn.png").getImage());
            imageStock.put("t", new ImageIcon(imagePath + "ExitOff.png").getImage());
            imageStock.put("h", new ImageIcon(imagePath + "ExitOn.png").getImage());
            imageStock.put("13", new ImageIcon(imagePath + "LeaderBoardsSign.png").getImage());
            imageStock.put("14", new ImageIcon(imagePath + "LeaderBoardsBoard.png").getImage());
            imageStock.put("15", new ImageIcon(imagePath + "BackOff.png").getImage());
            imageStock.put("16", new ImageIcon(imagePath + "BackOn.png").getImage());
            imageStock.put("17", new ImageIcon(imagePath + "About.jpg").getImage());
        }

        // Initialize objects + objectsMain
        {
            objects = new HashMap<>();
            objects.put("START 2", new Object(imageStock.get("1"), panel.width / 2, panel.height / 2 + 250, 100, 100));
            objects.put("TITLE", new Object(imageStock.get("3"), 0, 25, 1000, 410));
            int menuW = 1754;
            int menuH = 1240;
            float aspectRatio = (float) menuW / menuH;
            if (Math.abs(width - menuW) > Math.abs(height - menuH)) {
                menuW = width;
                menuH = (int) (width / aspectRatio);
            } else {
                menuH = height;
                menuW = (int) (height * aspectRatio);
            }
            objects.put("MENUBG", new Object(imageStock.get("4"), 0, 0, menuW, menuH));

            objectsMain = new HashMap<>();
            objectsMain.put("START BUTTON", new Object(imageStock.get("5"), 0, panel.height / 2 - 50, 200, 80));
            objectsMain.put("SETTINGS BUTTON", new Object(imageStock.get("7"), 0, panel.height / 2 + 50, 200, 80));
            objectsMain.put("ABOUT BUTTON", new Object(imageStock.get("9"), 0, panel.height / 2 + 140, 200, 80));
            objectsMain.put("EXIT_BUTTON", new Object(imageStock.get("9"), 12, panel.height / 2 + 220, 200, 80));
            int w = 249;
            int h = 1240;
            aspectRatio = (float) w / h;
            w = (int) (panel.height * aspectRatio);
            objectsMain.put("LEADERBOARDS SIGN", new Object(imageStock.get("13"), panel.width - w, 0, w, panel.height));
            w = 1215;
            aspectRatio = (float) w / h;
            w = (int) (panel.height * aspectRatio);
            objectsMain.put("LEADERBOARDS BOARD", new Object(imageStock.get("14"), panel.width - w, 0, w, panel.height));
        }

        // Initialize objectsAbout + objectsSettings
        {
            objectsAbout = new HashMap<>();
            objectsAbout.put("BACK BUTTON", new Object(imageStock.get("15"), 12, panel.height / 2 + 220, 200, 80));
            objectsAbout.put("ABOUT", new Object(imageStock.get("17"), panel.width / 2 - 300, panel.height / 2 - 300, 600, 600));

            objectsSettings = new HashMap<>();
            objectsSettings.put("BACK BUTTON", new Object(imageStock.get("15"), 12, panel.height / 2 + 220, 200, 80));
        }

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

        // Title Debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.setColor(new Color(0x000000));
            gg.drawString("main.Main Menu", 0, 50);
        }

        for (HashMap.Entry<String, Object> entry : objects.entrySet())
            entry.getValue().draw(gg, panel.camX, panel.camY);

        if (!about && !settings) {
            // Hovering effects
            {
                if (objectsMain.get("START BUTTON").hovering(panel.curX, panel.curY)) {
                    Object o = objectsMain.get("START BUTTON");
                    o.image = imageStock.get("6");
                } else {
                    Object o = objectsMain.get("START BUTTON");
                    o.image = imageStock.get("5");
                }

                if (objectsMain.get("SETTINGS BUTTON").hovering(panel.curX, panel.curY)) {
                    Object o = objectsMain.get("SETTINGS BUTTON");
                    o.image = imageStock.get("8");
                } else {
                    Object o = objectsMain.get("SETTINGS BUTTON");
                    o.image = imageStock.get("7");
                }

                if (objectsMain.get("ABOUT BUTTON").hovering(panel.curX, panel.curY)) {
                    Object o = objectsMain.get("ABOUT BUTTON");
                    o.image = imageStock.get("10");
                } else {
                    Object o = objectsMain.get("ABOUT BUTTON");
                    o.image = imageStock.get("9");
                }

                if (objectsMain.get("EXIT_BUTTON").hovering(panel.curX, panel.curY)) {
                    Object o = objectsMain.get("EXIT_BUTTON");
                    o.image = imageStock.get("h");
                } else {
                    Object o = objectsMain.get("EXIT_BUTTON");
                    o.image = imageStock.get("t");
                }

                if (objectsMain.get("LEADERBOARDS SIGN").hovering(panel.curX, panel.curY)) {
                    Object o = objectsMain.get("LEADERBOARDS SIGN");
                    leaderBoards -= 0.1f;
                    leaderBoards = Math.clamp(leaderBoards, -1, 2);
                    o.opacity = Math.clamp(leaderBoards, 0, 1);
                    objectsMain.get("LEADERBOARDS BOARD").opacity = 1 - o.opacity;
                } else {
                    Object o = objectsMain.get("LEADERBOARDS SIGN");
                    leaderBoards += 0.1f;
                    leaderBoards = Math.clamp(leaderBoards, -1, 2);
                    o.opacity = Math.clamp(leaderBoards, 0, 1);
                    objectsMain.get("LEADERBOARDS BOARD").opacity = 1 - o.opacity;
                }
            }

            for (HashMap.Entry<String, Object> entry : objectsMain.entrySet())
                entry.getValue().draw(gg, panel.camX, panel.camY);
        }
        else if (about) {
            if (objectsAbout.get("BACK BUTTON").hovering(panel.curX, panel.curY)) {
                Object o = objectsAbout.get("BACK BUTTON");
                o.image = imageStock.get("16");
            } else {
                Object o = objectsAbout.get("BACK BUTTON");
                o.image = imageStock.get("15");
            }

            for (HashMap.Entry<String, Object> entry : objectsAbout.entrySet())
                entry.getValue().draw(gg, panel.camX, panel.camY);
        }
        else {
            if (objectsSettings.get("BACK BUTTON").hovering(panel.curX, panel.curY)) {
                Object o = objectsSettings.get("BACK BUTTON");
                o.image = imageStock.get("16");
            } else {
                Object o = objectsSettings.get("BACK BUTTON");
                o.image = imageStock.get("15");
            }

            for (HashMap.Entry<String, Object> entry : objectsSettings.entrySet())
                entry.getValue().draw(gg, panel.camX, panel.camY);
        }

        pressFun();
        clickFun();
    }

    private void pressFun() {
        if (!press) return;

//        if (objects.get("START BUTTON").hovering(panel.curX, panel.curY) && objects.get("START BUTTON").hovering(pressX, pressY)) {
//            Object o = objects.get("START BUTTON");
//            o.image = imageStock.get("6");
//        } else {
//            pressX = -1;
//            pressY = -1;
//
//            Object o = objects.get("START BUTTON");
//            o.image = imageStock.get("5");
//        }
    }

    private void clickFun() {
        if (!click) return;

        if (!about && !settings) {
            if (objectsMain.get("START BUTTON").hovering(panel.curX, panel.curY)) {
                visible = false;
                panel.game.start();
            }

            if (objectsMain.get("EXIT_BUTTON").hovering(panel.curX, panel.curY)) {
                System.exit(0);
            }

            if (objectsMain.get("ABOUT BUTTON").hovering(panel.curX, panel.curY)) {
                about = true;
            }

            if (objectsMain.get("SETTINGS BUTTON").hovering(panel.curX, panel.curY)) {
                settings = true;
            }
        }
        else {
            if (about && objectsAbout.get("BACK BUTTON").hovering(panel.curX, panel.curY)) {
                about = false;
                settings = false;
            }
            if (settings && objectsSettings.get("BACK BUTTON").hovering(panel.curX, panel.curY)) {
                about = false;
                settings = false;
            }
        }

        click = false;
    }
}
