package gameobjects.bricks;

import java.awt.*;

public class SquareBrick extends Brick {
    private boolean destroyed = false;

    private static final Color FUTURISTIC_PURPLE = new Color(102, 0, 153);
    private static final Color FUTURISTIC_BLUE = new Color(0, 0, 153);

    public SquareBrick(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }

    @Override
    public void draw(Graphics g) {
        // Calculate the color based on the brick's health
        Color healthColor = blendColors(FUTURISTIC_BLUE, FUTURISTIC_PURPLE, getHealthRatio());

        g.setColor(healthColor);
        g.fillRect(x, y, width, height);

        // draw brick's health
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(brickHealth), x + width / 2, y + height / 2);

        // draw brick's border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    private float getHealthRatio() {
        return (float) brickHealth / maxHealth; // Assuming maxHealth is the maximum health of the brick
    }

    private Color blendColors(Color color1, Color color2, float ratio) {
        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);
        return new Color(red, green, blue);
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
