//package screens;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//import gameobjects.Ball;
//import gameobjects.Cannon;
//import screencomponents.GameField;
//
//public class GameManager extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
//    public static final int PANEL_WIDTH = 1600;
//    public static final int PANEL_HEIGHT = 1000;
//    private Timer timer;
//    Cannon cannon;
//    private java.util.List<Ball> balls = new java.util.ArrayList<>();
//    private int ballSpawnDelay = 0; // Delay counter for ball spawning
//
//    private boolean isMousePressed = false;
//
//    private GameField gameField;
//
//    public GameManager() {
//        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // Set the size of the panel
//        setFocusable(true); // Set the panel to be focusable
//
//        // Register key & mouse listeners
//        addKeyListener(this);
//        addMouseListener(this);
//        addMouseMotionListener(this);
//
//        // Add to track mouse movement
//
//        // Create a timer to update the game state
//        timer = new Timer(10, this);
//        timer.start();
//
//        gameField = new GameField(PANEL_WIDTH, PANEL_HEIGHT);
//
//        // Spawn ball on mouse release
////        addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseReleased(MouseEvent e) {
////                ballSpawnDelay = 100;
////            }
////        });
//
//        // Create a cannon object
////        cannon = new Cannon(300, 600, 50, 50);
//
//        //
//
//
//        cannon = new Cannon(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH, PANEL_HEIGHT-40, new Point(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH/2, 0));
//    }
//
//    /**
//     * Paints the game objects on the panel
//     * @param g the <code>Graphics</code> object to protect
//     */
//    @Override
//    protected void paintComponent(Graphics g) {
//        // PAINT TITLE SAYING LEVEL 1 ABOVE THE GAME FIELD
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("Arial", Font.BOLD, 50));
//
//        super.paintComponent(g);
//        gameField.draw(g);
////        for (Ball ball : balls) {
////            ball.draw(g);
////        }
////        cannon.draw(g);
//
//    }
//
//    /**
//     * Updates the game state
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (ballSpawnDelay > 0) {
//            spawnBall(3, (int) (Math.PI*0.5));
//            ballSpawnDelay--;
//        }
//        for (Ball ball : balls) {
//            ball.update();
//        }
//        cannon.update();
//        repaint();
//    }
//
//
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//        // Toggle debug mode
//        if (e.getKeyChar() == 'd') {
//            cannon.toggleDebug();
//            for (Ball ball : balls) {
//                ball.toggleDebug();
//            }
//        }
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {}
//
//    @Override
//    public void keyReleased(KeyEvent e) {}
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        cannon.setMousePosOnPress(e.getPoint());
//        cannon.setCurrentMousePos(e.getPoint());
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        cannon.setMousePosOnRelease(e.getPoint());
//        cannon.setCurrentMousePos(e.getPoint());
//
//        cannon.shoot();
//
//        // Spawn ball if cannon has produced velocity
//        if(cannon.getProducedVelocityMagnitude() > 0)
//            spawnBall(cannon.getAngle(), cannon.getProducedVelocityMagnitude());
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {}
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//
//    @Override
//    public void mouseMoved(MouseEvent e) {}
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        cannon.setCurrentMousePos(e.getPoint());
//    }
//}
