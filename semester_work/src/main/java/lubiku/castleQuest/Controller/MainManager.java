package lubiku.castleQuest.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Controller.Managers.*;
import lubiku.castleQuest.Configuration.Configurator;
import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.Model.NPC.NPC;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>MainManager</h2>
 * The MainController class is responsible for managing the overall flow and configuration of the game.
 * It initializes the necessary components, handles game setup, and manages interactions between different managers and handlers.
 */
public class MainManager {

    // GameWindow
    private GamePanel GAME_PANEL;

    // Handlers & Managers
    private ApplicationWindowManager APPLICATION_WINDOW_MANAGER;

    private TileManager TILE_MANAGER;
    private ObjectManager OBJECT_MANAGER;
    private MapManager MAP_MANAGER;
    private MonsterManager MONSTER_MANAGER;
    private NPCManager NPC_MANAGER;
    private EnvironmentManager ENVIRONMENT_MANAGER;

    // Hero
    private Hero hero;

    // NPC
    private NPC npc;

    /**
     * <h3>MainManager</h3>
     * Constructs a MainController instance.
     */
    public MainManager() {}

    /**
     * <h3>initApplicationConfiguration</h3>
     * Initializes the application configuration by creating the application config file and configuring the application window manager.
     * @see ApplicationWindowManager
     */
    public void initApplicationConfiguration(Configurator CONFIGURATOR, String APP_CONFIG_FILE) {
        CONFIGURATOR.loadAppConfig(APP_CONFIG_FILE);
        APPLICATION_WINDOW_MANAGER = new ApplicationWindowManager();
        APPLICATION_WINDOW_MANAGER.configureJsonObject(CONFIGURATOR.getAppWindowConfig());
    }

    /**
     * <h3>initPreGameConfiguration</h3>
     * Initializes the pre-game configuration.
     * It creates a game window manager, configures it with the provided game window configuration,
     * and sets the necessary settings for the main controller based on the game window manager's values.
     * @param MAIN_CONTROLLER The main controller instance.
     * @param GAME_WINDOW_CONFIG The JSON object containing the game window configuration.
     */
    public void initPreGameConfiguration(MainController MAIN_CONTROLLER, JsonObject GAME_WINDOW_CONFIG) {
        // Initialization and usage of gameWindowManager
        GameWindowManager GAME_WINDOW_MANAGER = new GameWindowManager();
        GAME_WINDOW_MANAGER.configureJsonObject(GAME_WINDOW_CONFIG);

        // Settings for MAIN_CONTROLLER
        MAIN_CONTROLLER.setORIGINAL_TILE_SIZE(GAME_WINDOW_MANAGER.getORIGINAL_TILE_SIZE());
        MAIN_CONTROLLER.setSCALE(GAME_WINDOW_MANAGER.getSCALE());
        MAIN_CONTROLLER.setMAX_SCREEN_COLUMNS(GAME_WINDOW_MANAGER.getMAX_SCREEN_COLUMNS());
        MAIN_CONTROLLER.setMAX_SCREEN_ROWS(GAME_WINDOW_MANAGER.getMAX_SCREEN_ROWS());
        MAIN_CONTROLLER.setMAX_WORLD_COLUMNS(GAME_WINDOW_MANAGER.getMAX_WORLD_COLUMNS());
        MAIN_CONTROLLER.setMAX_WORLD_ROWS(GAME_WINDOW_MANAGER.getMAX_WORLD_ROWS());
        MAIN_CONTROLLER.setFPS(GAME_WINDOW_MANAGER.getFPS());
    }

