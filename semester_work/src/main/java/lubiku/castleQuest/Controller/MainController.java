package lubiku.castleQuest.Controller;

import lubiku.castleQuest.Configuration.ConfigCollector;
import lubiku.castleQuest.Configuration.Configurator;
import lubiku.castleQuest.Configuration.JSONConfigWriter;
import lubiku.castleQuest.Controller.Handlers.CollisionHandler;
import lubiku.castleQuest.Logging.Logger;
import lubiku.castleQuest.View.Frames.AppFrame;
import lubiku.castleQuest.Controller.Handlers.EventHandler;
import lubiku.castleQuest.Controller.Handlers.KeyHandler;
import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.Model.NPC.NPC;
import lubiku.castleQuest.View.Frames.GameFrame;
import lubiku.castleQuest.View.UI;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>MainController</h2>
 * The MainController class is responsible for managing the main functionality and flow of the CastleQuest application.
 * It coordinates the setup and initialization of the application's components, including frames, panels, handlers, and managers.
 * The MainController follows the MVC (Model-View-Controller) architectural pattern to separate concerns and enhance maintainability.
 */
public class MainController {

    // Global Configs & Names SetUp
    private static final String APP_CONFIG_FILE = "Config/MainWindow.json";
    private String GAME_CONFIG_FILE, MAP_NAME, PLAYER_NAME;

    // Global Application Settings
    private String APPLICATION_TITLE;
    private int APPLICATION_WINDOW_WIDTH, APPLICATION_WINDOW_HEIGHT;

    // Global Game Settings
    private int ORIGINAL_TILE_SIZE, MAX_SCREEN_COLUMNS, MAX_SCREEN_ROWS, SCALE, FPS, MAX_WORLD_COLUMNS, MAX_WORLD_ROWS;

    // Logger / Managers / Controllers / Handlers / UI
    private final MainManager MAIN_MANAGER = new MainManager();
    private final Configurator CONFIGURATOR = new Configurator(this);
    private static final Logger LOGGER = new Logger();
    private static final UI UI = new UI();

    private CollisionHandler COLLISION_HANDLER;
    private EventHandler EVENT_HANDLER;
    private KeyHandler KEY_HANDLER;


    private GamePanel GAME_PANEL;
    private GameFrame GAME_FRAME;

    public MainController() { LOGGER.log("MainController successfully created."); }


    /**
     * <h3>initializeAppFrame</h3>
     * Initializes and sets up a new application frame.
     * This method creates and configures a new application frame using the stored preferences.
     * */
    public void initializeAppFrame() {
        this.setUpAppFramePreferences();
        // Application Initialization
        AppFrame APP_FRAME = new AppFrame(this, this.APPLICATION_TITLE, this.APPLICATION_WINDOW_WIDTH, this.APPLICATION_WINDOW_HEIGHT);
        APP_FRAME.setVisible(true);
        LOGGER.log(
                "Class AppFrame successfully initialized with attributes: APPLICATION_TITLE: " + this.APPLICATION_TITLE
                + " APPLICATION_WINDOW_WIDTH: " + this.APPLICATION_WINDOW_WIDTH + " APPLICATION_WINDOW_HEIGHT: " +
                this.APPLICATION_WINDOW_HEIGHT
        );
    }

    /**
     * <h3>setUpApplicationPreferences</h3>
     * Sets up the application preferences by retrieving the configuration values from the MainManager - ApplicationWindowManager.
     * The application title, window width, and window height are obtained and stored for later use.
     * @see MainManager
     * @see lubiku.castleQuest.Controller.Managers.ApplicationWindowManager
     */
    private void setUpAppFramePreferences() {
        MAIN_MANAGER.initApplicationConfiguration(CONFIGURATOR, APP_CONFIG_FILE);
        this.APPLICATION_TITLE = MAIN_MANAGER.getAPPLICATION_WINDOW_MANAGER().getTITLE();
        this.APPLICATION_WINDOW_WIDTH = MAIN_MANAGER.getAPPLICATION_WINDOW_MANAGER().getWINDOW_WIDTH();
        this.APPLICATION_WINDOW_HEIGHT = MAIN_MANAGER.getAPPLICATION_WINDOW_MANAGER().getWINDOW_HEIGHT();
    }

