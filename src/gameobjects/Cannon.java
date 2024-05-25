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


//    private Point mousePress;
//    private Point mouseRelease;


    // Constructor for the Cannon class of default width and height
    public Cannon(int x, int y, Point currentMousePos) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.currentMousePos = currentMousePos;

        // Initialize the angle and power
        calculateAngleAndPower();
    }

    // Constructor for the Cannon class of custom width and height
    public Cannon(int x, int y, int width, int height, Point currentMousePos) {
        super(x, y, width, height);

        this.currentMousePos = currentMousePos;

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



//        // Draw the cannon barrel
//        int x1 = 25;
//        int y1 = 25;
//        int x2 = (int) (x1 + 20 * Math.cos(angle));
//        int y2 = (int) (y1 + 20 * Math.sin(angle));
//        g.drawLine(x1, y1, x2, y2);
//
//        // Draw the power indicator
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 50, (int) power, 10);
//
        // Draw the angle indicator
        g.setColor(Color.GREEN);
        g.drawString("Angle: " + Math.toDegrees(angle), 0, 70);
//
//        // Draw the power indicator
//        g.setColor(Color.GREEN);
//        g.drawString("Power: " + power, 0, 90);
//
//        // Draw the mouse press and release points
//        g.setColor(Color.RED);
//        g.fillOval(mousePress.x - 5, mousePress.y - 5, 10, 10);
//        g.fillOval(mouseRelease.x - 5, mouseRelease.y - 5, 10, 10);
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


    // Call this method when the mouse is moved
    public void setCurrentMousePos(Point p) {
        currentMousePos = p;
    }
}
