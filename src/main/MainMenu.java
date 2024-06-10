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
    HashMap<String, Object> objects;
    HashMap<String, Image> imageStock;
    boolean click, press;
    int pressX, pressY;

    boolean initialHover = false;

    MainMenu(Panel panel, int width, int height) {
        this.panel = panel;
        visible = true;

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

        objects = new HashMap<>();
        objects.put("START 2", new Object(imageStock.get("1"), panel.width/2, panel.height/2 + 250, 100, 100));
        objects.put("TITLE", new Object(imageStock.get("3"), 0,25,1000,410));
        int menuW = 1754;
        int menuH = 1240;
        if (Math.abs(width - menuW) < Math.abs(height - menuH)) {
            menuH = (int) ((double) menuH / menuW * width);
            menuW = width;
        } else {
            menuW = (int) ((double) menuW / menuH * height);
            menuH = height;
        }
        objects.put("MENUBG", new Object(imageStock.get("4"), 0,0, menuW, menuH));

        objects.put("START BUTTON", new Object(imageStock.get("5"), 0,panel.height/2 - 50,200,80));
        objects.put("SETTINGS BUTTON", new Object(imageStock.get("7"), 0,panel.height/2 + 50,200,80));
        objects.put("ABOUT BUTTON", new Object(imageStock.get("9"), 0,panel.height/2 + 140,200,80));
        objects.put("EXIT BUTTON", new Object(imageStock.get("9"), 12,panel.height/2 + 220,200,80));
//        System.out.println(objects.get("EXIT BUTTON").x);

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
            gg.drawString("main.Main Menu", 0, 50);
        }

        if (objects.get("START BUTTON").hovering(panel.curX, panel.curY)) {
            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("6");
        } else {
            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("5");
        }

        if (objects.get("SETTINGS BUTTON").hovering(panel.curX, panel.curY)) {
            Object o = objects.get("SETTINGS BUTTON");
            o.image = imageStock.get("8");
        } else {
            Object o = objects.get("SETTINGS BUTTON");
            o.image = imageStock.get("7");
        }

        if (objects.get("ABOUT BUTTON").hovering(panel.curX, panel.curY)) {
            Object o = objects.get("ABOUT BUTTON");
            o.image = imageStock.get("10");
        } else {
            Object o = objects.get("ABOUT BUTTON");
            o.image = imageStock.get("9");
        }

        if (objects.get("EXIT BUTTON").hovering(panel.curX, panel.curY)) {
            Object o = objects.get("EXIT BUTTON");
            o.image = imageStock.get("h");
        } else {
            Object o = objects.get("EXIT BUTTON");
            o.image = imageStock.get("t");
        }

        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg, panel.camX, panel.camY);
        objects.get("EXIT BUTTON").draw(gg, panel.camX, panel.camY);

        pressFun();
        clickFun();
    }

    // pressFun detects presses in the screen
    private void pressFun() {
        if (press && objects.get("START BUTTON").hovering(panel.curX, panel.curY) && objects.get("START BUTTON").hovering(pressX, pressY)) {
            // this runs only if the initially pressed location is hovering the start button
            // and if the cursor is hovering the start button
            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("6"); // change the button image
        } else {
            // reset pressX and pressY so the code above will not run when the cursor is dragged out the button and back again
            pressX = -1;
            pressY = -1;

            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("5"); // change the button image
        }
    }

    // clickFun runs when the screen is clicked
    private void clickFun() {
        if (!click) return;

        System.out.print("screen clicked");
        if (objects.get("START BUTTON").hovering(panel.curX, panel.curY)) {
            System.out.print("butt clicked");

            // close the mainMenu
            visible = false;
            // start the game
            panel.game.start();
        }

        if (objects.get("EXIT BUTTON").hovering(panel.curX, panel.curY)) {
            System.out.print("butt clicked");

            // close the mainMenu
            System.exit(0);
        }
        System.out.println();

        click = false;
    }
}
