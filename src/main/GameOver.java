package main;

import utilities.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class GameOver {
    Panel panel;
    boolean visible;
    boolean click, press;
    int pressX, pressY;
    HashMap<String, Object> objects;
    HashMap<String, Image> imageStock;
    Game game;

    GameOver(Panel panel, Game game) {
        this.panel = panel;
        visible = false;
        this.game = game;

        imageStock = new HashMap<>();
        String imagePath = "src/assets/mainMenu/";
        imageStock.put("1", new ImageIcon(imagePath + "my beloved.png").getImage());
        imageStock.put("2", new ImageIcon(imagePath + "my beloved2.png").getImage());

        objects = new HashMap<>();
        objects.put("BUTTON", new Object(imageStock.get("1"), panel.width/2, panel.height/2 + 250, 100, 100));

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
            gg.drawString("main.Game Over", 0, 50);
        }

        gg.setColor(Color.green);
        gg.setFont(new Font("Consolas", Font.BOLD, 100));
        gg.drawString("game over screen", panel.width/2, 200);
        gg.drawString("u lose haha", panel.width/2, 300);

        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg, panel.camX, panel.camY);

        pressFun();
        clickFun();
    }

    private void pressFun() {
        if (press && objects.get("BUTTON").hovering(panel.curX, panel.curY) && objects.get("BUTTON").hovering(pressX, pressY)) {
            Object o = objects.get("BUTTON");
            o.image = imageStock.get("2"); // change the button image
        } else {
            pressX = -1;
            pressY = -1;

            Object o = objects.get("BUTTON");
            o.image = imageStock.get("1"); // change the button image
        }
    }

    private void clickFun() {
        if (!click) return;

        System.out.print("screen clicked");
        if (objects.get("BUTTON").hovering(panel.curX, panel.curY)) {
            System.out.print("butt clicked");

            visible = false;
            panel.mainMenu.visible = true;
            game.bgm.playOnLoop();
        }
        System.out.println();

        click = false;
    }
}
