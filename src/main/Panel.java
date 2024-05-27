package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel implements Runnable {
    int width, height;
    Thread thread;
    int curX, curY;
    int camX, camY;
    MainMenu mainMenu;
    Game game;

    Panel(Main main, int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);
        main.add(this);

        thread = new Thread(this);
        thread.start();

        mainMenu = new MainMenu(this);
        game = new Game(this);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }
        });
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        // backdrop
        gg.setColor(new Color(0xA1A1A1));
        gg.fillRect(0, 0, width, height);

        mainMenu.draw(gg);
        game.draw(gg);

        // debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 15));
            gg.setColor(new Color(0x000000));
            gg.drawString(width + " " + height, curX + 10, curY);
            gg.drawString(curX + " " + curY, curX + 10, curY + 15);
        }

        gg.dispose();
    }
}
