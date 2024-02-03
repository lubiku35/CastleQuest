package lubiku.castleQuest.View.Frames;
import lubiku.castleQuest.Controller.MainController;
import lubiku.castleQuest.Controller.MainManager;
import lubiku.castleQuest.View.Panels.ChooseMapPanel;
import lubiku.castleQuest.View.Panels.LoadGamePanel;
import lubiku.castleQuest.View.Panels.StartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * <h2>AppFrame</h2>
 * AppFrame class provides the main application window and handles the basic setup for itself, including creating the start panel and choose map panel.
 * @see JFrame
 */
public class AppFrame extends JFrame {

    // CONTROLLER
    private final MainController MAIN_CONTROLLER;

    /**
     * <h3>AppFrame</h3>
     * Creates a new instance of the AppFrame class, representing the main application window frame.
     * It initializes the application by setting up the application preferences and creating the start panel.
     *
     * @param MAIN_CONTROLLER           The MainController instance associated with the application.
     * @param APPLICATION_TITLE         The title of the application window.
     * @param APPLICATION_WINDOW_WIDTH  The width of the application window.
     * @param APPLICATION_WINDOW_HEIGHT The height of the application window.
     * @see MainManager
     * @see StartPanel
     */
    public AppFrame(MainController MAIN_CONTROLLER, String APPLICATION_TITLE, int APPLICATION_WINDOW_WIDTH, int APPLICATION_WINDOW_HEIGHT) {
        this.MAIN_CONTROLLER = MAIN_CONTROLLER;
        this.setTitle(APPLICATION_TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(APPLICATION_WINDOW_WIDTH, APPLICATION_WINDOW_HEIGHT));
        this.setResizable(false);

        initializeStartPanel();
        this.MAIN_CONTROLLER.getLOGGER().log("Initializing new StartPanel.");
    }

    /**
     * <h3>initializeChooseMapPanel</h3>
     * Initializes the ChooseMapPanel within the AppFrame.
     * This method creates a new ChooseMapPanel and associates it with the provided player name.
     * @param playerName The name of the player for whom the map is being chosen.
     * @see ChooseMapPanel
     */
    public void initializeChooseMapPanel(String playerName) {
        this.MAIN_CONTROLLER.getLOGGER().log("Initializing new ChooseMapPanel with PLAYER_NAME set to: " + playerName + ".");
        ChooseMapPanel CHOOSE_MAP_PANEL = new ChooseMapPanel(this, playerName);
    }

    public void initializeStartPanel() {
        StartPanel START_PANEL = new StartPanel(this);
    }


    // ----- GETTERS -----
    public MainController getMAIN_CONTROLLER() { return MAIN_CONTROLLER; }
}
