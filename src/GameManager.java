import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gameobjects.Ball;
import gameobjects.Cannon;

public class GameManager extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 600;
    private Timer timer;
    Cannon cannon;
    private java.util.List<Ball> balls = new java.util.ArrayList<>();
    private int ballSpawnDelay = 0; // Delay counter for ball spawning

    private boolean isMousePressed = false;

    public GameManager() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set the size of the panel

        // Register mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        // Add to track mouse movement

        // Create a timer to update the game state
        timer = new Timer(10, this);
        timer.start();

        // Spawn ball on mouse release
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                ballSpawnDelay = 100;
//            }
//        });

        // Create a cannon object
//        cannon = new Cannon(300, 600, 50, 50);

        //


        cannon = new Cannon(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH, PANEL_HEIGHT-40, new Point(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH/2, 0));
    }

    /**
     * Paints the game objects on the panel
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball ball : balls) {
            ball.draw(g);
        }
        cannon.draw(g);

    }

    /**
     * Updates the game state
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (ballSpawnDelay > 0) {
            spawnBall();
            ballSpawnDelay--;
        }
        for (Ball ball : balls) {
            ball.update();
        }
        cannon.update();
        repaint();
    }


    private void spawnBall() {
        // Assuming Ball constructor takes the initial x, y position and direction
        Ball newBall = new Ball(400, 300, 20, 3, 3, Color.RED, PANEL_WIDTH, PANEL_HEIGHT);
        balls.add(newBall);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        cannon.setCurrentMousePos(e.getPoint());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
