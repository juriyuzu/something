package main;

import javax.swing.*;

public class Splash extends JFrame {
    SplashPanel panel;

    Splash() {
        setLayout(null);
        setSize(1000, 710);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        panel = new SplashPanel(this, getWidth(), getHeight());

        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello Nigga.");
        new Splash();
    }
}
