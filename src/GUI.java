import javax.swing.*;

public class GUI {
    private static final String TITLE = "CubeXCrash"; // The title of the JFrame

    public GUI() {
        JFrame frame = new JFrame(TITLE); // Create a new JFrame with the specified title

        ScreenManager screenManager = new ScreenManager(); // Create a new ScreenManager
        // GameManager screenManager = new GameManager(); // Create a new GameManager

        // Set up the JFrame
        frame.add(screenManager);
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