    /**
     * <h3>initializeGameFrame</h3>
     * Initializes and sets up a new game frame.
     * This method handles data access, game preferences, and creates the game panel and game frame.
     * It also sets up the UI for the game panel.
     * @see GamePanel
     * @see GameFrame
     * @see UI
     */
    public void initializeGameFrame(boolean isLoaded) {
        // Data Handling
        LOGGER.log("Successfully accessed data in MainController, CONFIG_FILE: " + this.GAME_CONFIG_FILE +
                " MAP_NAME: " + this.MAP_NAME + " PLAYER_NAME: " + this.PLAYER_NAME + ".");
        this.setUpGamePreferences(isLoaded);
        LOGGER.log("Succeed PreGameConfiguration.");

        // Game Panel
        GAME_PANEL = this.createGamePanel(this.PLAYER_NAME);
        LOGGER.log("Game Panel successfully created.");

        // Game Frame
        this.GAME_FRAME = this.createGameFrame(GAME_PANEL);
        this.GAME_FRAME.loadGamePanel(isLoaded);
        LOGGER.log("Game Frame successfully created with loaded Game Panel.");

        GAME_PANEL.getUI().setUpUI(GAME_PANEL);
        LOGGER.log("UI in GamePanel successfully initialized.");
    }

    /**
     * <h3>createGameFrame</h3>
     * Creates a new instance of GameFrame using the specified game panel.
     * @param GAME_PANEL The game panel to be used in the GameFrame.
     * @return A new instance of GameFrame with the specified title and game panel.
     */
    private GameFrame createGameFrame(GamePanel GAME_PANEL) { return new GameFrame(this.APPLICATION_TITLE, GAME_PANEL); }

    /**
     * <h3>setUpGamePreferences</h3>
     * Sets up the game preferences by preparing the game configurations and initializing the pre-game configuration.
     * The game configurations are loaded from the specified game configuration file.
     */
    private void setUpGamePreferences(boolean isLoaded) {
        this.prepareGameConfigs(isLoaded);
        MAIN_MANAGER.initPreGameConfiguration(this , CONFIGURATOR.getGameWindowConfig());
        LOGGER.log("Initializing PreGameConfiguration with file: " + this.GAME_CONFIG_FILE + ".");
    }

    /**
     * <h3>prepareGameConfigs</h3>
     * Prepares the game configurations by loading them from the specified game configuration file.
     * The loaded configurations are used for the game initialization.
     */
    private void prepareGameConfigs(boolean isLoaded) {
        CONFIGURATOR.loadGameConfigs(this, this.GAME_CONFIG_FILE, isLoaded);
        LOGGER.log("Loading game configuration file.");
    }

    /**
     * <h3>createGamePanel</h3>
     * Creates a new instance of GamePanel using the specified parameters.
     * @return A new instance of GamePanel with the MainController, Configurator, and player name.
     */
    private GamePanel createGamePanel(String PLAYER_NAME) { return new GamePanel(this, CONFIGURATOR, PLAYER_NAME); }

    /**
     * <h3>connectHandlersToPanel</h3>
     * The KeyHandler, EventHandler, and CollisionHandler are created and connected to the game panel.
     * The MainManager is set as the main manager of the game panel.
     * @param GAME_PANEL The game panel to connect the handlers and managers to.
     */
    public void connectHandlersToPanel(GamePanel GAME_PANEL) {
        this.KEY_HANDLER = new KeyHandler(GAME_PANEL);
        this.EVENT_HANDLER = new EventHandler(GAME_PANEL);
        this.COLLISION_HANDLER = new CollisionHandler(this.MAIN_MANAGER, GAME_PANEL);

        // Manager
        GAME_PANEL.setMAIN_MANAGER(this.MAIN_MANAGER);

        // Handlers
        GAME_PANEL.setKEY_HANDLER(this.KEY_HANDLER);
        GAME_PANEL.setEVENT_HANDLER(this.EVENT_HANDLER);
        GAME_PANEL.setCOLLISION_HANDLER(this.COLLISION_HANDLER);

        LOGGER.log("\nHandlers / Managers : (MAIN_MANAGER, KEY_HANDLER, EVENT_HANDLER, COLLISION_HANDLER) successfully connected with GAME_PANEL");
    }

    /**
     * <h3>connectUItoPanel</h3>
     * Connects the UI instance to the specified game panel.
     * @param GAME_PANEL The game panel to connect the UI to.
     */
    public void connectUItoPanel(GamePanel GAME_PANEL) {
        UI.setGAME_PANEL(GAME_PANEL);
        GAME_PANEL.setUI(UI);
    }

    /**
     * <h3>runAccumulatorMethod</h3>
     * Runs the accumulator method for managing game loop timing.
     * @param GAME_PANEL The game panel to update and repaint.
     * @param thread     The thread used for the game loop.
     */
    public void runAccumulatorMethod(GamePanel GAME_PANEL, Thread thread) {
        LOGGER.log("\nStarting accumulator method");
        // Accumulator method
        double drawInterval = (double) 1_000_000_000 / this.FPS;
        double accumulator = 0;
        long lastingTime = System.nanoTime();
        long currentTime;
        while (thread != null) {
            currentTime = System.nanoTime();
            accumulator += (currentTime - lastingTime) / drawInterval;
            lastingTime = currentTime;
            if (accumulator >= 1) {
                System.out.println("HER");
                this.performGameLoop(GAME_PANEL);
                accumulator--;
            }
        }
    }

