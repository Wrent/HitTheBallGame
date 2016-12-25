package game;

/**
 * This interface represents the View of the balls data. If a class wants to
 * view the data of balls, it has to register as a View for it and to register,
 * it has to implement the View interface.
 *
 * @author Adam
 */
public interface View {

    /**
     * This method is called, when BallsData experiences a data change. It
     * means, that the View should repaint its graphics based on the data
     * change.
     *
     * @author Adam
     */
    public void dataChange();
}
