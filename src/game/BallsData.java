package game;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton class, which saves the information about all balls and threads
 * together. It is possible to register as a view to this data and show them in
 * programs. Actually, here is only one view registered, but e.g. for debugging
 * purposes, there could be e.g. a table representation of the balls.
 *
 * @author Adam
 */
class BallsData {

    private static BallsData instance; //the instance of the singleton class
    private CopyOnWriteArrayList<Ball> balls; //collection of the balls
    private CopyOnWriteArrayList<View> views; //collection of the views
    private CopyOnWriteArrayList<Thread> threads; //collection of the thrads
    private int score;

    /**
     * Private constructor which allocates the collections. It is private,
     * because the class is the singleton.
     *
     * @author Adam
     */
    private BallsData() {
        balls = new CopyOnWriteArrayList<>();
        views = new CopyOnWriteArrayList<>();
        threads = new CopyOnWriteArrayList<>();
    }

    /**
     * Static method, which is used to get the instance of the BallsData. If the
     * instance does not exist yet, this will create it.
     *
     * @return The instance of the singleton.
     * @author Adam
     */
    public static BallsData getInstance() {
        if (instance == null) {
            instance = new BallsData();
        }
        return instance;
    }

    /**
     * Returns the collection with balls data.
     *
     * @return The collection of balls.
     * @author Adam
     */
    public CopyOnWriteArrayList<Ball> getData() {
        return balls;
    }

    /**
     * Returns the collection with threads.
     *
     * @return The collection of threads.
     * @author Adam
     */
    public CopyOnWriteArrayList<Thread> getThreads() {
        return threads;
    }

    /**
     * Adds the new ball to the collection.
     *
     * @param ball The newly created ball.
     * @author Adam
     */
    public void addBall(Ball ball) {
        balls.add(ball);
        //inform others of data change
        fire();
    }

    /**
     * Remove the ball from the collection.
     *
     * @param ball The ball, which was hit.
     * @author Adam
     */
    public void removeBall(Ball ball) {
        balls.remove(ball);
        //increase score and update the score label
        score++;
        GamePanel.getInstance().getScore().setText(score + " balls hit");
        //inform others of data change
        fire();
    }

    /**
     * Adds the new thread to the collection.
     *
     * @param thread The newly created thread.
     * @author Adam
     */
    public void addThread(Thread thread) {
        threads.add(thread);
    }

    /**
     * Returns the score (integer)
     *
     * @return Returns the score - the number.
     * @author Adam
     */
    public int getScore() {
        return score;
    }

    /**
     * Removes the thread to the collection.
     *
     * @param thread The thread of the ball, which was hit.
     * @author Adam
     */
    public void removeThread(Thread thread) {
        threads.remove(thread);
    }

    /**
     * The method informs all the registered views, that there has been a data
     * change and they should repaint their graphics environments.
     *
     * @author Adam
     */
    public void fire() {
        for (View v : views) {
            v.dataChange();
        }
    }

    /**
     * Registeres a new view for the data.
     *
     * @param view A class implementing the View interface.
     * @author Adam
     */
    public void register(View view) {
        views.add(view);
    }

    /**
     * Clears all the data (for example after the restart of the game)
     *
     * @author Adam
     */
    public void clear() {
        for (Ball ball : balls) {
            ball.setAlive(false);
        }
        threads.clear();
        balls.clear();

        score = 0;
    }
}
