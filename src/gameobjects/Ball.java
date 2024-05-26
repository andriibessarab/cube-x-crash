package gameobjects;

import gameobjects.bricks.Brick;
import screens.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Ball extends GameObject {
    private int speedX; // Speed of the ball along the X-axis
    private int speedY; // Speed of the ball along the Y-axis
    private Color color; // Color of the ball
    private final int parentWidth; // Width of the game area
    private final int parentHeight; // Height of the game area

    private final ImageIcon img;

    public Ball(int x, int y, int diameter, int speedX, int speedY, Color color, int parentWidth, int parentHeight) {
        super(x, y, diameter, diameter); // Width and height are equal for a ball (diameter)
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;

        img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/game/ball.gif")));
    }

    @Override
    public void update() {
        // Update the ball's position based on its speed
        x += speedX;
        y += speedY;

        System.out.println("Ball x: " + x + " Ball y: " + y);

        // Collision detection with the borders
        if (x <= GameScreen.GAME_X || x >= GameScreen.GAME_X + GameScreen.GAME_WIDTH - width) {
            speedX = -speedX; // Reverse direction on X-axis
        }
        if (y <= GameScreen.GAME_Y || y >= GameScreen.GAME_Y + GameScreen.GAME_HEIGHT - height) {
            speedY = -speedY; // Reverse direction on Y-axis
        }
    }

    public boolean isCollidingWith(Brick o) {
        return x < o.getX() + o.getWidth() &&
                x + width > o.getX() &&
                y < o.getY() + o.getHeight() &&
                y + height > o.getY();
    }

    public boolean isCollidingWith(Ball o) {
        return x < o.getX() + o.getWidth() &&
                x + width > o.getX() &&
                y < o.getY() + o.getHeight() &&
                y + height > o.getY();
    }

    public void bounceOff() {
        speedX = -speedX;
        speedY = -speedY;
    }

//    public void bounceOff(Ball o) {
//        // Swap speeds of the two balls
//        int tempSpeedX = speedX;
//        int tempSpeedY = speedY;
//        speedX = -o.speedX;
//        speedY = -o.speedY;
//        o.speedX = -tempSpeedX;
//        o.speedY = -tempSpeedY;
//    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y, width, height, null);

        // Draw debug information if debug mode is enabled
        if (debug) {
            drawDebug(g);
        }
    }

    @Override
    protected void drawDebug(Graphics g) {
        g.drawString("SpeedX: " + speedX, x + width + 5, y);
        g.drawString("SpeedY: " + speedY, x + width + 5, y + 15);
    }
}
