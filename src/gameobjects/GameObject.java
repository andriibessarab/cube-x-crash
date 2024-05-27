package gameobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class GameObject {
    protected int x, y; // Position
    protected int width, height; // Size
    protected boolean isVisible; // Visibility flag
    protected boolean debug; // Debug mode flag

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true;
        this.debug = false;
    }

    protected BufferedImage loadImage(String url) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource(url)));
        } catch (IOException e) {
            throw new RuntimeException("Error loading image: " + url, e);
        }
    }

    public abstract void update(); // Update the object's state

    public abstract void draw(Graphics g); // Draw the object

    protected abstract void drawDebug(Graphics g); // Draw the object in debug mode

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
    public void toggleDebug() { debug = !debug; }
}
