package gameobjects.bricks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TriangleBrick extends Brick {
    private boolean destroyed = false;

    private final BufferedImage brickImage;

    public TriangleBrick(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);

        brickImage = loadImage("/assets/game/triangle_brick_1.png");
    }


    @Override
    public void draw(java.awt.Graphics g) {
        int[] xPoints = {x, x + width, x + width};
        int[] yPoints = {y, y, y + height};

        // Calculate the health ratio
        float healthRatio = getHealthRatio();

        // Blend the colors based on the health ratio
        Color shadeColor = blendColors(Color.WHITE, Color.BLACK, healthRatio, 80);

        // Draw the brick image
        g.drawImage(brickImage, x, y, width, height, null);

        // Draw brick's border
        g.setColor(shadeColor);
        g.fillPolygon(xPoints, yPoints, 3);

        // Write the health of the brick on the brick
        g.setColor(Color.WHITE);
        g.setFont(customFont.deriveFont(20f));
        g.drawString(Integer.toString(brickHealth), x+width-38, y+23);
    }

    @Override
    protected void drawDebug(Graphics g) {

    }

    @Override
    public void update() {
        // No need to update anything for a triangle brick
    }

    @Override
    public void reset() {
        destroyed = false;
    }
}
