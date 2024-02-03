package lubiku.castleQuest.Controller.Managers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
/**
 * <h2>MapManager</h2>
 * The MapManager class is responsible for managing and configuring the game map.
 * It implements the Configurator interface for JSON-based configuration.
 */
public class MapManager implements Configurator {
    private JsonArray jsonMapTiles;
    private int[][] mapTiles;

    /**
     * <h3>MapManager</h3>
     * Constructor which creates a new MapManager.
     */
    public MapManager() { }

    /**
     * <h3>configureJsonObject</h3>
     * Configures the map using the provided JSON object.
     * Extracts the map tiles data and populates the mapTiles array accordingly.
     * @param config The JSON object containing the map configuration.
     */
    @Override
    public void configureJsonObject(JsonObject config) {
        this.setJsonMapTiles(config.get("tiles").getAsJsonArray());
        int MAX_WORLD_COLUMNS = this.getJsonMapTiles().size();
        int MAX_WORLD_ROWS = this.getJsonMapTiles().get(0).getAsJsonArray().size();
        mapTiles = new int[MAX_WORLD_COLUMNS][MAX_WORLD_ROWS];

        for (int row = 0; row < MAX_WORLD_COLUMNS; row++) { for (int column = 0; column < MAX_WORLD_ROWS; column++) { mapTiles[column][row] = this.getJsonMapTiles().get(row).getAsJsonArray().get(column).getAsInt(); } }
    }

    @Override
    public void configureJsonArray(JsonArray configurator) { }

    // ----- GETTERS -----
    public JsonArray getJsonMapTiles() { return jsonMapTiles; }
    public int[][] getMapTiles() { return mapTiles; }

    // ----- SETTERS -----
    public void setJsonMapTiles(JsonArray jsonMapTiles) { this.jsonMapTiles = jsonMapTiles; }
}
