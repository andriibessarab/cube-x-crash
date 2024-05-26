package screens;

import gameobjects.Ball;
import gameobjects.Cannon;
import gameobjects.bricks.Brick;
import gameobjects.bricks.SquareBrick;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameScreen extends Screen {
    private java.util.List<Ball> balls;
    private java.util.List<Brick> bricks;
    private SquareBrick squareBrick;
    private Cannon cannon;

    public GameScreen(int width, int height, ScreenChangeListener listener) {
        super(width, height, listener);
    }

    @Override
    protected void initializeComponents() {
        balls = new java.util.ArrayList<>();
        bricks = new java.util.ArrayList<>();

        cannon = new Cannon(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH, PANEL_HEIGHT-40, new Point(PANEL_WIDTH / 2 - Cannon.DEFAULT_WIDTH/2, 0));
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * PANEL_WIDTH);
            int y = (int) (Math.random() * PANEL_HEIGHT);
            int health = (int) (Math.random() * 5) + 1;
            bricks.add(new SquareBrick(x, y, 50, 50, health));
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
        for (Ball ball : balls) {
            ball.draw(g);
        }
        for (Brick brick : bricks) {
            if (!brick.isDestroyed())
                brick.draw(g);
        }

        cannon.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (ballSpawnDelay > 0) {
//            spawnBall(3, (int) (Math.PI*0.5));
//            ballSpawnDelay--;
//        }
        for (Ball ball : balls) {
            ball.update();

            for (Brick brick : bricks) {
                if (ball.isCollidingWith(brick) && !brick.isDestroyed()) {
                    brick.hit(1);

                    // Bounce off the brick if it's not destroyed
                    if(!brick.isDestroyed())
                        ball.bounceOff();
                }
            }
        }
        cannon.update();
        repaint();
    }

    private void spawnBall(double angle, double velocityMagnitude) {
        int velocityX = (int) (velocityMagnitude * Math.cos(angle-Math.PI/2));
        int velocityY = -(int) (velocityMagnitude * Math.sin(angle-Math.PI/2));

        // Assuming Ball constructor takes the initial x, y position and direction
        Ball newBall = new Ball(400, 300, 30, velocityX, velocityY, Color.RED, PANEL_WIDTH, PANEL_HEIGHT);
        balls.add(newBall);
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
        cannon.setMousePosOnPress(e.getPoint());
        cannon.setCurrentMousePos(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cannon.setMousePosOnRelease(e.getPoint());
        cannon.setCurrentMousePos(e.getPoint());

        cannon.shoot();

        // Spawn ball if cannon has produced velocity
        if(cannon.getProducedVelocityMagnitude() > 0){
            spawnBall(cannon.getAngle(), cannon.getProducedVelocityMagnitude());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cannon.setCurrentMousePos(e.getPoint());
    }
}
