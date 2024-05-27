package screens;

import file_manager.FileManager;
import gameobjects.Ball;
import gameobjects.Cannon;
import gameobjects.bricks.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameScreen extends Screen {
    public final static int GAME_WIDTH = 1266;
    public final static int GAME_HEIGHT = 632;
    public final static int GAME_X = 169;
    public final static int GAME_Y = 130;

    private final static int GAME_COLS = 16;
    private final static int SQUARE_BLOCK_SIDE =  GAME_WIDTH / GAME_COLS;
    private final static int GAME_ROWS = GAME_HEIGHT / SQUARE_BLOCK_SIDE;

    private final int DEFAULT_NUM_BALLS = 10, DEFAULT_LIKELIHOOD_OF_NEW_BRICK = 20, DEFAULT_AVG_HEALTH_FOR_BRICK = 8;

    private ImageIcon gameFrame;
    private ImageIcon coinIcon;
    private ImageIcon ballImage;
    private final ImageIcon bgImage;

    private java.util.List<Ball> balls;
    private Brick[][] bricks;
    private Cannon cannon;

    private Point shootingPosition;
    private boolean turnOngoing;
    private boolean blocksMovedDown = true; // Add this flag
    private Point nextShootingPosition;
    private int likelihoodOfNewBrick = DEFAULT_LIKELIHOOD_OF_NEW_BRICK;
    private boolean isPaused = false;
    private Font customFont;
    private FileManager fileManager;
    private int currScore = 0, ballCount = DEFAULT_NUM_BALLS;
    private boolean gameIsOver = false;
    private int numCoins, highScore;
    private final Clip brickHitClip, brickBreakClip, extraBallClip;

    private int avgHealthForBrick = DEFAULT_AVG_HEALTH_FOR_BRICK;



    public GameScreen(int width, int height, ScreenChangeListener listener) {
        super(width, height, listener);

        // set initial shooting position to be in middle of game area
        shootingPosition = new Point(PANEL_WIDTH / 2, GAME_Y + GAME_HEIGHT - 50);

        for (int i = 0; i < 3; i++) {
            fillRow(i, likelihoodOfNewBrick);
        }

        try {
            // Load the custom font from the file
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/superchargelaser.otf"));
        } catch (IOException | FontFormatException e) {
            // Handle exception
        }

        bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/main_menu/bg2.gif")));
        ballImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/game/ball.gif")));

        brickHitClip = loadSoundClip("/assets/sounds/brick_hit.wav");
        brickBreakClip = loadSoundClip("/assets/sounds/brick_break.wav");
        extraBallClip = loadSoundClip("/assets/sounds/extra_ball.wav");
    }

    @Override
    protected void initializeComponents() {
        fileManager = new FileManager();

        numCoins = fileManager.getCoins();
        highScore = fileManager.getHighScore();

        gameFrame = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/game/game_frame.png")));
        coinIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/game/coin.webp")));


        balls = new java.util.ArrayList<>();
        bricks = new Brick[GAME_ROWS][GAME_COLS];

        cannon = new Cannon(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH, PANEL_HEIGHT-40, new Point(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH/2, 0));

        repaint();
    }

    @Override
    public void update() {}

    @Override
    public void stop() {}

    public void reset() {
        balls.clear();
        bricks = new Brick[GAME_ROWS][GAME_COLS];
        turnOngoing = false;

        // fill in first three rows
        for (int i = 0; i < 3; i++) {
            fillRow(i, likelihoodOfNewBrick);
        }

        highScore = fileManager.getHighScore();
        currScore = 0;
        ballCount = DEFAULT_NUM_BALLS;
        likelihoodOfNewBrick = DEFAULT_LIKELIHOOD_OF_NEW_BRICK;
        avgHealthForBrick = DEFAULT_AVG_HEALTH_FOR_BRICK;
    }

    // FIll a row with bricks
    private void fillRow(int row, int probability) {
        int filledColumns = 0;
        // Increment the average health every few rows randomly between 1 and 3 rows
        if (row % (new Random().nextInt(3) + 1) == 0) {
            avgHealthForBrick += 1; // Adjust this value to control the rate of difficulty increase
        }

        // Gently increase the likelihood of brick spawning
        likelihoodOfNewBrick = Math.min(likelihoodOfNewBrick + 1, 95); // Ensure it doesn't exceed 95

        for (int j = 0; j < GAME_COLS; j++) {
            if (Math.random() > likelihoodOfNewBrick / 100.0)
                continue;

            // Generate a health value around the average health
            int health = avgHealthForBrick + (new Random().nextInt(3) - 1); // Randomly -1, 0, or +1 from avgHealthForBrick

            double n = Math.random();
            if (n > .4)
                bricks[row][j] = new SquareBrick(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y + row * SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, health);
            else if (n > .08)
                bricks[row][j] = new TriangleBrick(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y + row * SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, health);
            else if (n > .03)
                bricks[row][j] = new ExtraBall(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y + row * SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, 1);
            else
                bricks[row][j] = new Coin(GAME_X + j * SQUARE_BLOCK_SIDE, GAME_Y + row * SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, SQUARE_BLOCK_SIDE, 1);
            filledColumns++;
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage.getImage(), 0, 0, (int) (PANEL_WIDTH*1.2), (int) (PANEL_HEIGHT*1.2), this);

        g.drawImage(gameFrame.getImage(), 0, 0, null);

        for (Ball ball : balls) {
            ball.draw(g);
        }
        for (Brick[] brick : bricks) {
            for (Brick value : brick) {
                if (value != null)
                    value.draw(g);
            }
        }

        // Draw the score
        g.setColor(Color.WHITE);
        g.setFont(customFont.deriveFont(30f));
        g.drawString("Score: " + currScore, 1150, 80);
        g.setFont(customFont.deriveFont(20f));
        g.drawString("High Score: " + highScore, 1150, 104);

        // Draw the ball count
        g.setFont(customFont.deriveFont(30f));
        g.drawImage(ballImage.getImage(), 175, 790, 50, 50, null);
        g.drawString("Balls: " + ballCount, 234, 824);

        // Draw the game-over indicator row
        Color gameOverRowColor = new Color(196, 196, 243, 50); // Blue color with alpha for transparency
        g.setColor(gameOverRowColor);
        g.fillRect(GAME_X, GAME_Y+SQUARE_BLOCK_SIDE*(GAME_ROWS-1),  GAME_WIDTH, SQUARE_BLOCK_SIDE+1);

        // draw a line on top that fades in and out
        g.setColor(new Color(196, 196, 243, (int) (Math.abs(Math.sin(System.currentTimeMillis() / 200.0) * 255))));
        g.drawLine(GAME_X, GAME_Y+SQUARE_BLOCK_SIDE*(GAME_ROWS-1), GAME_X+GAME_WIDTH, GAME_Y+SQUARE_BLOCK_SIDE*(GAME_ROWS-1));

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
            checkForGameOver();
        } else {
            blocksMovedDown = false; // Reset the flag at the start of each turn
        }

        for (Ball ball : balls) {ball.update();

            // Remove ball if it goes out of bounds

            if (ball.getY() > GAME_Y + GAME_HEIGHT - 40) {
                balls.remove(ball);
                // Mak e sure x coordinate is not right in the corner
                int x = (int) ball.getX();
                if(x < GAME_X + 10)
                    x += GAME_X + 10;
                else if(x > GAME_X + GAME_WIDTH - 20)
                    x -= GAME_X + GAME_WIDTH - 20;

                if(nextShootingPosition == null)
                    nextShootingPosition = new Point(x, (int) shootingPosition.getY());
                break;
            }

            // Check for collisions of balls with bricks
            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    var value = bricks[i][j];
                    if (value != null && ball.isCollidingWith(value) && !value.isDestroyed()) {
                        value.hit(1);

                        // Update score
                        currScore += 10;

                        // Update high score
                        if (currScore > highScore) {
                            highScore = currScore;
                        }

                        if (value.isDestroyed()) {
                            // If of class ExtraBall, add a ball
                            if(value instanceof ExtraBall) {
                                ballCount++;
                                extraBallClip.setFramePosition(0);
                                extraBallClip.start();
                            }
                            else {
                                brickBreakClip.setFramePosition(0);
                                brickBreakClip.start();
                            }
                            bricks[i][j] = null;
                        } else {
                            brickHitClip.setFramePosition(0);
                            brickHitClip.start();
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
        new Timer(60, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Set cannon position to the shooting position
                cannon.setX(shootingPosition.x);
                cannon.setY(shootingPosition.y);

                if (count < ballCount) {
                    int velocityX = (int) (velocityMagnitude * Math.cos(angle - Math.PI / 2)) * 3;
                    int velocityY = -(int) (velocityMagnitude * Math.sin(angle - Math.PI / 2)) * 3;

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
    private void checkForGameOver() {
        // if any bricks in last row go back to main menu
        for (int j = 0; j < GAME_COLS; j++) {
            if (bricks[GAME_ROWS - 1][j] != null) {
                if (fileManager.getHighScore() < currScore) {
                    fileManager.saveHighScore(currScore);
                }

                reset();
                screenChangeListener.changeScreen("main-menu");
                break;
            }
        }
    }

    // Shift all blocks down by one row and generate new blocks in the top row
    private void moveBlocksDown() {
        // Move all blocks down by one row in array
        for (int i = GAME_ROWS - 1; i > 0; i--) {
            for (int j = 0; j < GAME_COLS; j++) {
                bricks[i][j] = bricks[i - 1][j];
                bricks[i - 1][j] = null;
                if(bricks[i][j] != null)
                    bricks[i][j].moveDown(SQUARE_BLOCK_SIDE);
            }
        }

        // Generate new blocks in the top row
        fillRow(0, likelihoodOfNewBrick);
    }

    public void togglePause() {
        isPaused = true;
        setOverlay();

    }

    private void setOverlay() {
        // Create a semi-transparent black rectangle to cover the game area
        Graphics g = getGraphics();
        g.setColor(new Color(0, 0, 0, 127)); // Semi-transparent black
        g.fillRect(GAME_X, GAME_Y, GAME_WIDTH, GAME_HEIGHT);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());

        // Toggle debug mode
        if (e.getKeyChar() == KeyEvent.VK_D) {
            cannon.toggleDebug();
            for (Ball ball : balls) {
                ball.toggleDebug();
            }
        }
        if (e.getKeyChar() == KeyEvent.VK_R) {
            reset();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (balls.isEmpty()) {
            cannon.setMousePressed(true);
        }

        // Prevent multiple shots in one turn
        if (turnOngoing) {
            return;
        }
        cannon.setMousePosOnPress(e.getPoint());
        cannon.setCurrentMousePos(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cannon.setMousePressed(false);

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
