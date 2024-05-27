package gameobjects.bricks;

import gameobjects.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class Brick extends GameObject {
    protected int maxHealth;
    protected int brickHealth;
    protected boolean isDestroyed = false;
    protected Font customFont;
    public static final Color FUTURISTIC_PURPLE = new Color(153, 0, 46);
    public static final Color FUTURISTIC_BLUE = new Color(0, 0, 153);

    public Brick(int x, int y, int width, int height, int health) {
        super(x, y, width, height);
        maxHealth = health;
        brickHealth = health;

        try {
            // Load the custom font from the file
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/superchargelaser.otf"));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("Error loading font file", e);
        }
    }

    public abstract void draw(java.awt.Graphics g);

    public abstract void update();

    public abstract void reset();

    public boolean isDestroyed() {
        return isDestroyed;
    };

    public void hit(int damage) {
        brickHealth -= damage;
        if (brickHealth <= 0) {
            isDestroyed = true;
        }
    }

    protected float getHealthRatio() {
        return (float) brickHealth / maxHealth; // Assuming maxHealth is the maximum health of the brick
    }

    protected Color blendColors(Color color1, Color color2, float ratio, int alpha) {
        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);
        return new Color(red, green, blue, alpha);
    }

    public void moveDown(int distance) {
        y += distance;
    }

    public int getHealth() {
        return brickHealth;
    }
}
