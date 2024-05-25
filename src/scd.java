import javax.swing.*;

import screens.MainMenuScreen;
import screens.Screen;

import java.awt.*;

public class scd extends JPanel {
    public static final int PANEL_WIDTH = 1600;
    public static final int PANEL_HEIGHT = 1000;

    // All the screens
    public final Screen MAIN_MENU_SCREEN;

    // The current screen
    private Screen currentScreen;

    public scd() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set the size of the panel
        setFocusable(true); // Set the panel to be focusable
        requestFocus(); // Request focus

        // Initialize the screens
        MAIN_MENU_SCREEN = new MainMenuScreen(PANEL_WIDTH, PANEL_HEIGHT);

        // Set the current screen
        setCurrentScreen(MAIN_MENU_SCREEN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw a square
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

//
//        // Draw the current screen
//        currentScreen.draw(g);
    }

    public void setCurrentScreen(Screen screen) {
        // Remove the previous screen
        if (currentScreen != null) {
            this.remove(currentScreen);
        }

        // Update the current screen
        currentScreen = screen;

        // Add the new screen to the JPanel
        this.add(currentScreen);

        // Refresh the JPanel
        validate();
        repaint();
    }
}
