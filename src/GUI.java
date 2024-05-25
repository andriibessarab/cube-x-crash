import javax.swing.*;
import java.awt.*;

public class GUI {
    private static final String TITLE = "My Game";

    public GUI() {
        JFrame frame = new JFrame(TITLE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
