package lubiku.castleQuest.View.Frames;

import lubiku.castleQuest.View.GamePanel;

import javax.swing.*;

/**
 * <h2>GameFrame</h2>
 * The GameFrame class represents the game window frame for the CastleQuest game.
 * It is responsible for setting up and displaying the game panel.
 * @see JFrame
 */
public class GameFrame extends JFrame {
    private GamePanel GAME_PANEL;
    /**
     * <h3>GameFrame</h3>
     * Constructs a new instance of the GameFrame class with the specified application title and game panel.
     * @param APPLICATION_TITLE The title of the application window.
     * @param GAME_PANEL       The GamePanel instance to be loaded into the game frame.
     */
    public GameFrame(String APPLICATION_TITLE, GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;

        this.setTitle(APPLICATION_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     * <h3>loadGamePanel</h3>
     * Loads the specified GamePanel into the game frame, adjusts the frame size, centers it on the screen, and makes it visible.
     */
    public void loadGamePanel(boolean isLoaded) {
        if (isLoaded) {
            this.add(this.GAME_PANEL);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.GAME_PANEL.initGameThread();
            this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState());
        } else {
            this.add(this.GAME_PANEL);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.GAME_PANEL.initGameThread();
        }

    }

}
