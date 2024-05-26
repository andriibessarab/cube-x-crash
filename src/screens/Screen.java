package screens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Screen extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    protected final int PANEL_WIDTH;
    protected final int PANEL_HEIGHT;
    protected ScreenChangeListener screenChangeListener;
    protected Timer timer;

    public Screen(int width, int height, ScreenChangeListener listener) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.screenChangeListener = listener;
        initJPanel();
        initializeComponents();

        // Create a timer to update the game state
        timer = new Timer(10, this);
        timer.start();
    }

    protected void initJPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // Method to initialize UI components, to be implemented by derived classes
    protected abstract void initializeComponents();

    // Method to update the screen's state, to be implemented by derived classes
    public abstract void update();

    // Method to stop any ongoing operations when the screen is no longer active
    public abstract void stop();

    protected BufferedImage loadImage(String url) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource(url)));
        } catch (IOException e) {
            throw new RuntimeException("Error loading image: " + url, e);
        }
    }

    // Override event methods if needed by derived classes
    @Override
    public void actionPerformed(ActionEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Default implementation, can be overridden
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Default implementation, can be overridden
    }
}
