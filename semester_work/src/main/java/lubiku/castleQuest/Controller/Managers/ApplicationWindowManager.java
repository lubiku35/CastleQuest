package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.View.Frames.AppFrame;

/**
 * <h2>ApplicationWindowManager</h2>
 * The `ApplicationWindowManager` class manages the configuration of the application's window.
 * It implements the `Configurator` interface to enable it to be configured with JSON data.
 * @see Configurator
 */
public class ApplicationWindowManager implements Configurator {
    private String TITLE;
    private int WINDOW_WIDTH, WINDOW_HEIGHT;

    /**
     * <h3>ApplicationWindowManager</h3>
     * Constructor which creates a new ApplicationWindowManager.
     */
    public ApplicationWindowManager() { }

    /**
     * <h3>configureJsonObject</h3>
     * Configures the `Application` with a JsonObject by setting it:
     *  <ul>
     *      <li>TITLE</li>
     *      <li>WINDOW_WIDTH</li>
     *      <li>WINDOW_HEIGHT</li>
     *  </ul>
     *  @param config The JsonArray containing the configuration data.
     *  @see AppFrame
     */
    @Override
    public void configureJsonObject(JsonObject config) {
        this.setTITLE(config.get("TITLE").getAsString());
        this.setWINDOW_WIDTH(config.get("WINDOW_WIDTH").getAsInt());
        this.setWINDOW_HEIGHT(config.get("WINDOW_HEIGHT").getAsInt());
    }

    /**
     * <h3>configureJsonArray</h3>
     * Configures the `Application` with a JsonArray - <i>NO USAGE</i>
     * @param configurator The `JsonArray` containing the configuration data.
     */
    @Override
    public void configureJsonArray(JsonArray configurator) { }


    // ----- GETTERS -----
    public String getTITLE() { return TITLE; }
    public int getWINDOW_WIDTH() { return WINDOW_WIDTH; }
    public int getWINDOW_HEIGHT() { return WINDOW_HEIGHT; }


    // ----- SETTERS -----
    public void setTITLE(String TITLE) { this.TITLE = TITLE;}
    public void setWINDOW_WIDTH(int WINDOW_WIDTH) { this.WINDOW_WIDTH = WINDOW_WIDTH; }
    public void setWINDOW_HEIGHT(int WINDOW_HEIGHT) { this.WINDOW_HEIGHT = WINDOW_HEIGHT; }
}