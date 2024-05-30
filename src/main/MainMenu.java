package main;

import utilities.Object;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class MainMenu {
    Panel panel;
    boolean visible;
    HashMap<String, Object> objects;
    HashMap<String, Image> imageStock;
    boolean click, press;
    int pressX, pressY;

    MainMenu(Panel panel) {
        this.panel = panel;
        visible = true;

        imageStock = new HashMap<>();
        String imagePath = "src/assets/mainMenu/";
        imageStock.put("1", new ImageIcon(imagePath + "my beloved.png").getImage());
        imageStock.put("2", new ImageIcon(imagePath + "my beloved2.png").getImage());

        objects = new HashMap<>();
        objects.put("START BUTTON", new Object(imageStock.get("1"), panel.width/2, panel.height/2 + 250, 100, 100));

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
                pressX = e.getX();
                pressY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
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

        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg, panel.camX, panel.camY);

        pressFun();
        clickFun();
    }

    // pressFun detects presses in the screen
    private void pressFun() {
        if (press && objects.get("START BUTTON").hovering(panel.curX, panel.curY) && objects.get("START BUTTON").hovering(pressX, pressY)) {
            // this runs only if the initially pressed location is hovering the start button
            // and if the cursor is hovering the start button

            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("2"); // change the button image
        } else {
            // reset pressX and pressY so the code above will not run when the cursor is dragged out the button and back again
            pressX = -1;
            pressY = -1;

            Object o = objects.get("START BUTTON");
            o.image = imageStock.get("1"); // change the button image
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
        System.out.println();

        click = false;
    }
}