    /**
     * <h3>performGameLoop</h3>
     * Performs the game loop by updating the game panel and triggering a repaint.
     * @param GAME_PANEL The game panel to update and repaint.
     */
    private void performGameLoop(GamePanel GAME_PANEL) { GAME_PANEL.update(); GAME_PANEL.repaint(); }

    /**
     * <h3>restartGame</h3>
     * Restarts the game.
     * This method restarts the game by calling the restart method of the MAIN_MANAGER,
     * setting the game state of GAME_PANEL to 1.
     * @see MainManager
     */
    public void restartGame() {
        this.MAIN_MANAGER.restart(this.GAME_PANEL, CONFIGURATOR);
        this.GAME_PANEL.setGameState(1);
    }

    /**
     * <h3>backToChooseMapPanel</h3>
     * Navigates back to the choose map panel.
     * This method disposes the current GAME_FRAME, sets up the application frame preferences,
     * initializes a new AppFrame, removes all content from its content pane,
     * initializes the choose map panel with the specified PLAYER_NAME,
     * and makes the AppFrame visible.
     *
     * @param PLAYER_NAME the name of the player
     */
    public void backToChooseMapPanel(String PLAYER_NAME) {
        this.GAME_FRAME.dispose();
        this.setUpAppFramePreferences();
        // Application Initialization
        AppFrame APP_FRAME = new AppFrame(this, this.APPLICATION_TITLE, this.APPLICATION_WINDOW_WIDTH, this.APPLICATION_WINDOW_HEIGHT);
        APP_FRAME.getContentPane().removeAll();
        APP_FRAME.initializeChooseMapPanel(PLAYER_NAME);
        APP_FRAME.setVisible(true);
    }

    public void provideSaveGame() {
        JSONConfigWriter jsonConfigWriter = new JSONConfigWriter(this, this.GAME_PANEL);
        jsonConfigWriter.createJsonSaveFile();
    }

    // ----- GETTERS -----

    // Logger
    public Logger getLOGGER() { return LOGGER; }

    // Manager
    public MainManager getMAIN_MANAGER() { return MAIN_MANAGER; }

    // GamePanel Preferences
    public int getORIGINAL_TILE_SIZE() { return ORIGINAL_TILE_SIZE; }
    public int getMAX_SCREEN_COLUMNS() { return MAX_SCREEN_COLUMNS; }
    public int getMAX_SCREEN_ROWS() { return MAX_SCREEN_ROWS; }
    public int getSCALE() { return SCALE; }
    public int getMAX_WORLD_COLUMNS() { return MAX_WORLD_COLUMNS; }
    public int getMAX_WORLD_ROWS() { return MAX_WORLD_ROWS; }

    public CollisionHandler getCOLLISION_HANDLER() { return COLLISION_HANDLER; }

    public String getPLAYER_NAME() { return PLAYER_NAME; }
    public String getMAP_NAME() { return MAP_NAME; }

    // ----- SETTERS -----

    // Global Settings
    public void setORIGINAL_TILE_SIZE(int ORIGINAL_TILE_SIZE) { this.ORIGINAL_TILE_SIZE = ORIGINAL_TILE_SIZE; }
    public void setMAX_SCREEN_COLUMNS(int MAX_SCREEN_COLUMNS) { this.MAX_SCREEN_COLUMNS = MAX_SCREEN_COLUMNS; }
    public void setMAX_SCREEN_ROWS(int MAX_SCREEN_ROWS) { this.MAX_SCREEN_ROWS = MAX_SCREEN_ROWS; }
    public void setMAX_WORLD_COLUMNS(int MAX_WORLD_COLUMNS) { this.MAX_WORLD_COLUMNS = MAX_WORLD_COLUMNS; }
    public void setMAX_WORLD_ROWS(int MAX_WORLD_ROWS) { this.MAX_WORLD_ROWS = MAX_WORLD_ROWS; }
    public void setSCALE(int SCALE) { this.SCALE = SCALE; }
    public void setFPS(int FPS) { this.FPS = FPS; }

    // Global Dependencies
    public void setGAME_CONFIG_FILE(String GAME_CONFIG_FILE) { this.GAME_CONFIG_FILE = GAME_CONFIG_FILE; }
    public void setMAP_NAME(String MAP_NAME) { this.MAP_NAME = MAP_NAME; }
    public void setPLAYER_NAME(String PLAYER_NAME) { this.PLAYER_NAME = PLAYER_NAME; }
}
