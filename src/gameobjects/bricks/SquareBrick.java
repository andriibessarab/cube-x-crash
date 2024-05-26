package gameobjects.bricks;

import java.awt.*;

public class SquareBrick extends Brick {
    private boolean destroyed = false;

    public SquareBrick(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }


    @Override
    public void draw(java.awt.Graphics g) {
        g.setColor(java.awt.Color.RED);
        g.fillRect(x, y, width, height);

        // draw brick'shealth
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(brickHealth), x + width / 2, y + height / 2);

        // draw brick's border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
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
