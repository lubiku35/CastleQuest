package lubiku.castleQuest;

import lubiku.castleQuest.Controller.MainController;
import lubiku.castleQuest.View.Frames.AppFrame;


/**
 * <h2>Main</h2>
 * The Main class serves as the entry point for the CastleQuest application.
 * It initializes and starts the program by creating an instance of the AppFrame class via 'MainController'.
 * @see AppFrame
 * @see MainController
 */
public class Main {
    private static final MainController MAIN_CONTROLLER = new MainController();

    /**
     * <h3>main</h3>
     * Initializes an instance of the `AppFrame` class handled by 'MainController'.
     *
     * @param args the command-line arguments passed to the application (unused)
     * @see AppFrame
     * @see MainController
     */
    public static void main(String[] args) { MAIN_CONTROLLER.initializeAppFrame(); }
}