    /**
     * <h3>initGameConfiguration</h3>
     * Initializes the game configuration by setting up various managers and handlers, creating game config files,
     * and configuring game window, map, objects, hero, NPC, and enemies.
     */
    public void initGameConfiguration(MainController MAIN_CONTROLLER, GamePanel GAME_PANEL, Configurator CONFIGURATOR, String PLAYER_NAME) {
        this.GAME_PANEL = GAME_PANEL;

        this.setUpGameEngineManagers();

        // Environment Configuration
        this.ENVIRONMENT_MANAGER.setUpLighting();

        // Map Manager Configuration
        this.setUpMap(CONFIGURATOR.getMapConfig());
        MAIN_CONTROLLER.getLOGGER().log("Map setUp complete");

        // Tile Manager Configuration
        this.setUpTiles();
        MAIN_CONTROLLER.getLOGGER().log("Tiles setUp complete");

        // Object Setter Configuration
        this.setUpObjects(CONFIGURATOR.getObjectsConfig());
        MAIN_CONTROLLER.getLOGGER().log("Objects setUp complete");

        // Hero Configuration
        this.GAME_PANEL.setHero(new Hero(this.GAME_PANEL, this.GAME_PANEL.getKEY_HANDLER()));
        this.setHero(this.GAME_PANEL.getHero());
        this.getHero().setName(PLAYER_NAME);
        this.setUpHero(this.getHero(), CONFIGURATOR.getHeroConfig());
        MAIN_CONTROLLER.getLOGGER().log("Hero setUp complete");

        // NPC Configuration
        this.setNpc(this.setUpNPC(new NPC(this.GAME_PANEL), NPC_MANAGER, CONFIGURATOR.getNpcConfig()));
        this.GAME_PANEL.setNpc(this.npc);
        MAIN_CONTROLLER.getLOGGER().log("NPC setUp complete");

        // Enemies Configuration
        this.setUpEnemies(CONFIGURATOR.getEnemiesConfig());
        MAIN_CONTROLLER.getLOGGER().log("Enemies setUp complete");
    }

    /**
     * <h3>restart</h3>
     * Restarts the game with the specified game panel and configurator.
     * It updates the game panel, sets up the hero based on the hero configuration,
     * resets the hero's key and special key status.
     * It configures the environment lighting, map manager, tile manager, object setter,
     * and sets up enemies and NPCs based on their respective configurations.
     * Finally, it sets the NPC for the game panel.
     * @param GAME_PANEL The game panel to restart.
     * @param CONFIGURATOR The configurator containing the game configurations.
     */
    public void restart(GamePanel GAME_PANEL, Configurator CONFIGURATOR) {
        this.GAME_PANEL = GAME_PANEL;

        this.setUpHero(this.GAME_PANEL.getHero(), CONFIGURATOR.getHeroConfig());
        this.GAME_PANEL.getHero().setHasKey(0);
        this.GAME_PANEL.getHero().setHasSpecialKey(0);

        // Environment Configuration
        this.ENVIRONMENT_MANAGER.setUpLighting();

        // Map Manager Configuration
        this.setUpMap(CONFIGURATOR.getMapConfig());

        // Tile Manager Configuration
        this.setUpTiles();

        // Object Setter Configuration
        this.setUpObjects(CONFIGURATOR.getObjectsConfig());

        // Enemies Configuration
        this.setUpEnemies(CONFIGURATOR.getEnemiesConfig());

        this.setNpc(this.setUpNPC(new NPC(this.GAME_PANEL), NPC_MANAGER, CONFIGURATOR.getNpcConfig()));
        this.GAME_PANEL.setNpc(this.npc);
    }

    /**
     * <h3>setUpGameEngineManagers</h3>
     * Sets up the game engine managers.
     * It creates instances of the tile manager, object manager, map manager, monster manager, NPC manager, and environment manager.
     */
    private void setUpGameEngineManagers() {
        this.setTILE_MANAGER(new TileManager(GAME_PANEL));
        this.setOBJECT_MANAGER(new ObjectManager());
        this.setMAP_MANAGER(new MapManager());
        this.setMONSTER_MANAGER(new MonsterManager(GAME_PANEL));
        this.setNPC_MANAGER(new NPCManager(GAME_PANEL));
        this.setENVIRONMENT_MANAGER(new EnvironmentManager(GAME_PANEL));
    }

    /**
     * <h3>setUpHero</h3>
     * Sets up the configuration for the hero.
     * @param hero       The Hero instance to be set up.
     * @param heroConfig The JsonObject containing the hero's configuration.
     * @see HeroManager
     */
    private void setUpHero(Hero hero, JsonObject heroConfig) {
        hero.getHeroManager().setHero(hero);
        hero.getHeroManager().setTILE_SIZE(GAME_PANEL.getTILE_SIZE());
        hero.getHeroManager().configureJsonObject(heroConfig);
        hero.getHeroManager().constructHeroSprites();
        hero.getHeroManager().constructHeroAttackingSprites();
    }

