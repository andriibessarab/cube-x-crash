package screencomponents;

import gameobjects.Brick;
import java.awt.*;
import java.util.ArrayList;

public class GameField {
    private final int WIDTH;
    private final int HEIGHT;
    private final int X;
    private final int Y;

    private final int BRICK_WIDTH = 50;
    private final int BRICK_HEIGHT = 20;
//    private final int ROWS = HEIGHT / BRICK_HEIGHT;
//    private final int COLS = WIDTH / BRICK_WIDTH;

    private int ROWS = 5;
    private int COLS = 10;

    private ArrayList<Brick> bricks;

    public GameField(int panelWidth, int panelHeight) {
        WIDTH = (int) (panelWidth * .95);
        HEIGHT = (int) (panelHeight * .87);
        X = (panelWidth - WIDTH) / 2;
        Y = (panelHeight - HEIGHT) / 2 - panelHeight / 30;

        bricks = new ArrayList<>();
        initializeBricks();
    }

    private void initializeBricks() {
//        for (int row = 0; row < ROWS; row++) {
//            for (int col = 0; col < COLS; col++) {
//                int x = X + (col * BRICK_WIDTH);
//                int y = Y + (row * BRICK_HEIGHT);
//                bricks.add(new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT));
//            }
//        }
    }

    public void draw(Graphics g) {
        // draw broder
        g.setColor(Color.BLACK);
        g.drawRect(X, Y, WIDTH, HEIGHT);
        for (Brick brick : bricks) {
            brick.draw(g);
        }
    }
}
