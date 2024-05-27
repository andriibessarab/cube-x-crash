package gameobjects;

import gameobjects.bricks.Brick;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Cannon extends GameObject {
    // Define the default width and height of the cannon
    public final static int DEFAULT_WIDTH = 100;
    public final static int DEFAULT_HEIGHT = 100;

    // Define the colors for the cannon
    private final Color BASE_COLOR = new Color(255, 103, 0);
    private final Color BARREL_COLOR = new Color(255, 179, 71);
    private final Color BORDER_COLOR = new Color(166, 68, 3);
    private final Color ACCENT_COLOR = new Color(227, 93, 3);

    // Define the scaling factor(s)
    private final int POWER_SCALING_FACTOR = 20;

    // Define the angle and power of the cannon
    private double angle; // Angle in radians
    private double power;

    // Define the current mouse position
    private Point currentMousePos;
    private Point mousePosOnPress;
    private Point mousePosOnRelease;
    private Point shootingPos;

    private boolean isMousePressed = false;

    private BufferedImage arrow;

    // Constructor for the Cannon of default width and height
    public Cannon(int x, int y, Point currentMousePos, Point shootingPos) {
        // Call the parent constructor
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Initialize the mouse position
        this.currentMousePos = currentMousePos;
        this.mousePosOnPress = currentMousePos;
        this.mousePosOnRelease = currentMousePos;
        this.shootingPos = shootingPos;

        // Initialize the angle and power
        calculateAngle();
    }

    // Constructor for the Cannon of custom width and height
    public Cannon(int x, int y, int width, int height, Point currentMousePos) {
        // Call the parent constructor
        super(x, y, width, height);

        // Initialize the mouse position
        this.currentMousePos = currentMousePos;
        this.mousePosOnPress = currentMousePos;
        this.mousePosOnRelease = currentMousePos;

        // Initialize the angle and power
        calculateAngle();
    }

    @Override
    public void update() {
        // Update the angle and power of the cannon
        calculateAngle();
    }

    // Draw the cannon
    @Override
    public void draw(Graphics g) {
        // Cast Graphics to Graphics2D for more advanced operations
        Graphics2D g2d = (Graphics2D) g;

        if(isMousePressed) {
            calculateAngle();
            calculatePower();

            // draw arrow between press down position and current mouse position
            g2d.setColor(Brick.FUTURISTIC_BLUE);
            if (shootingPos != null && currentMousePos != null)
                drawArrow(g2d, shootingPos.x, shootingPos.y, currentMousePos.x, currentMousePos.y);
        }

        // Draw the debug information
        if (super.debug) {
            drawDebug(g);
        }
    }

    // Helper method to draw an arrow
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g2d.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g2d.drawLine(0, 0, len, 0);

        // Create a gradient from blue to purple
        Color startColor = new Color(0, 0, 255); // Blue
        Color endColor = new Color(128, 0, 128); // Purple
        GradientPaint gp = new GradientPaint(len, -20, startColor, len, 20, endColor, true);
        g2d.setPaint(gp);

        // Increase the arrow leg width by changing the second and third points
        g2d.fillPolygon(new int[] {len, len - 30, len - 30, len},
                new int[] {0, -30, 30, 0}, 4);

        // Reset the transform
        g2d.setTransform(new AffineTransform());
    }

    // Draw the debug information
    @Override
    protected void drawDebug(Graphics g) {
        // Draw the mouse press and release points
        g.setColor(Color.RED);
        g.fillOval(mousePosOnPress.x - 5, mousePosOnPress.y - 5, 10, 10);
        g.drawString("Mouse Press", mousePosOnPress.x + 10, mousePosOnPress.y + 10);

        // Draw the mouse press and release points
        g.setColor(Color.GREEN);
        g.fillOval(mousePosOnRelease.x - 5, mousePosOnRelease.y - 5, 10, 10);
        g.drawString("Mouse Release", mousePosOnRelease.x + 10, mousePosOnRelease.y + 10);

        // Draw the current mouse position
        g.setColor(Color.BLUE);
        g.fillOval(currentMousePos.x - 5, currentMousePos.y - 5, 10, 10);
        g.drawString("Current Mouse", currentMousePos.x + 10, currentMousePos.y + 10);

        // Vector from press to current mouse position (current rotation vector)
        g.setColor(Color.BLACK);
        g.drawLine(mousePosOnPress.x, mousePosOnPress.y, currentMousePos.x, currentMousePos.y);
        g.drawString("dx: " + (currentMousePos.x - mousePosOnPress.x), currentMousePos.x + 10, currentMousePos.y + 30);
        g.drawString("dy: " + (currentMousePos.y - mousePosOnPress.y), currentMousePos.x + 10, currentMousePos.y + 50);

        // Vector from press to release position (final rotation & power vector)
        g.setColor(Color.MAGENTA);
        g.drawLine(mousePosOnPress.x, mousePosOnPress.y, mousePosOnRelease.x, mousePosOnRelease.y);
        g.drawString("dx: " + (mousePosOnRelease.x - mousePosOnPress.x), mousePosOnRelease.x + 10, mousePosOnRelease.y + 30);
        g.drawString("dy: " + (mousePosOnRelease.y - mousePosOnPress.y), mousePosOnRelease.x + 10, mousePosOnRelease.y + 50);

        // Vector from center of cannon to release position (shoot vector)
        g.setColor(Color.GREEN);
        g.drawLine(x + width / 2, y + height / 2, mousePosOnRelease.x, mousePosOnRelease.y);
        g.drawString("dx: " + (mousePosOnRelease.x - x - width / 2), mousePosOnRelease.x + 10, mousePosOnRelease.y + 80);
        g.drawString("dy: " + (mousePosOnRelease.y - y - height / 2), mousePosOnRelease.x + 10, mousePosOnRelease.y + 100);
    }

    // Shoot the cannon
    public void shoot() {
        calculateAngle();
        calculatePower();
    }

    // Calculate the angle of the cannon
    private void calculateAngle() {
        if (currentMousePos == null || shootingPos == null) return;

        double deltaX = currentMousePos.x - shootingPos.x;
        double deltaY = -(currentMousePos.y - shootingPos.y);

        angle = Math.atan2(deltaY, deltaX); // Angle in radians

        // Make sure angle is between 30 and 150 degrees
        if (angle < Math.toRadians(30)) angle = Math.toRadians(30);
        if (angle > Math.toRadians(150)) angle = Math.toRadians(150);
    }


    // Calculate the power of the cannon
    private void calculatePower() {
        if (mousePosOnPress == null || mousePosOnRelease == null) return;

        // Calculate  delta x and delta y
        double deltaX = currentMousePos.x - shootingPos.x;
        double deltaY = -(currentMousePos.y - shootingPos.y);

        // Calculate the power based on the distance between the mouse press and release positions
        power = Math.sqrt(deltaX * deltaX + deltaY * deltaY) / POWER_SCALING_FACTOR; // Adjust the divisor for scaling power

        if(power > 11) power = 15;
    }

    public double getAngle() {
        return angle;
    }

    public double getProducedVelocityMagnitude() {
        return power;
    }

    public void setCurrentMousePos(Point p) {
        currentMousePos = p;
    }

    public void setMousePosOnPress(Point p) {
        mousePosOnPress = p;
    }

    public void setMousePosOnRelease(Point p) {
        mousePosOnRelease = p;
    }

    public void setMousePressed(boolean state) {
        isMousePressed = state;
    }

    public void setShootingPos(Point p) {
        shootingPos = p;
    }

    public double getPower() {
        calculatePower();
        return power;
    }
}
