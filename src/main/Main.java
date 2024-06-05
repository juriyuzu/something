package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main extends JFrame {
    Panel panel;

    Main() {
        setLayout(null);
        setResizable(false);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Set the cursor to be invisible
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("");
        Point hotspot = new Point(0, 0);
        Cursor invisibleCursor = toolkit.createCustomCursor(image, hotspot, "InvisibleCursor");
        setCursor(invisibleCursor);

        Main main = this;
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (panel != null) {
                    remove(panel);
                }
                panel = new Panel(main, getWidth(), getHeight());
                add(panel);
                revalidate();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    public static void main(String[] args) {
        System.out.println("Hello Nigga.");
        SwingUtilities.invokeLater(() -> {
            Main mainFrame = new Main();
            mainFrame.setVisible(true);
        });
    }
}
