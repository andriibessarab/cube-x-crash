package screens;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MainMenuScreen extends Screen {
    // URLs
    private final URL BG_URL = getClass().getResource("/assets/bg1.jpg");;

    // Images
    private final BufferedImage BG_IMAGE;

    public MainMenuScreen(int panelWidth, int panelHeight) {
        super(panelWidth, panelHeight);
        assert BG_URL != null;
        try {
            BG_IMAGE = ImageIO.read(BG_URL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the super method
        super.paintComponent(g);

                assert BG_IMAGE != null;
        g.drawImage(BG_IMAGE, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
