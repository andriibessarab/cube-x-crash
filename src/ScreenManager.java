import screens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenManager extends JPanel implements ActionListener, ScreenChangeListener {
    public static final int PANEL_WIDTH = 1600;
    public static final int PANEL_HEIGHT = 1000;
    private final Timer timer;

    // All the screens
    private final Screen mainMenuScreen;
    private final Screen gameScreen;

    // Current screen
    private Screen currentScreen;

    public ScreenManager() {
        // Initialize the screens
        mainMenuScreen = new MainMenuScreen(PANEL_WIDTH, PANEL_HEIGHT, this);
        gameScreen = new GameScreen(PANEL_WIDTH, PANEL_HEIGHT, this);

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set the size of the panel
        setFocusable(true); // Set the panel to be focusable
        requestFocus(); // Request focus for the panel

        setCurrentScreen(mainMenuScreen);

        // Create a timer to update the game state
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call the super method
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setCurrentScreen(Screen screen) {
        // Remove the previous screen
        if (currentScreen != null) {
            this.remove(currentScreen);
        }

        // Update the current screen
        currentScreen = screen;

        // Set the layout manager
        this.setLayout(new BorderLayout());

        // Add the new screen to the JPanel
        this.add(currentScreen, BorderLayout.CENTER);

        // Refresh the JPanel
        this.revalidate();
        this.repaint();
    }

    @Override
    public void changeScreen(String screenName) {
        Screen newScreen = null;

        // Check which screen to switch to
        switch (screenName) {
            case "main-menu" -> setCurrentScreen(mainMenuScreen);
            case "game" -> setCurrentScreen(gameScreen);
        }

    }
}
