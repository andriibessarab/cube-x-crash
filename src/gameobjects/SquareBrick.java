package gameobjects;

import java.awt.*;

public class SquareBrick extends Brick {
    public SquareBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void hit() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    protected void drawDebug(Graphics g) {

    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setDestroyed(boolean destroyed) {

    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean isCollidingWith(Ball ball) {
        return false;
    }

    @Override
    public void setCollidingWith(Ball ball) {

    }
}
