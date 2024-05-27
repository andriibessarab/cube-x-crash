package gameobjects.bricks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SquareBrick extends Brick {
    private boolean destroyed = false;

    private final BufferedImage brickImage;

    public SquareBrick(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);

        brickImage = loadImage("/assets/game/square_brick.png");
    }

    @Override
    public void draw(Graphics g) {
        // Calculate the health ratio
        float healthRatio = getHealthRatio();

        // Blend the colors based on the health ratio
        Color shadeColor = blendColors(FUTURISTIC_PURPLE, FUTURISTIC_BLUE, healthRatio, 80);

        // Draw the brick image (optional, if you want the image to overlay the color)
        g.drawImage(brickImage, x, y, width, height, null);

        // Set the color for the graphics context
        g.setColor(shadeColor);

        // Fill the brick with the blended color
        g.fillRect(x, y, width, height);

        // Write the health of the brick on the brick
        g.setColor(Color.WHITE);
        g.setFont(customFont.deriveFont(20f));
        g.drawString(Integer.toString(brickHealth), x+8, y+23);
    }

    @Override
    protected void drawDebug(Graphics g) {

    }

    @Override
    public void update() {
        // No need to update anything for a square brick
    }

    @Override
    public void reset() {
        destroyed = false;
    }
}
