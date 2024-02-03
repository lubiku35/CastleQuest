package lubiku.castleQuest.View;
import lubiku.castleQuest.Configuration.Configurator;
import lubiku.castleQuest.Controller.Handlers.CollisionHandler;
import lubiku.castleQuest.Controller.Handlers.EventHandler;
import lubiku.castleQuest.Controller.Handlers.KeyHandler;
import lubiku.castleQuest.Controller.MainController;
import lubiku.castleQuest.Controller.MainManager;
import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.Model.NPC.NPC;
import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.Model.Parents.GameObject;

import javax.swing.*;
import java.awt.*;

/**
 * <h2>GamePanel</h2>
 * The GamePanel class is a JPanel subclass that represents the main game panel where the gameplay is rendered and updated.
 * */
public class GamePanel extends JPanel implements Runnable {
    private String configName;
    private String mapName;

    // Player Name
    private String playerName;

    // Default Settings
    private int TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT;

    // Logger / Managers / Controllers / Handlers / UI
    private MainManager MAIN_MANAGER;
    private KeyHandler KEY_HANDLER;
    private EventHandler EVENT_HANDLER;
    private CollisionHandler COLLISION_HANDLER;

    // UI
    private UI UI;

    // Creates a thread
    private Thread gameThread;

    // Create a Hero
    private Hero hero;

    // NPC
    private NPC npc;

    // GAME STATES
    private final int titleScreenState = 0;
    private final int normalGameState = 1;
    private final int pausedGameState = 2;
    private final int dialogueGameState = 3;
    private final int characterGameState = 4;
    private final int gameOverGameState = 5;
    private final int gameFinishedGameState = 6;
    private final int mapGameState = 7;

    // Global GameState
    private int gameState = titleScreenState;

    // Main Controller
    private final MainController MAIN_CONTROLLER;


    /**
     * <h3>GamePanel</h3>
     * Constructs a GamePanel object with the specified parameters.
     * @param MAIN_CONTROLLER The main controller instance.
     * @param CONFIGURATOR The configurator instance.
     * @param PLAYER_NAME The name of the player.
     * @see MainController
     * @see Configurator
     */
    public GamePanel(MainController MAIN_CONTROLLER, Configurator CONFIGURATOR, String PLAYER_NAME) {


        this.playerName = PLAYER_NAME;
        this.MAIN_CONTROLLER = MAIN_CONTROLLER;

        this.configureGamePanel();

        this.MAIN_CONTROLLER.connectHandlersToPanel(this);
        this.MAIN_CONTROLLER.getMAIN_MANAGER().initGameConfiguration(
                this.MAIN_CONTROLLER,
                this,
                CONFIGURATOR,
                this.playerName
        );

        // For better rendering performance
        this.setDoubleBuffered(true);
        this.MAIN_CONTROLLER.getLOGGER().log("\nGamePanel DoubleBuffer: " + this.isDoubleBuffered());

        // Allow focus
        this.setFocusable(true);
        this.MAIN_CONTROLLER.getLOGGER().log("GamePanel Focus: " + this.isFocusable());

        this.addKeyListener(this.getKEY_HANDLER());
        this.MAIN_CONTROLLER.getLOGGER().log("KeyListener" + this.getKEY_HANDLER() + "successfully added");
        this.MAIN_CONTROLLER.connectUItoPanel(this);
    }


    /**
     * <h3>configureGamePanel</h3>
     * Configures the game panel dimensions and background color based on the main controller's settings.
     */
    private void configureGamePanel() {
        this.TILE_SIZE = this.MAIN_CONTROLLER.getORIGINAL_TILE_SIZE() * this.MAIN_CONTROLLER.getSCALE();
        this.SCREEN_WIDTH = this.MAIN_CONTROLLER.getMAX_SCREEN_COLUMNS() * this.getTILE_SIZE();
        this.SCREEN_HEIGHT = this.MAIN_CONTROLLER.getMAX_SCREEN_ROWS() * this.getTILE_SIZE();
        this.WORLD_WIDTH = this.MAIN_CONTROLLER.getMAX_WORLD_COLUMNS() * this.getTILE_SIZE();
        this.WORLD_HEIGHT = this.MAIN_CONTROLLER.getMAX_WORLD_COLUMNS() * this.getTILE_SIZE();

        this.setPreferredSize( new Dimension(this.getSCREEN_WIDTH(), this.getSCREEN_HEIGHT()));
        this.setBackground(Color.BLACK);
    }

    /**
     * <h3>run</h3>
     * Runs the game thread by invoking the accumulator method of the main controller.
     * @see MainController
     */
    @Override
    public void run() { this.MAIN_CONTROLLER.runAccumulatorMethod(this, gameThread); }

    /**
     * <h3>initGameThread</h3>
     * Initializes the game thread and starts it.
     * @see Thread
     */
    public void initGameThread() { gameThread = new Thread(this); gameThread.start(); }

