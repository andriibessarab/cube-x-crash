package gameobjects.bricks;

import java.awt.*;

public class TriangleBrick extends Brick {
    private boolean destroyed = false;

    public TriangleBrick(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }


    @Override
    public void draw(java.awt.Graphics g) {
        g.setColor(java.awt.Color.RED);
        int[] xPoints = {x, x + width, x + width};
        int[] yPoints = {y, y, y + height};
        g.fillPolygon(xPoints, yPoints, 3);

        // draw brick's health
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(brickHealth), x + width / 2, y + height / 2);

        // draw brick's border
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
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
