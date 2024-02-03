package lubiku.castleQuest.Configuration;

import lubiku.castleQuest.Controller.MainController;
import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.Model.Parents.GameObject;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>ConfigCollector</h2>
 * The ConfigCollector class collects and stores various configuration data related to the game, including game window data, map data, hero data, NPC data, object data, and monsters data.
 */
public class ConfigCollector {

    // Game Window Data
    private int ORIGINAL_TILE_SIZE, SCALE, MAX_SCREEN_COLUMNS, MAX_SCREEN_ROWS, MAX_WORLD_COLUMNS, MAX_WORLD_ROWS, FPS;

    // Map Data
    private String MAP_NAME;
    private int[][] MAP_TILES;
    private int[][] MAP;

    // Hero Data
    private String HERO_NAME;
    private int HERO_X_POSITION, HERO_Y_POSITION, HERO_SPEED, HERO_MAXIMUM_HEALTH, HERO_CURRENT_HEALTH, HAS_KEY, HAS_SPECIAL_KEY;
    private boolean HAS_MAP, HAS_ENCHANTED_SWORD;

    // NPC Data
    private int NPC_X_POSITION, NPC_Y_POSITION, NPC_SPEED;

    // Objects Data
    private GameObject[] gameObjects;

    // Monsters Data
    private Monster[] monsters;

    /**
     * <h3>ConfigCollector</h3>
     * Creates a new instance of the ConfigCollector class.
     */
    public ConfigCollector() { }

    /**
     * <h3>getCurrentData</h3>
     * Retrieves the current game data and stores it in the ConfigCollector.
     * @param MAIN_CONTROLLER The MainController instance associated with the game.
     * @param GAME_PANEL      The GamePanel instance associated with the game.
     */
    public void getCurrentData(MainController MAIN_CONTROLLER, GamePanel GAME_PANEL) {
        // GameWindow Data
        this.getGameWindowData(MAIN_CONTROLLER);
        // Hero Data
        this.getHeroData(GAME_PANEL);
        // Map Data
        this.getMapData(MAIN_CONTROLLER);
        // NPC Data
        this.getNpcData(GAME_PANEL);
        // Objects Data
        this.getObjectData(MAIN_CONTROLLER);
        // Monsters Data
        this.getMonstersData(MAIN_CONTROLLER);
    }

    private void getGameWindowData(MainController MAIN_CONTROLLER) {
        this.setORIGINAL_TILE_SIZE(MAIN_CONTROLLER.getORIGINAL_TILE_SIZE());
        this.setSCALE(MAIN_CONTROLLER.getSCALE());
        this.setMAX_SCREEN_COLUMNS(MAIN_CONTROLLER.getMAX_SCREEN_COLUMNS());
        this.setMAX_SCREEN_ROWS(MAIN_CONTROLLER.getMAX_SCREEN_ROWS());
        this.setMAX_WORLD_COLUMNS(MAIN_CONTROLLER.getMAX_WORLD_COLUMNS());
        this.setMAX_WORLD_ROWS(MAIN_CONTROLLER.getMAX_WORLD_ROWS());
        this.setFPS(60);
    }

    private void getMapData(MainController MAIN_CONTROLLER) {
        this.setMAP_NAME(MAIN_CONTROLLER.getMAP_NAME());
        this.setMAP_TILES(MAIN_CONTROLLER.getMAIN_MANAGER().getTILE_MANAGER().getMapTileNumbers());
    }

    private void getHeroData(GamePanel GAME_PANEL) {
        this.setHERO_NAME(GAME_PANEL.getHero().getName());
        this.setHERO_X_POSITION(GAME_PANEL.getHero().getENTITY_X_POSITION());
        this.setHERO_Y_POSITION(GAME_PANEL.getHero().getENTITY_Y_POSITION());
        this.setHERO_SPEED(GAME_PANEL.getHero().getENTITY_SPEED());
        this.setHERO_MAXIMUM_HEALTH(GAME_PANEL.getHero().getMaximumHealth());
        this.setHERO_CURRENT_HEALTH(GAME_PANEL.getHero().getHealth());
        this.setHAS_KEY(GAME_PANEL.getHero().getHasKey());
        this.setHAS_SPECIAL_KEY(GAME_PANEL.getHero().getHasSpecialKey());
        this.setHAS_MAP(GAME_PANEL.getHero().isHasMap());
        this.setHAS_ENCHANTED_SWORD(GAME_PANEL.getHero().isHasEnchantedSword());
    }

    private void getNpcData(GamePanel GAME_PANEL) {
        this.setNPC_X_POSITION(GAME_PANEL.getNpc().getENTITY_X_POSITION());
        this.setNPC_Y_POSITION(GAME_PANEL.getNpc().getENTITY_Y_POSITION());
        this.setNPC_SPEED(GAME_PANEL.getNpc().getENTITY_SPEED());
    }

    private void getObjectData(MainController MAIN_CONTROLLER) { this.setObjects(MAIN_CONTROLLER.getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()); }

    private void getMonstersData(MainController MAIN_CONTROLLER) { this.setMonsters(MAIN_CONTROLLER.getMAIN_MANAGER().getMONSTER_MANAGER().getMonsters()); }

    private int[][] transposeArray(int[][] array) {
        int rows = array.length;
        int columns = array[0].length;

        int[][] transposedArray = new int[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposedArray[j][i] = array[i][j];
            }
        }

