package screens;

import file_manager.FileManager;

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

    // File manager
    private final FileManager fileManager = new FileManager();

    // Images
    private final ImageIcon bgImage;
    private final BufferedImage screenFrameImage;
    private final BufferedImage titleImage;
    private final BufferedImage levelsButtonImage;
    private final BufferedImage infiniteModeButtonImage;
    private final BufferedImage shopButtonImage;
    private final BufferedImage playButton;
    private final BufferedImage settingsButton;
    private final BufferedImage quitButton;
    private final BufferedImage soundOnButton;
    private final BufferedImage soundOffButton;

    // Sounds
    private final Clip backgroundMusicClip;
    private final Clip hoverSoundClip;
    private final Clip buttonPressSoundClip;

    private String hoveredButton = ""; // The button being hovered over
    private boolean soundOn; // Whether the sound is on or off

    public MainMenuScreen(int panelWidth, int panelHeight, ScreenChangeListener listener) {
        // Call the parent constructor
        super(panelWidth, panelHeight, listener);

        soundOn = fileManager.isSoundEnabled();

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
        playButton = loadImage("/assets/main_menu/play_button.png");
        settingsButton = loadImage("/assets/main_menu/settings_button.png");
        quitButton = loadImage("/assets/main_menu/quit_button.png");
        soundOnButton = loadImage("/assets/main_menu/sound_on_button.png");
        soundOffButton = loadImage("/assets/main_menu/sound_off_button.png");

        // Load sounds
        backgroundMusicClip = loadSoundClip("/assets/sounds/lobby_track.wav");
        hoverSoundClip = loadSoundClip("/assets/sounds/button_hover.wav");
        buttonPressSoundClip = loadSoundClip("/assets/sounds/button_press.wav");

        // Load and play background music
        if (backgroundMusicClip != null) {
            if(soundOn) {
                FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusicClip.start();
            }
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

    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent method
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(bgImage.getImage(), 0, 0, (int) (PANEL_WIDTH*1.2), (int) (PANEL_HEIGHT*1.2), this);
        g.drawImage(screenFrameImage, 0, 0, this);
        g.drawImage(titleImage, 0, 0, this);

        // Draw buttons using the stored Y positions from the BUTTON_Y array
        drawButton(g, playButton, BUTTON_X, BUTTON_Y[0], "play", BUTTON_WIDTH, BUTTON_HEIGHT, hoveredButton, HOVER_OPACITY);
        if(soundOn)
            drawButton(g, soundOnButton, BUTTON_X, BUTTON_Y[1], "sound", BUTTON_WIDTH, BUTTON_HEIGHT, hoveredButton, HOVER_OPACITY);
        else
            drawButton(g, soundOffButton, BUTTON_X, BUTTON_Y[1], "sound", BUTTON_WIDTH, BUTTON_HEIGHT, hoveredButton, HOVER_OPACITY);
        drawButton(g, quitButton, BUTTON_X, BUTTON_Y[2], "quit", BUTTON_WIDTH, BUTTON_HEIGHT, hoveredButton, HOVER_OPACITY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the mouse coordinates
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if Play button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[0] && mouseY <= (BUTTON_Y[0] + BUTTON_HEIGHT)) {
            // Play button press sound effect
            if(soundOn) {
                buttonPressSoundClip.setFramePosition(0); // Rewind to the beginning
                buttonPressSoundClip.start();
            }
            screenChangeListener.changeScreen("game");
        }

        // Check if Sound button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[1] && mouseY <= (BUTTON_Y[1] + BUTTON_HEIGHT)) {

            // Toggle sound
            if(soundOn) {
                soundOn = false;
                backgroundMusicClip.stop();
            } else {
                soundOn = true;
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusicClip.start();
            }

            fileManager.saveSoundEnabled(soundOn);
        }

        // Check if Quit button is clicked
        if (mouseX >= BUTTON_X && mouseX <= (BUTTON_X + BUTTON_WIDTH) &&
                mouseY >= BUTTON_Y[2] && mouseY <= (BUTTON_Y[2] + BUTTON_HEIGHT)) {
            if (soundOn) {
                // Play button press sound effect
                buttonPressSoundClip.setFramePosition(0); // Rewind to the beginning
                buttonPressSoundClip.start();
            }
            System.exit(0);
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
            if (isMouseOverButton(mouseX, mouseY, BUTTON_X, BUTTON_Y[i], BUTTON_WIDTH, BUTTON_HEIGHT)) {
                switch (i) {
                    case 0 -> hoveredButton = "play";
                    case 1 -> hoveredButton = "sound";
                    case 2 -> hoveredButton = "quit";
                }
                // Only play the sound if the hovered button has changed
                if (!hoveredButton.equals(previousHoveredButton) && soundOn) {
                    hoverSoundClip.setFramePosition(0); // Rewind to the beginning
                    hoverSoundClip.start();
                }
                break; // Exit the loop after finding the hovered button
            }
        }
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
}