    /**
     * <h3>paintComponent</h3>
     * Overrides the paintComponent method to handle custom painting of the game components.
     * @param graphics The Graphics object to paint on.
     * @see JComponent
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Extend Graphics to Graphics2D to provide more sophisticated control
        Graphics2D graphics2D = (Graphics2D) graphics;

        if (this.gameState == this.titleScreenState) {
            // UI
            this.UI.draw(graphics2D);
        } else {
            // TILES
            this.MAIN_MANAGER.getTILE_MANAGER().drawTiles(graphics2D);

            // OBJECTS
            for (GameObject gameObject : this.getObjects()) { if (gameObject != null) { gameObject.draw(graphics2D, this);} }

            // MONSTERS
            for (Monster monster : this.getMonsters()) { if (monster != null) { monster.drawMonster(graphics2D, monster);} }

            // NPC
            this.npc.draw(graphics2D, this);

            // HERO
            this.hero.drawHero(graphics2D);

            // ENVIRONMENT
            this.MAIN_MANAGER.getENVIRONMENT_MANAGER().draw(graphics2D);

            // UI
            this.UI.draw(graphics2D);

            graphics2D.dispose();
        }
    }

    /**
     * <h3>update</h3>
     * Updates the game state by updating the hero, NPC, and monsters.
     * @see Monster
     * @see Hero
     * @see NPC
     */
    public void update() {
        if (this.gameState == this.normalGameState) {
            // HERO UPDATE
            hero.updateHero();

            // NPC UPDATE
            npc.updateNPC();
            // MONSTER UPDATE
            for (int i = 0; i < this.getMonsters().length; i++) {
                if (this.getMonsters()[i] != null) {
                    if (this.getMonsters()[i].isAlive() && !this.getMonsters()[i].isDying()) {
                        this.getMonsters()[i].updateMonster(this.getMonsters()[i]);
                    }
                    if (!this.getMonsters()[i].isAlive()) {
                        this.getMonsters()[i] = null;
                    }
                }
            }
        }
    }

    /**
     * <h3>getMonsters</h3>
     * Retrieves the array of monsters from the main monster manager.
     * @return The array of monsters.
     * @see lubiku.castleQuest.Controller.Managers.MonsterManager
     */
    private Monster[] getMonsters() {
        return this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters();
    }

    /**
     * <h3>getObjects</h3>
     * Retrieves the array of game objects from the main object manager.
     * @return The array of game objects.
     * @see lubiku.castleQuest.Controller.Managers.ObjectManager
     */
    private GameObject[] getObjects() { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects(); }

    // ----- GETTERS -----

    // Logger / Managers / Controllers / Handlers / UI
    public KeyHandler getKEY_HANDLER()  { return KEY_HANDLER; }
    public EventHandler getEVENT_HANDLER() { return EVENT_HANDLER; }
    public CollisionHandler getCOLLISION_HANDLER() { return COLLISION_HANDLER; }
    public MainController getMAIN_CONTROLLER() { return MAIN_CONTROLLER; }

    // GameStates
    public int getGameState() { return gameState; }
    public int getNormalGameState() { return normalGameState; }
    public int getPausedGameState() { return pausedGameState; }
    public int getDialogueGameState() { return dialogueGameState; }
    public int getTitleScreenState() { return titleScreenState; }
    public int getCharacterGameState() { return characterGameState; }
    public int getGameOverGameState() { return gameOverGameState; }
    public int getGameFinishedGameState() { return gameFinishedGameState; }
    public int getMapGameState() { return mapGameState; }

    public int getTILE_SIZE() { return TILE_SIZE; }
    public int getSCREEN_WIDTH() { return SCREEN_WIDTH; }
    public int getSCREEN_HEIGHT() { return SCREEN_HEIGHT; }

    @Override
    public lubiku.castleQuest.View.UI getUI() { return UI; }

    // Hero
    public Hero getHero() { return hero; }
    public NPC getNpc() { return npc; }

    // ----- SETTERS -----
    public void setMAIN_MANAGER(MainManager MAIN_MANAGER) { this.MAIN_MANAGER = MAIN_MANAGER; }
    public void setKEY_HANDLER(KeyHandler KEY_HANDLER) { this.KEY_HANDLER = KEY_HANDLER; }
    public void setEVENT_HANDLER(EventHandler EVENT_HANDLER) { this.EVENT_HANDLER = EVENT_HANDLER; }
    public void setCOLLISION_HANDLER(CollisionHandler COLLISION_HANDLER) { this.COLLISION_HANDLER = COLLISION_HANDLER; }

    public void setUI(lubiku.castleQuest.View.UI UI) { this.UI = UI; }

    // GameState
    public void setGameState(int gameState) { this.gameState = gameState; }

    // GameThread
    public void setGameThread(Thread gameThread) { this.gameThread = gameThread; }

    // HERO
    public void setHero(Hero hero) { this.hero = hero; }

    // NPC
    public void setNpc(NPC npc) { this.npc = npc; }
}
