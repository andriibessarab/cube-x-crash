package gameobjects;

import java.awt.*;

public class Cannon extends GameObject {
    // Define the default width and height of the cannon
    public final static int DEFAULT_WIDTH = 100;
    public final static int DEFAULT_HEIGHT = 100;

    // Define the colors for the cannon
    private final Color BASE_COLOR = new Color(255, 103, 0);
    private final Color BARREL_COLOR = new Color(255, 179, 71);
    private final Color BORDER_COLOR = new Color(166, 68, 3);
    private final Color ACCENT_COLOR = new Color(227, 93, 3);

    // Define the angle and power of the cannon
    private double angle; // Angle in radians
    private double power;

    // Define the current mouse position
    private Point currentMousePos;
    private Point mousePosOnPress;
    private Point mousePosOnRelease;

    // Define the debug mode
    boolean debug = false;

    // Constructor for the Cannon of default width and height
    public Cannon(int x, int y, Point currentMousePos) {
        // Call the parent constructor
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Initialize the mouse position
        this.currentMousePos = currentMousePos;
        this.mousePosOnPress = currentMousePos;
        this.mousePosOnRelease = currentMousePos;

        // Initialize the angle and power
        calculateAngleAndPower();
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
        calculateAngleAndPower();
    }

    @Override
    public void update() {
        // Update the angle and power of the cannon
        calculateAngleAndPower();

//        // Calculate the angle and power based on mousePress and mouseRelease
//        if (mouseRelease != null && mousePress != null) {
//            double deltaX = mouseRelease.x - mousePress.x;
//            double deltaY = mouseRelease.y - mousePress.y;
//            angle = Math.atan2(deltaY, deltaX);
//            power = Math.sqrt(deltaX * deltaX + deltaY * deltaY) / 10; // Adjust the divisor for scaling power
//        }
    }

    @Override
    public void draw(Graphics g) {
        int barrelRelativeWidth = (int)(width * 0.85); // The width of the barrel relative to the width of the base
        int barrelRelativeHeight = (int)(height * 0.9); // The height of the barrel relative to the height of the base

        int barrelRelativeYPos = y - (int)(height * 0.75); // The height of the barrel relative to the height of the cannon
        int barrelRelaticeXPos = x + (width - barrelRelativeWidth) / 2; // The x position of the barrel relative to the width of the cannon

        int startAngleDegrees = Math.max(140, Math.min(220,  (int) Math.toDegrees(angle))); // The rot angle of the barrel

        // Draw the base of the cannon
        g.setColor(BASE_COLOR);
        g.fillArc(x, y, width, height, 0, 180);

        // Draw the border of the base of the cannon
        g.setColor(BORDER_COLOR);
        g.drawArc(x, y, width, height, 0, 180);

        // Draw the barrel of the cannon
        g.setColor(BARREL_COLOR);
        g.fillArc(barrelRelaticeXPos, barrelRelativeYPos, barrelRelativeWidth, barrelRelativeHeight, startAngleDegrees, 180);

        // Draw the border of the barrel of the cannon
        g.setColor(BORDER_COLOR);
        g.drawArc(barrelRelaticeXPos, barrelRelativeYPos, barrelRelativeWidth, barrelRelativeHeight, startAngleDegrees, 180);

        // Draw the debug information
        if (debug) {
            drawDebug(g);
        }
    }

    private void drawDebug(Graphics g) {
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

    private void calculateAngleAndPower() {
        // Calculate  delta x and delta y
        double deltaX = currentMousePos.x - x - (double) width /2;
        double deltaY = currentMousePos.y - y - (double) height /2;

        // Calculate the angle based on the current mouse position
        angle = Math.abs(Math.atan2(deltaY, deltaX)) + + Math.PI/2;
    }

    // Call this method when the mouse is pressed
//    public void setMousePress(Point p) {
//        mousePress = p;
//    }
//
//    // Call this method when the mouse is released
//    public void setMouseRelease(Point p) {
//        mouseRelease = p;
//        update(); // Update the cannon's angle and power
//    }


    public void setCurrentMousePos(Point p) {
        currentMousePos = p;
    }

    public void setMousePosOnPress(Point p) {
        mousePosOnPress = p;
    }

    public void setMousePosOnRelease(Point p) {
        mousePosOnRelease = p;
    }

    public void toggleDebug() {
        this.debug = !this.debug;
    }
}
