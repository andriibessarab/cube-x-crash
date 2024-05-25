package gameobjects;

public abstract class Brick extends GameObject {
    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void hit();

    public abstract boolean isDestroyed();

    public abstract int getScore();

    public abstract void draw(java.awt.Graphics g);

    public abstract void update();

    public abstract void reset();

    public abstract void setDestroyed(boolean destroyed);

    public abstract void setScore(int score);

    public abstract void setX(int x);

    public abstract void setY(int y);

    public abstract void setWidth(int width);

    public abstract void setHeight(int height);

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract boolean isCollidingWith(Ball ball);

    public abstract void setCollidingWith(Ball ball);
}
