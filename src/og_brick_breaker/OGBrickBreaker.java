import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

final class OGBrickBreaker extends JPanel implements ActionListener, KeyListener {
    // Global Timer
    private final Timer timer;

    // Statistics
    private int score = 0;
    private int bricksLeft = 30;

    // Game Objects
    private Ball ball;
    private Paddle paddle;
    private final Brick[] bricks = new Brick[bricksLeft];

    // Game States
    private boolean play = false;
    private boolean mainMenu = true;

    public OGBrickBreaker() {
        timer = new Timer(10, this);

        // Initialize game objects
        gameInit();

        // Set focusable
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Add key listener
        addKeyListener(this);

        // Start the timer
        timer.start();
    }

    public void gameInit() {
        // Reset score
        score = 0;

        // Reset bricks
        bricksLeft = 3;

        // Make sure ball has non-zero dx
        int dx;
        do {
            dx = (int) (Math.random() * 5) - 2;
        } while (dx == 0);

        // Initialize ball
        ball = new Ball(250, 300, dx, 2, 20);

        // Initialize bricks
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                bricks[col + row * 5] = new Brick(11 + col * 96, 40 + row * 32, 95, 30, Color.WHITE, 4);
            }
        }

        // Initialize paddle
        paddle = new Paddle(225, 470, 5, 120, 10, Color.PINK);
        paddle.initKeyStates();
    }


    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.BLACK);
        g.fillRect(5, 5, 490, 495);
        if (mainMenu) {
            g.setColor(Color.WHITE);
            if (System.currentTimeMillis() % 1000 < 500) {
                g.setFont(new Font("Courier", Font.PLAIN, 25));
                g.drawString("brick_breaker", 164, 225);
                g.setFont(new Font("Courier", Font.PLAIN, 15));
                g.drawString("press space to start game", 145, 380);
            }
            return;
        }
        if (ball.isStopped()) {
            g.setColor(Color.WHITE);
            if (System.currentTimeMillis() % 1000 < 500) {
                g.setFont(new Font("Courier", Font.PLAIN, 25));
                g.drawString("Game Over!", 181, 225);
                g.setFont(new Font("Courier", Font.PLAIN, 17));
                g.drawString("Score: " + score, 210, 250);
                g.setFont(new Font("Courier", Font.PLAIN, 15));
                g.drawString("press space to play again", 145, 380);
            }
            return;
        }
        if (play) {
            ball.move(g);
            paddle.draw(g);
            ball.collidesWith(paddle);
            int bricks_count = 0;
            for (Brick brick : bricks) {
                if (brick != null && !brick.isDestroyed()) {
                    bricks_count++;
                    bricksLeft = bricks_count;
                    brick.draw(g);
                    score += ball.collidesWith(brick);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.PLAIN, 20));
            g.drawString("brick_breaker", 10, 30);
            g.setFont(new Font("Courier", Font.PLAIN, 20));
            g.drawString("score: " + (score < 10 ? "00" : score < 100 ? "0" : "") + score, 373, 30);
            paddle.update();
        }
        if (bricksLeft <= 0) {
            g.setColor(Color.WHITE);
            if (System.currentTimeMillis() % 1000 < 500) {
                g.setFont(new Font("Courier", Font.PLAIN, 25));
                g.drawString("Well Done!", 181, 225);
                g.setFont(new Font("Courier", Font.PLAIN, 17));
                g.drawString("Score: " + score, 210, 250);
                g.setFont(new Font("Courier", Font.PLAIN, 15));
                g.drawString("press space to play again", 145, 380);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameInit();
            paint(getGraphics());
            play = true;
            mainMenu = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (mainMenu) {
                System.exit(0);
            }
            mainMenu = true;
            play = false;
            paint(getGraphics());
        }
        paddle.updateKeyState(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Update key states for paddle
        paddle.updateKeyState(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

final class Ball {
    // Position & Motion
    private int x; // top left x val
    private int y; // top left y val
    private int dx;
    private int dy;

    // Properties
    private final Color color;
    private int size; // ball's diameter

    // State
    private boolean isStopped;

    public Ball(int x, int y, int dx, int dy, int size) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.color = Color.ORANGE;
        this.isStopped = false;
    }

    // Move the ball
    public void move(Graphics g) {
        // Bounce off walls
        g.drawImage(new ImageIcon("ball.png").getImage(), x, y, size, size, null);
        if (x < 5 || x > 495 - size) {
            dx = -dx;
        }
        if (y < 5) {
            dy = -dy;
        } else if (y > 495 - size - 15) { // Stop if the ball hits bottom
            dy = 0;
            dx = 0;
            isStopped = true;
        }

        x += dx;
        y += dy;

        draw(g);
    }

    // Collision detection with bricks
    public int collidesWith(Brick obj) {
        if (x < obj.getX() + obj.getWidth() &&
                x + size > obj.getX() &&
                y < obj.getY() + obj.getHeight() &&
                y + size > obj.getY()) {

            // Determine the side of the collision
            double overlapLeft = (x + size) - obj.getX();
            double overlapRight = (obj.getX() + obj.getWidth()) - x;
            double overlapTop = (y + size) - obj.getY();
            double overlapBottom = (obj.getY() + obj.getHeight()) - y;

            // Find the minimum overlap
            double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

            // Bounce ball in the correct direction
            if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                dx *= -1; // Reverse horizontal direction
            } else {
                dy *= -1; // Reverse vertical direction
            }

            y += 5; // Move the ball down a bit to prevent multiple collisions

            // Hit the brick
            obj.hit(1);
            return 1;
        }
        return 0;
    }

    // Collision detection with paddle
    public void collidesWith(Paddle obj) {
        if (x < obj.getX() + obj.getWidth() &&
                x + size > obj.getX() &&
                y < obj.getY() + obj.getHeight() &&
                y + size > obj.getY()) {

            // Determine the side of the collision
            double overlapLeft = (x + size) - obj.getX();
            double overlapRight = (obj.getX() + obj.getWidth()) - x;
            double overlapTop = (y + size) - obj.getY();
            double overlapBottom = (obj.getY() + obj.getHeight()) - y;

            // Find the minimum overlap
            double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

            // Bounce ball in the correct direction
            if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                dx *= -1; // Reverse horizontal direction
            } else {
                dy *= -1; // Reverse vertical direction
            }

            y -= 5; // Move the ball up a bit to prevent it from getting stuck in the paddle
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, size, size);
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getSize() {
        return size;
    }

    public boolean isStopped() {
        return isStopped;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

final class Brick {
    // Position & Motion
    private int x;
    private int y;
    private int width;
    private int height;

    // Durability
    private final int durabilityTotal;
    private int durabilityRemain;

    // Properties
    private Color color;

    // Status
    private boolean isDestroyed;

    public Brick(int x, int y, int width, int height, Color color, int durability) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.durabilityTotal = durability;
        this.durabilityRemain = durability;
        this.isDestroyed = false;
    }

    // Hit the brick
    public void hit(int damage) {
        durabilityRemain -= damage;
        if (durabilityRemain == 0) {
            isDestroyed = true;
        }
    }

    public void draw(Graphics g) {
        // Color goes slowly from green to red as health approaches zero
        int red = (int) (255 * (1 - (double) durabilityRemain / durabilityTotal));
        int green = (int) (255 * (double) durabilityRemain / durabilityTotal);
        color = new Color(red, green, 0);

        // Outline (same color, darker shade)
        g.setColor(color.darker());
        g.drawRect(x - 1, y - 1, width + 1, height + 1);

        // Brick
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Durability counter
        // g.setColor(Color.BLACK);
        // g.drawString(Integer.toString(durabilityRemain), x + width / 2, y + height / 2);
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public int getDurabilityTotal() {
        return durabilityTotal;
    }

    public int getDurabilityRemain() {
        return durabilityRemain;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDurabilityRemain(int durabilityRemain) {
        // Make sure durabilityRemain is within [0, durabilityTotal]
        if (durabilityRemain > durabilityTotal) {
            this.durabilityRemain = durabilityTotal;
        } else this.durabilityRemain = Math.max(durabilityRemain, 0);
    }
}

final class Paddle {
    // Position & Motion
    private int x;
    private int y;

    // Properties
    private final int width;
    private final int height;
    private final int speed;
    private final Color color;

    // Key states
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public Paddle(int x, int y, int speed, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // Move the paddle left
    public void moveLeft() {
        x -= speed;
        if (x < 10)
            x = 10;
    }

    // Move the paddle right
    public void moveRight() {
        x += speed;
        if (x > 370)
            x = 370;
    }

    // Initialize key states
    public void initKeyStates() {
        leftPressed = false;
        rightPressed = false;
    }

    // Update the key states based on key events
    public void updateKeyState(int keyCode, boolean pressed) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = pressed;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = pressed;
        }
    }

    // Update method to be called in the game loop
    public void update() {
        if (leftPressed) {
            moveLeft();
        }
        if (rightPressed) {
            moveRight();
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
