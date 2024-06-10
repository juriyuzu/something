package main;

import utilities.Object;
import utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class Panel extends JPanel implements Runnable {
    int width, height;
    Thread thread;
    public int curX;
    public int curY;
    public int camX;
    public int camY;
    MainMenu mainMenu;
    Game game;
    GameOver gameOver;
    public Sound sound;
    Object cursor;
    public Pause pause;

    Panel(Main main, int width, int height) {
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);
        main.add(this);

        thread = new Thread(this);
        thread.start();

        mainMenu = new MainMenu(this, width, height);
        sound = new Sound();
        game = new Game(this, main);
        pause = new Pause(game, width, height);
        gameOver = new GameOver(this, game);
        cursor = new Object(new ImageIcon("src/assets/mainMenu/cursor.png").getImage(), 0, 0, 100, 100);

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
        pause.draw(gg);
        gameOver.draw(gg);
        clickEffect(gg);

        // debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 15));
            gg.setColor(new Color(0x000000));
            gg.drawString(width + " " + height, curX + 10, curY);
            gg.drawString(curX + " " + curY, curX + 10, curY + 15);
        }

        cursor.draw(gg, 0, 0);
        cursor.gotoxy(curX - 15, curY - 15);

        gg.dispose();
    }

    public int clickEffect, clicks = 0;
    void clickEffect(Graphics2D gg) {
        if (clickEffect > 0) {
            gg.setFont(new Font("Consolas", Font.BOLD, 100));
            gg.drawString("CLICK " + clicks, curX, curY - 100);
        }
        clickEffect = Math.max(clickEffect - 1, 0);
    }
}