        return transposedArray;
    }

    // ----- SETTERS -----

    // GameWindow
    public void setORIGINAL_TILE_SIZE(int ORIGINAL_TILE_SIZE) { this.ORIGINAL_TILE_SIZE = ORIGINAL_TILE_SIZE; }
    public void setSCALE(int SCALE) { this.SCALE = SCALE; }
    public void setMAX_SCREEN_COLUMNS(int MAX_SCREEN_COLUMNS) { this.MAX_SCREEN_COLUMNS = MAX_SCREEN_COLUMNS; }
    public void setMAX_SCREEN_ROWS(int MAX_SCREEN_ROWS) { this.MAX_SCREEN_ROWS = MAX_SCREEN_ROWS; }
    public void setMAX_WORLD_COLUMNS(int MAX_WORLD_COLUMNS) { this.MAX_WORLD_COLUMNS = MAX_WORLD_COLUMNS; }
    public void setMAX_WORLD_ROWS(int MAX_WORLD_ROWS) { this.MAX_WORLD_ROWS = MAX_WORLD_ROWS; }
    public void setFPS(int FPS) { this.FPS = FPS; }

    // Map
    public void setMAP_NAME(String MAP_NAME) { this.MAP_NAME = MAP_NAME; }
    public void setMAP_TILES(int[][] MAP_TILES) { this.MAP_TILES = MAP_TILES; }

    // Hero
    public void setHERO_NAME(String HERO_NAME) { this.HERO_NAME = HERO_NAME; }
    public void setHERO_X_POSITION(int HERO_X_POSITION) { this.HERO_X_POSITION = HERO_X_POSITION; }
    public void setHERO_Y_POSITION(int HERO_Y_POSITION) { this.HERO_Y_POSITION = HERO_Y_POSITION; }
    public void setHERO_SPEED(int HERO_SPEED) { this.HERO_SPEED = HERO_SPEED; }
    public void setHERO_MAXIMUM_HEALTH(int HERO_MAXIMUM_HEALTH) { this.HERO_MAXIMUM_HEALTH = HERO_MAXIMUM_HEALTH; }
    public void setHERO_CURRENT_HEALTH(int HERO_CURRENT_HEALTH) { this.HERO_CURRENT_HEALTH = HERO_CURRENT_HEALTH; }
    public void setHAS_KEY(int HAS_KEY) { this.HAS_KEY = HAS_KEY; }
    public void setHAS_SPECIAL_KEY(int HAS_SPECIAL_KEY) { this.HAS_SPECIAL_KEY = HAS_SPECIAL_KEY; }
    public void setHAS_MAP(boolean HAS_MAP) { this.HAS_MAP = HAS_MAP; }
    public void setHAS_ENCHANTED_SWORD(boolean HAS_ENCHANTED_SWORD) { this.HAS_ENCHANTED_SWORD = HAS_ENCHANTED_SWORD; }

    // NPC
    public void setNPC_X_POSITION(int NPC_X_POSITION) { this.NPC_X_POSITION = NPC_X_POSITION; }
    public void setNPC_Y_POSITION(int NPC_Y_POSITION) { this.NPC_Y_POSITION = NPC_Y_POSITION; }
    public void setNPC_SPEED(int NPC_SPEED) { this.NPC_SPEED = NPC_SPEED; }

    // Objects
    public void setObjects(GameObject[] gameObjects) { this.gameObjects = gameObjects; }

    // Monsters
    public void setMonsters(Monster[] monsters) { this.monsters = monsters; }


    // ----- GETTERS -----

    // GameWindow
    public int getORIGINAL_TILE_SIZE() { return ORIGINAL_TILE_SIZE; }
    public int getSCALE() { return SCALE; }
    public int getMAX_SCREEN_COLUMNS() { return MAX_SCREEN_COLUMNS; }
    public int getMAX_SCREEN_ROWS() { return MAX_SCREEN_ROWS; }
    public int getMAX_WORLD_COLUMNS() { return MAX_WORLD_COLUMNS; }
    public int getMAX_WORLD_ROWS() { return MAX_WORLD_ROWS; }
    public int getFPS() { return FPS; }

    // Map
    public String getMAP_NAME() { return MAP_NAME; }
    public int[][] getMAP_TILES() { return MAP_TILES; }

    // Hero
    public String getHERO_NAME() { return HERO_NAME; }
    public int getHERO_X_POSITION() { return HERO_X_POSITION; }
    public int getHERO_Y_POSITION() { return HERO_Y_POSITION; }
    public int getHERO_SPEED() { return HERO_SPEED; }
    public int getHERO_MAXIMUM_HEALTH() { return HERO_MAXIMUM_HEALTH; }
    public int getHERO_CURRENT_HEALTH() { return HERO_CURRENT_HEALTH; }
    public int getHAS_KEY() { return HAS_KEY; }
    public int getHAS_SPECIAL_KEY() { return HAS_SPECIAL_KEY; }
    public boolean isHAS_MAP() { return HAS_MAP; }
    public boolean isHAS_ENCHANTED_SWORD() { return HAS_ENCHANTED_SWORD; }

    // NPC
    public int getNPC_X_POSITION() { return NPC_X_POSITION; }
    public int getNPC_Y_POSITION() { return NPC_Y_POSITION; }
    public int getNPC_SPEED() { return NPC_SPEED; }

    // Objects
    public GameObject[] getObjects() { return gameObjects; }

    // Monsters
    public Monster[] getMonsters() { return monsters; }
}
