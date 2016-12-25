package game;

import javax.swing.*;

/**
 * Main class, which initilalizes the Game and starts the main application
 * window.
 *
 * @author Adam Kucera
 */
public class TheGame extends JApplet {

    /**
     * Sets the main game panel.
     *
     * @author Adam Kucera
     */
    public void init() {
        this.setContentPane(GamePanel.getInstance());
    }

    /**
     * Initializes the window of the appliacion.
     *
     * @param args Java command line arguments
     * @author Adam Kucera
     */
    public static void main(String[] args) {
        TheGame applet = new TheGame();
        JFrame window = new JFrame("The Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * window is resizable, but the Game window has all the time same size 
         * (otherwise it would be unfair to other players)
         */
        window.setSize(450, 560);
        window.setContentPane(applet);
        applet.init();
        window.setVisible(true);
    }
}
