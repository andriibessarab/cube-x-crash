package gameobjects.bricks;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ExtraBall extends Brick {
    private ImageIcon img;

    public ExtraBall(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
        maxHealth = health;
        brickHealth = health;

        img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/game/extra_ball.gif")));
    }

    @Override
    public void draw(Graphics g) {
        // dra w a square
        g.setColor(Color.RED);

        g.drawImage(img.getImage(), x+(int)(width*0.1), y+(int)(height*0.1), (int)(width*.8), (int)(height*0.8), null);

//
//        g.drawImage(image.getImage(), 0, 0, 200, 200, null);
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    protected void drawDebug(Graphics g) {

    }
}
