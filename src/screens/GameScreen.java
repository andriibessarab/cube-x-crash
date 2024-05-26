package screens;

import gameobjects.Ball;
import gameobjects.Cannon;
import gameobjects.bricks.Brick;
import gameobjects.bricks.SquareBrick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameScreen extends Screen {
    public final static int GAME_WIDTH = 1266;
    public final static int GAME_HEIGHT = 632;
    public final static int GAME_X = 169;
    public final static int GAME_Y = 130;
    private final static int GAME_ROWS = 5;
    private final static int ALLOCATED_AREA = 20;
    private final static int SQUARE_BLOCK_SIDE =  (GAME_HEIGHT - ALLOCATED_AREA) / GAME_ROWS;
    private final static int GAME_COLS = GAME_WIDTH / SQUARE_BLOCK_SIDE;


    private BufferedImage gameFrame;

    private java.util.List<Ball> balls;
    private Brick[][] bricks;
    private SquareBrick squareBrick;
    private Cannon cannon;

    private Point shootingPosition;
    private boolean turnOngoing;
    private boolean blocksMovedDown = false; // Add this flag
    private Point nextShootingPosition;

    public GameScreen(int width, int height, ScreenChangeListener listener) {
        super(width, height, listener);

        // set initial shooting position to be in middle of game area
        shootingPosition = new Point(PANEL_WIDTH / 2, GAME_Y + GAME_HEIGHT - 50);
    }

    @Override
    protected void initializeComponents() {
        gameFrame = loadImage("/assets/game/game_frame.png");

        balls = new java.util.ArrayList<>();
        bricks = new Brick[GAME_ROWS][GAME_COLS];

        cannon = new Cannon(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH, PANEL_HEIGHT-40, new Point(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH/2, 0));
        // generate random block sin top two rows
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < GAME_COLS; j++) {
                if(Math.random() > 0.2)
                    continue;
                squareBrick = new SquareBrick(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y + i * SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, 8);
                bricks[i][j] = squareBrick;
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(gameFrame, 0, 0, null);

        for (Ball ball : balls) {
            ball.draw(g);
        }
        for (Brick[] brick : bricks) {
            for (Brick value : brick) {
                if (value != null)
                    value.draw(g);
            }
        }

        cannon.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if all balls are out of bounds
        if (balls.isEmpty()) {
            // Reset shooting position to the last ball's position
            if(nextShootingPosition != null) {
                shootingPosition = nextShootingPosition;
                nextShootingPosition = null;
            }
            if (!blocksMovedDown) {
                moveBlocksDown(); // Move blocks down only once per turn
                blocksMovedDown = true; // Set the flag
            }
            // checkForGameOver();
        } else {
            blocksMovedDown = false; // Reset the flag at the start of each turn
        }

        for (Ball ball : balls) {ball.update();

            // Remove ball if it goes out of bounds

            if (ball.getY() > GAME_Y + GAME_HEIGHT - 40) {
                balls.remove(ball);
                if(nextShootingPosition == null)
                    nextShootingPosition = new Point((int) ball.getX(), (int) shootingPosition.getY());
                break;
            }

//            for (Ball ball2 : balls) {
//                if (ball != ball2 && ball.isCollidingWith(ball2)) {
//                    ball.bounceOff(ball2);
//                    ball2.bounceOff(ball);
//                }
//            }

            // Check for collisions of balls with bricks
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    var value = bricks[i][j];
                    if (value != null && ball.isCollidingWith(value) && !value.isDestroyed()) {
                        value.hit(1);

                        if (value.isDestroyed()) {
                            bricks[i][j] = null;
                        } else {
                            ball.bounceOff();
                        }
                    }
                }
            }
        }

        cannon.update();

        repaint();
    }

    private void spawnBalls(double angle, double velocityMagnitude) {
        turnOngoing = true; // Prevent shooting while balls are being spawned
        int spacing = 5; // spacing between each ball
        new Timer(100, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 10) {
                    int velocityX = (int) (velocityMagnitude * Math.cos(angle - Math.PI / 2));
                    int velocityY = -(int) (velocityMagnitude * Math.sin(angle - Math.PI / 2));

                    Ball newBall = new Ball(shootingPosition.x + count * spacing, shootingPosition.y, 30, velocityX, velocityY, Color.RED, PANEL_WIDTH, PANEL_HEIGHT);
                    balls.add(newBall);
                    count++;
                } else {
                    ((Timer)e.getSource()).stop();
                    if (balls.isEmpty()) {
                        turnOngoing = false; // Ensure turnOngoing is false only after all balls are processed
                    }
                }
            }
        }).start();
    }

    // Check for game over condition
//    private void checkForGameOver() {
//        // if any bricks in last row go back to main menu
//        for (int j = 0; j < GAME_COLS; j++) {
//            if (bricks[GAME_ROWS - 1][j] != null) {
//                screenChangeListener.changeScreen("main-menu");
//                break;
//            }
//        }
//    }

    // Shift all blocks down by one row and generate new blocks in the top row
    private void moveBlocksDown() {
        for (int i = GAME_ROWS - 1; i > 0; i--) {
            System.arraycopy(bricks[i - 1], 0, bricks[i], 0, GAME_COLS);
        }
        for (int j = 0; j < GAME_COLS; j++) {
            if(Math.random() > 0.2)
                continue;
            squareBrick = new SquareBrick(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, 8);
            bricks[0][j] = squareBrick;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Toggle debug mode
        if (e.getKeyChar() == 'd') {
            cannon.toggleDebug();
            for (Ball ball : balls) {
                ball.toggleDebug();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Prevent multiple shots in one turn
        if (turnOngoing) {
            return;
        }
        cannon.setMousePosOnPress(e.getPoint());
        cannon.setCurrentMousePos(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Prevent multiple shots in one turn
        if (turnOngoing) {
            return;
        }

        turnOngoing = true;

        cannon.setMousePosOnRelease(e.getPoint());
        cannon.setCurrentMousePos(e.getPoint());

        cannon.shoot();

        // Spawn ball if cannon has produced velocity
        if(cannon.getProducedVelocityMagnitude() > 0){
            spawnBalls(cannon.getAngle(), cannon.getProducedVelocityMagnitude());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (balls.isEmpty()) {
            turnOngoing = false;
        }
        cannon.setCurrentMousePos(e.getPoint());
    }
}
