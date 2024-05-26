package screens;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;

public class MainMenuScreen extends Screen {
    // Button dimensions
    private final double BUTTON_WIDTH_RATIO = 0.25;
    private final double BUTTON_HEIGHT_RATIO = 35.0 / 9.0;
    private final int BUTTON_SPACING = 10;
    private final float HOVER_OPACITY = 0.5f; // 50% transparency
    private final int BUTTON_WIDTH;
    private final int BUTTON_HEIGHT;
    private final int BUTTON_X;
    private final int[] BUTTON_Y;

    // Images
    private final ImageIcon bgImage;
    private final BufferedImage screenFrameImage;
    private final BufferedImage titleImage;
    private final BufferedImage levelsButtonImage;
    private final BufferedImage infiniteModeButtonImage;
    private final BufferedImage shopButtonImage;

    // Sounds
    private final Clip backgroundMusicClip;
    private final Clip hoverSoundClip;
    private final Clip buttonPressSoundClip;

    private String hoveredButton = ""; // The button being hovered over

    public MainMenuScreen(int panelWidth, int panelHeight, ScreenChangeListener listener) {
        // Call the parent constructor
        super(panelWidth, panelHeight, listener);

        // Calculate button dimensions
        BUTTON_WIDTH = (int) (panelWidth * BUTTON_WIDTH_RATIO);
        BUTTON_HEIGHT = (int) (BUTTON_WIDTH / BUTTON_HEIGHT_RATIO);
        BUTTON_X = (PANEL_WIDTH - BUTTON_WIDTH) / 2;
        BUTTON_Y = new int[3];
        BUTTON_Y[0] = (panelHeight - BUTTON_HEIGHT) / 2 - (int) (panelHeight * 0.05);
        for (int i = 1; i < BUTTON_Y.length; i++) {
            BUTTON_Y[i] = BUTTON_Y[i - 1] + BUTTON_HEIGHT + BUTTON_SPACING;
        }

        // Load images
        bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/main_menu/bg2.gif")));
        screenFrameImage = loadImage("/assets/main_menu/screen_frame.png");
        titleImage = loadImage("/assets/main_menu/title.png");
        levelsButtonImage = loadImage("/assets/main_menu/button_levels.png");
        infiniteModeButtonImage = loadImage("/assets/main_menu/button_infinite_mode.png");
        shopButtonImage = loadImage("/assets/main_menu/button_shop.png");

        // Load sounds
        backgroundMusicClip = loadSoundClip("/assets/sounds/lobby_track.wav");
        hoverSoundClip = loadSoundClip("/assets/sounds/button_hover.wav");
        buttonPressSoundClip = loadSoundClip("/assets/sounds/button_press.wav");

        // Load and play background music

        if (backgroundMusicClip != null) {
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Play continuously
            backgroundMusicClip.start();
        }
    }

    @Override
    protected void initializeComponents() {

    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    // Method to load a sound clip
    private Clip loadSoundClip(String soundFilePath) {
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

    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent method
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(bgImage.getImage(), 0, 0, (int) (PANEL_WIDTH*1.2), (int) (PANEL_HEIGHT*1.2), this);
        g.drawImage(screenFrameImage, 0, 0, this);
        g.drawImage(titleImage, 0, 0, this);

        // Draw buttons using the stored Y positions from the BUTTON_Y array
        drawButton(g, levelsButtonImage, BUTTON_X, BUTTON_Y[0], "levels");
        drawButton(g, infiniteModeButtonImage, BUTTON_X, BUTTON_Y[1], "infinite");
        drawButton(g, shopButtonImage, BUTTON_X, BUTTON_Y[2], "shop");
    }


    // Draw a button on the screen
    private void drawButton(Graphics g, BufferedImage buttonImage, int x, int y, String buttonName) {
        // Apply dark filter if the button is being hovered over
        if (buttonName.equals(hoveredButton)) {
            buttonImage = applyDarkFilter(buttonImage);
        }

        g.drawImage(buttonImage, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, this);
    }

    // Apply a dark filter to an image
    private BufferedImage applyDarkFilter(BufferedImage originalImage) {
        BufferedImage darkImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = darkImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, HOVER_OPACITY));
        g2d.setColor(new Color(0, 0, 0, 127)); // Dark shade
        g2d.fillRect(0, 0, darkImage.getWidth(), darkImage.getHeight());
        g2d.dispose();
        return darkImage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the mouse coordinates
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if Levels button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[0] && mouseY <= (BUTTON_Y[0] + BUTTON_HEIGHT)) {
            // Play button press sound effect
            buttonPressSoundClip.setFramePosition(0); // Rewind to the beginning
            buttonPressSoundClip.start();
            System.out.println("Levels button pressed");
        }

        // Check if Infinite Mode button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[1] && mouseY <= (BUTTON_Y[1] + BUTTON_HEIGHT)) {
            // Play button press sound effect
            buttonPressSoundClip.setFramePosition(0); // Rewind to the beginning
            buttonPressSoundClip.start();

            screenChangeListener.changeScreen("game");
        }

        // Check if Shop button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[2] && mouseY <= (BUTTON_Y[2] + BUTTON_HEIGHT)) {
            // Play button press sound effect
            buttonPressSoundClip.setFramePosition(0); // Rewind to the beginning
            buttonPressSoundClip.start();
            System.out.println("Shop button pressed");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check which button is being hovered over
        String previousHoveredButton = hoveredButton;
        hoveredButton = "";
        for (int i = 0; i < BUTTON_Y.length; i++) {
            if (isMouseOverButton(mouseX, mouseY, BUTTON_X, BUTTON_Y[i])) {
                switch (i) {
                    case 0 -> hoveredButton = "levels";
                    case 1 -> hoveredButton = "infinite";
                    case 2 -> hoveredButton = "shop";
                }
                // Only play the sound if the hovered button has changed
                if (!hoveredButton.equals(previousHoveredButton)) {
                    hoverSoundClip.setFramePosition(0); // Rewind to the beginning
                    hoverSoundClip.start();
                }
                break; // Exit the loop after finding the hovered button
            }
        }
    }


    private boolean isMouseOverButton(int mouseX, int mouseY, int buttonX, int buttonY) {
        return mouseX >= buttonX && mouseX <= (buttonX + BUTTON_WIDTH) &&
                mouseY >= buttonY && mouseY <= (buttonY + BUTTON_HEIGHT);
    }
}
