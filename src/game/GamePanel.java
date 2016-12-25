package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Main panel of the application - info labels, main game environment and
 * control buttons. Singleton!
 *
 * @author Adam
 */
class GamePanel extends JPanel {

    private BallsPanel bp; //main game environment
    private JLabel score; //text label with score
    private JLabel timeleft; //text label with time left
    private JLabel info; //information text label
    private static GamePanel instance; //instance of this singleton class

    /**
     * Constructor is private, so only one instance will exist - singleton
     * class.
     *
     * @author Adam
     */
    private GamePanel() {
        //create the components of the game
        bp = new BallsPanel();
        /* 
         * main game environment is in the GridBagLayout, so it will
         * be always the same size and always in the middle
         */
        JPanel outer = new JPanel(new GridBagLayout());
        outer.add(bp);

        //buttons creation
        JPanel buttonpanel = new JPanel(new FlowLayout());
        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        JButton restart = new JButton("Restart");

        //labels creation
        score = new JLabel("0 balls hit");
        timeleft = new JLabel("60 seconds");
        info = new JLabel("Press Start button");
        info.setFont(new Font("Serif", Font.PLAIN, 30));

        //add action listeners to the buttons
        start.addActionListener(new Start());
        pause.addActionListener(new Pause());
        restart.addActionListener(new Restart());

        //add buttons to the button panel
        buttonpanel.add(score);
        buttonpanel.add(start);
        buttonpanel.add(pause);
        buttonpanel.add(restart);
        buttonpanel.add(timeleft);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(info);

        //add components to the app
        this.setLayout(new BorderLayout());
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(outer, BorderLayout.CENTER);
        this.add(buttonpanel, BorderLayout.SOUTH);
    }

    /**
     * The instance of the class is received by this static method.
     *
     * @author Adam
     * @return The instance of the singleton class.
     */
    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    /**
     * Returns the label with score.
     *
     * @author Adam
     * @return Score text label.
     */
    public JLabel getScore() {
        return score;
    }

    /**
     * Returns the label with time left.
     *
     * @author Adam
     * @return Time left text label.
     */
    public JLabel getTimeleft() {
        return timeleft;
    }

    /**
     * Returns the label with information.
     *
     * @author Adam
     * @return Information text label.
     */
    public JLabel getInfo() {
        return info;
    }

    /**
     * Inner class, which serves as the Action listener for pause button.
     *
     * @author Adam
     */
    class Pause implements ActionListener {

        /**
         * Pause causes the game animations to pause.
         *
         * @author Adam
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.animate(false);
        }
    }

    /**
     * Inner class, which serves as the Action listener for start button.
     *
     * @author Adam
     */
    class Start implements ActionListener {

        /**
         * Start will start the game or continue after the pause.
         *
         * @author Adam
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.animate(true);
            info.setText("");
            score.setText("0 balls hit");
        }
    }

    /**
     * Inner class, which serves as the Action listener for restart button.
     *
     * @author Adam
     */
    class Restart implements ActionListener {

        /**
         * Restart causes the game to start again.
         *
         * @author Adam
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            bp.restart();
            info.setText("");
            score.setText("0 balls hit");
        }
    }
}
