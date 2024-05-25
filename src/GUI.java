import javax.swing.*;

public class GUI {
    private static final String TITLE = "CubeXCrash"; // The title of the JFrame

    public GUI() {
        JFrame frame = new JFrame(TITLE); // Create a new JFrame with the specified title
        GameManager gameManager = new GameManager(); // Create an instance of the GameManager class

        // Set up the JFrame
        frame.add(gameManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
