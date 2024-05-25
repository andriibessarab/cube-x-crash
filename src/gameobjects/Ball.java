package gameobjects;

import java.awt.*;

public class Ball extends GameObject {
    private int speedX; // Speed of the ball along the X-axis
    private int speedY; // Speed of the ball along the Y-axis
    private Color color; // Color of the ball
    private final int parentWidth; // Width of the game area
    private final int parentHeight; // Height of the game area

    public Ball(int x, int y, int diameter, int speedX, int speedY, Color color, int parentWidth, int parentHeight) {
        super(x, y, diameter, diameter); // Width and height are equal for a ball (diameter)
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
    }

    @Override
    public void update() {
        // Update the ball's position based on its speed
        x += speedX;
        y += speedY;

        // Collision detection with the borders
        if (x <= 0 || x >= parentWidth - width) { // Assuming 'parentWidth' is the width of the game area
            speedX = -speedX; // Reverse direction on X-axis
        }
        if (y <= 0 || y >= parentHeight - height) { // Assuming 'parentHeight' is the height of the game area
            speedY = -speedY; // Reverse direction on Y-axis
        }
    }

    @Override
    public void draw(Graphics g) {
        // Set the color for the ball
        g.setColor(color);
        // Draw the ball as a filled circle
        g.fillOval(x, y, width, height);

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
