package screens;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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

    // Method to load a sound clip
    protected Clip loadSoundClip(String soundFilePath) {
        Clip soundClip = null;
        try {
            URL url = this.getClass().getResource(soundFilePath);
            assert url != null;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            soundClip = AudioSystem.getClip();
            soundClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException("Error loading sound clip: " + soundFilePath, e);
        }
        return soundClip;
    }

    // Draw a button on the screen
    protected void drawButton(Graphics g, BufferedImage buttonImage, int x, int y, String buttonName, int BUTTON_WIDTH, int BUTTON_HEIGHT, String hoveredButton, float HOVER_OPACITY) {
        // Apply dark filter if the button is being hovered over
        if (buttonName.equals(hoveredButton)) {
            buttonImage = applyDarkFilter(buttonImage, HOVER_OPACITY);
        }

        g.drawImage(buttonImage, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, this);
    }

    // Apply a dark filter to an image
    protected BufferedImage applyDarkFilter(BufferedImage originalImage, float HOVER_OPACITY) {
        BufferedImage darkImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = darkImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, HOVER_OPACITY));
        g2d.setColor(new Color(0, 0, 0, 127)); // Dark shade
        g2d.fillRect(0, 0, darkImage.getWidth(), darkImage.getHeight());
        g2d.dispose();
        return darkImage;
    }

    protected boolean isMouseOverButton(int mouseX, int mouseY, int buttonX, int buttonY, int BUTTON_WIDTH, int BUTTON_HEIGHT) {
        return mouseX >= buttonX && mouseX <= (buttonX + BUTTON_WIDTH) &&
                mouseY >= buttonY && mouseY <= (buttonY + BUTTON_HEIGHT);
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
