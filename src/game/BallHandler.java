package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/**
 * Balls handler represents the time in our game. It checks every second, if
 * there is enough balls, it sometimes creates a special balls, it cares about
 * starting and pausing of the game and it also counts the time until the end.
 *
 * @author Adam
 */
public class BallHandler {

    private final int TIMING = 60000; //how long will one game be
    private Timer timer; //timer ticking every second
    private int size; //the size of the game environment
    private int timeleft = TIMING; //how many time is left before end
    private int interval; //how often will the timer tick

    /**
     * Constructor, which builds the timer.
     *
     * @param interval How often will timer tick
     * @param size How big is the game environment.
     * @author Adam
     */
    public BallHandler(int interval, int size) {
        this.interval = interval;
        timer = new Timer(interval, new Tick());
        this.size = size;
    }

    /**
     * Starts the ticking of timer and movement of balls.
     *
     * @author Adam
     */
    public void start() {
        timer.start();
        for (Ball ball : BallsData.getInstance().getData()) {
            ball.setRunning(true);
        }
    }

    /**
     * Stops the ticking of timer and movement of balls.
     *
     * @author Adam
     */
    public void stop() {
        timer.stop();
        for (Ball ball : BallsData.getInstance().getData()) {
            ball.setRunning(false);
        }
    }

    /**
     * Checks, if there is at least ten balls and if not, creates them to make
     * them 10.
     *
     * @author Adam
     */
    private void checkBalls() {
        while (BallsData.getInstance().getData().size() < 10) {
            createBall();
        }
    }

    /**
     * Function used to create a ball.
     *
     * @author Adam
     */
    private void createBall() {
        Random generator = new Random();
        //generates the ball with random attributes
        Ball ball = new Ball(getColor(generator.nextInt(6)), new Point(generator.nextInt(BallsPanel.DIMENSION), generator.nextInt(BallsPanel.DIMENSION)), generator.nextInt(4) + 1, generator.nextInt(4) + 1);
        ball.setBounds(size, size);
        //inserts the ball to the collection
        BallsData.getInstance().addBall(ball);
        Thread thread = new Thread(ball);
        //starts the movement of the ball
        thread.start();
        BallsData.getInstance().addThread(thread);
    }

    /**
     * This function is used to generate colors from the given ones.
     *
     * @param num The color is generated from the number.
     * @returns A color.
     * @author Adam
     */
    private Color getColor(int num) {
        switch (num) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.ORANGE;
            default:
                return Color.PINK;
        }
    }

    /**
     * Function, which updates the label with time remaining.
     *
     * @author Adam
     */
    private void updateTime() {
        GamePanel.getInstance().getTimeleft().setText(timeleft / 1000 + " seconds");
    }

    /**
     * Restarts the time and the timer.
     *
     * @author Adam
     */
    public void restart() {
        timeleft = TIMING;
        timer.restart();
        updateTime();
    }

    /**
     * Ends the timer and shows the information of the ended game.
     *
     * @author Adam
     */
    private void endGame() {
        timeleft = TIMING;
        timer.stop();
        updateTime();

        GamePanel.getInstance().getInfo().setText("You hit " + BallsData.getInstance().getScore() + " balls!");

        //also clears the data of the previous game
        BallsData.getInstance().clear();
    }

    /**
     * Inner class representing one Tick of the timer.
     *
     * @author Adam
     */
    class Tick implements ActionListener {

        /**
         * This function is started once every second. It checks, if there is
         * enough balls and it sometimes generates an extra ball (once in 10
         * secs). It also cares about the time left info, and it ends the game
         * after one minute.
         *
         * @author Adam
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            checkBalls();
            Random generator = new Random();
            int random = generator.nextInt(100);
            if (random < 10) {
                createBall();
            }

            timeleft -= interval;

            updateTime();
            if (timeleft <= 0) {
                endGame();
            }
        }
    }
}
