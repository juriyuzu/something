package main;

import utilities.Key;
import utilities.Object;
import utilities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SplashPanel extends JPanel implements Runnable {
    int width, height;
    Thread thread;
    public int curX;
    public int curY;
    Object object;
    Key key;
    JFrame frame;
    Main main;
    boolean click;

    SplashPanel(JFrame frame, int width, int height) {
        this.frame = frame;
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);
        frame.add(this);

        thread = new Thread(this);
        thread.start();

        object = new Object(new ImageIcon("src/assets/mainMenu/SPLASH.png").getImage(), 0, 0, width, height);
        key = new Key(frame);

        main = new Main(frame);
        lock = true;

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

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                click = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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

    boolean lock;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        // backdrop
        gg.setColor(new Color(0xA1A1A1));
        gg.fillRect(0, 0, width, height);

        // debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 100));
            gg.setColor(new Color(0x000000));
            gg.drawString("SPLASH SCREEN", 0, 100);
        }

        object.draw(gg, 0, 0);

        if (key.space || click && lock) {
            main.setVisible(true);
            lock = false;
        }

        gg.dispose();
    }
}