    /**
     * <h3>setUpNPC</h3>
     * Sets up the configuration for the NPC.
     *
     * @param npc       The NPC instance to be set up.
     * @param npcConfig The JsonObject containing the NPC's configuration.
     * @return The initialized NPC.
     * @see NPCManager
     */
    private NPC setUpNPC(NPC npc, NPCManager NPC_MANAGER, JsonObject npcConfig) {
        npc.setNPC_MANAGER(NPC_MANAGER);
        npc.getNPC_MANAGER().setTILE_SIZE(GAME_PANEL.getTILE_SIZE());
        npc.getNPC_MANAGER().setNpc(npc);
        npc.getNPC_MANAGER().configureJsonObject(npcConfig);
        npc.getNPC_MANAGER().constructNPCSprites();
        return npc;
    }

    /**
     * <h3>setUpObjects</h3>
     * Sets up the configuration for the objects.
     * @param objectsConfig The JsonArray containing the objects' configuration.
     * @see ObjectManager
     */
    private void setUpObjects(JsonArray objectsConfig) {
        this.OBJECT_MANAGER.setTILE_SIZE(GAME_PANEL.getTILE_SIZE());
        this.OBJECT_MANAGER.configureJsonArray(objectsConfig);
    }

    /**
     * <h3>setUpMap</h3>
     * Sets up the configuration for the map.
     * @param mapConfig The JsonObject containing the map's configuration.
     * @see MapManager
     */
    private void setUpMap(JsonObject mapConfig) { this.MAP_MANAGER.configureJsonObject(mapConfig); }

    /**
     * <h3>setUpEnemies</h3>
     * Sets up the configuration for the enemies.
     * @param enemiesConfig The JsonArray containing the enemies' configuration.
     * @see MonsterManager
     */
    private void setUpEnemies(JsonArray enemiesConfig) {
        this.MONSTER_MANAGER.configureJsonArray(enemiesConfig);
    }

    /**
     * <h3>setUpTiles</h3>
     * Sets up the tiles for the game map.
     * It configures the tile numbers based on the maximum world columns and rows, sets the map tile numbers using the map manager, and creates the tile images.
     * @see TileManager
     */
    private void setUpTiles() {
        this.TILE_MANAGER.configureMapTileNumbers(this.GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_COLUMNS(), this.GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_ROWS());
        this.TILE_MANAGER.setMapTileNumbers(this.MAP_MANAGER.getMapTiles());
        this.TILE_MANAGER.createTileImages();
    }

    // ----- GETTERS -----
    public ApplicationWindowManager getAPPLICATION_WINDOW_MANAGER() { return APPLICATION_WINDOW_MANAGER; }
    public TileManager getTILE_MANAGER() { return TILE_MANAGER; }
    public ObjectManager getOBJECT_MANAGER() {
        return OBJECT_MANAGER;
    }
    public MonsterManager getMONSTER_MANAGER() {
        return MONSTER_MANAGER;
    }
    public EnvironmentManager getENVIRONMENT_MANAGER() { return ENVIRONMENT_MANAGER; }
    public MapManager getMAP_MANAGER() { return MAP_MANAGER; }
    public NPCManager getNPC_MANAGER() { return NPC_MANAGER; }


    // Hero
    public Hero getHero() { return hero; }

    // ----- SETTERS ----
    public void setTILE_MANAGER(TileManager TILE_MANAGER) {
        this.TILE_MANAGER = TILE_MANAGER;
    }
    public void setOBJECT_MANAGER(ObjectManager OBJECT_MANAGER) {
        this.OBJECT_MANAGER = OBJECT_MANAGER;
    }
    public void setMAP_MANAGER(MapManager MAP_MANAGER) {
        this.MAP_MANAGER = MAP_MANAGER;
    }
    public void setMONSTER_MANAGER(MonsterManager MONSTER_MANAGER) {
        this.MONSTER_MANAGER = MONSTER_MANAGER;
    }
    public void setNPC_MANAGER(NPCManager NPC_MANAGER) {
        this.NPC_MANAGER = NPC_MANAGER;
    }
    public void setENVIRONMENT_MANAGER(EnvironmentManager ENVIRONMENT_MANAGER) { this.ENVIRONMENT_MANAGER = ENVIRONMENT_MANAGER; }

    // Hero
    public void setHero(Hero hero) { this.hero = hero; }

    // NPC
    public void setNpc(NPC npc) { this.npc = npc; }

}
