package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>GameWindowManager</h2>
 * The `GameWindowManager` class manages the configuration of the game window.
 * It implements the `Configurator` interface to enable it to be configured with JSON data.
 * @see GamePanel
 */
public class GameWindowManager implements Configurator {
    private int ORIGINAL_TILE_SIZE, SCALE, MAX_SCREEN_COLUMNS, MAX_SCREEN_ROWS, MAX_WORLD_COLUMNS, MAX_WORLD_ROWS, FPS;

    /**
     * <h3>GameWindowManager</h3>
     * Constructor which creates a new GameWindowManager.
     */
    public GameWindowManager() { }

    /**
     * <h3>configureJsonObject</h3>
     * Configures the `Application` with a JsonObject by setting it:
     *  <ul>
     *      <li>ORIGINAL_TILE_SIZE</li>
     *      <li>SCALE</li>
     *      <li>MAX_SCREEN_COLUMNS</li>
     *      <li>MAX_SCREEN_ROWS</li>
     *      <li>MAX_WORLD_COLUMNS</li>
     *      <li>MAX_WORLD_ROWS</li>
     *      <li>FPS</li>
     *  </ul>
     *  @param config The JsonArray containing the configuration data.
     *  @see GamePanel
     */
    @Override
    public void configureJsonObject(JsonObject config) {
        this.setORIGINAL_TILE_SIZE(config.get("ORIGINAL_TILE_SIZE").getAsInt());
        this.setSCALE(config.get("SCALE").getAsInt());
        this.setMAX_SCREEN_COLUMNS(config.get("MAX_SCREEN_COLUMNS").getAsInt());
        this.setMAX_SCREEN_ROWS(config.get("MAX_SCREEN_ROWS").getAsInt());
        this.setMAX_WORLD_COLUMNS(config.get("MAX_WORLD_COLUMNS").getAsInt());
        this.setMAX_WORLD_ROWS(config.get("MAX_WORLD_ROWS").getAsInt());
        this.setFPS(config.get("FPS").getAsInt());
    }

    /**
     * <h2>configureJsonArray</h2>
     * Configures the `GameWindow` with a JsonArray - <i>NO USAGE</i>
     * @param config The `JsonArray` containing the configuration data.
     */
    @Override
    public void configureJsonArray(JsonArray config) { }


    // ----- GETTERS -----
    public int getORIGINAL_TILE_SIZE() { return ORIGINAL_TILE_SIZE; }
    public int getSCALE() { return SCALE; }
    public int getMAX_SCREEN_COLUMNS() { return MAX_SCREEN_COLUMNS; }
    public int getMAX_SCREEN_ROWS() { return MAX_SCREEN_ROWS; }
    public int getMAX_WORLD_COLUMNS() { return MAX_WORLD_COLUMNS; }
    public int getMAX_WORLD_ROWS() { return MAX_WORLD_ROWS; }
    public int getFPS() { return FPS; }


    // ----- SETTERS -----
    public void setORIGINAL_TILE_SIZE(int ORIGINAL_TILE_SIZE) { this.ORIGINAL_TILE_SIZE = ORIGINAL_TILE_SIZE; }
    public void setSCALE(int SCALE) { this.SCALE = SCALE; }
    public void setMAX_SCREEN_COLUMNS(int MAX_SCREEN_COLUMNS) { this.MAX_SCREEN_COLUMNS = MAX_SCREEN_COLUMNS; }
    public void setMAX_SCREEN_ROWS(int MAX_SCREEN_ROWS) { this.MAX_SCREEN_ROWS = MAX_SCREEN_ROWS; }
    public void setMAX_WORLD_COLUMNS(int MAX_WORLD_COLUMNS) { this.MAX_WORLD_COLUMNS = MAX_WORLD_COLUMNS; }
    public void setMAX_WORLD_ROWS(int MAX_WORLD_ROWS) { this.MAX_WORLD_ROWS = MAX_WORLD_ROWS; }
    public void setFPS(int FPS) { this.FPS = FPS; }
}
