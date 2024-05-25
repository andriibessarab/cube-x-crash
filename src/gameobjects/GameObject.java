package gameobjects;

import java.awt.*;

public abstract class GameObject {
    protected int x, y; // Position
    protected int width, height; // Size
    protected boolean isVisible; // Visibility flag

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true;
    }

    public abstract void update(); // Update the object's state

    public abstract void draw(Graphics g); // Draw the object

    // Getters and setters for position, size, and visibility
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public boolean isVisible() { return isVisible; }
    public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
}
