package screens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;


public abstract class Screen extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    protected final int PANEL_WIDTH;
    protected int PANEL_HEIGHT;

    public Screen(int width, int height) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        initJPanel();
    }

    protected void initJPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    protected BufferedImage loadImage(String url) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource(url)));
        } catch (IOException e) {
            throw new RuntimeException("Error loading image: " + url, e);
        }
    }
}
