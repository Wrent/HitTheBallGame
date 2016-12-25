package game;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * The main class representing each ball in the game. This class only counts
 * with the movement abstraction, it does not know nothing about the animation
 * itself. It only suplies the function to draw itself. Every ball runs as a
 * standalone thread, so it implements Runnable interface.
 *
 * @author Adam
 */
class Ball implements Runnable {

    private final int R = 30; //size of each ball
    private Color color; //the color of the ball
    private Point position; //coordinates of the ball
    private int x_v; //speed of the ball in x way
    private int y_v; //speed of the ball in y way
    private int rightBound; //right bound of the game environment
    private int bottomBound; //bottom bound of the game environment
    private boolean alive = true; //does the ball still exist?
    private boolean running = true; //is the ball moving?
    private Ellipse2D oval; //2D object representing the ball

    /**
     * Constructor creates the ball.
     *
     * @param color The color of the ball
     * @param position The start position of the ball
     * @param x_v Initial x velocity
     * @param y_v Initial y velocity
     * @author Adam
     */
    public Ball(Color color, Point position, int x_v, int y_v) {
        this.color = color;
        this.position = position;
        this.x_v = x_v;
        this.y_v = y_v;
        this.oval = new Ellipse2D.Double(position.x, position.y, R, R);
    }

    /**
     * Sets the bounds of the game environment (top and left bound are always 0)
     *
     * @param right X coordinate of right bound.
     * @param bottom Y coordinate of bottom bound.
     * @author Adam
     */
    public void setBounds(int right, int bottom) {
        rightBound = right - R;
        bottomBound = bottom - R;
    }

    /**
     * Main movement function, it moves in both ways at given velocity. It is
     * synchronized, so it won't move, when the user is clicking.
     *
     * @author Adam
     */
    synchronized public void move() {
        position.x += x_v;
        position.y += y_v;

        //is the ball still inside the game environment?
        checkBounds();

        oval.setFrame(position.x, position.y, R, R);
        //inform the others, that the position of the ball have changed
        BallsData.getInstance().fire();
    }

    /**
     * Function which draws the ball in the given graphics environent.
     *
     * @author Adam
     */
    void draw(Graphics2D graphics) {
        graphics.fill(oval);
    }

    /**
     * Returns the color of the ball.
     *
     * @author Adam
     * @return The color of the ball.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Checks, if the ball is still in the game environment and if not, corrects
     * the position and turns velocity.
     *
     * @author Adam
     */
    private void checkBounds() {
        if (position.x < 0) {
            position.x = 0;
            x_v = -1 * x_v;
        }
        if (position.y < 0) {
            position.y = 0;
            y_v = -1 * y_v;
        }
        if (position.x > rightBound) {
            position.x = rightBound;
            x_v = -1 * x_v;
        }
        if (position.y > bottomBound) {
            position.y = bottomBound;
            y_v = -1 * y_v;
        }

    }

    /**
     * Sets if the ball should continue with its existence.
     *
     * @param alive Boolean to be or not to be.
     * @author Adam
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Gets the 2D representation of the ball.
     *
     * @return The Ellipse2D object of the ball.
     * @author Adam
     */
    public Ellipse2D getOval() {
        return oval;
    }

    /**
     * Sets if the ball should continue with its movement.
     *
     * @param alive Boolean to move or not to move.
     * @author Adam
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Main method of each ball thread, there is an infinite loop in which the
     * ball performs its movement. To make the movement slower, before every
     * step, it sleeps for 20ms.
     *
     * @author Adam
     */
    @Override
    public void run() {
        //every thread has the same minumum priority
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        //perform the movement until the ball is popeda
        while (alive) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Thread failed to fall asleep!");
            }
            //if the game is paused, the ball does not move.
            if (running) {
                move();
            }
        }
    }
}
