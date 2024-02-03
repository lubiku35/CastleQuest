package lubiku.castleQuest.Configuration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Controller.MainController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * <h2>Configurator</h2>
 * The Configurator class is responsible for loading and managing various configurations used in the game.
 * It uses the Gson library for reading and parsing JSON configuration files.
 */
public class Configurator {
    private final Gson gson = new Gson();

    // APP CONFIG
    private JsonObject appWindowConfig;

    // GAME CONFIGS
    private JsonObject heroConfig;
    private JsonObject npcConfig;
    private JsonObject mapConfig;
    private JsonObject gameWindowConfig;
    private JsonArray objectsConfig;
    private JsonArray enemiesConfig;

    private MainController MAIN_CONTROLLER;

    /**
     * <h3>Configurator</h3>
     * Constructs a new instance of the Configurator class.
     * @param MAIN_CONTROLLER - takes MainController primary for log purposes
     * @see MainController
     */
    public Configurator(MainController MAIN_CONTROLLER) { this.MAIN_CONTROLLER = MAIN_CONTROLLER; }

    /**
     * <h3>loadAppConfig</h3>
     * Loads the application configuration from a specified JSON file.
     * @param configFile The path to the configuration file.
     */
    public void loadAppConfig(String configFile) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            JsonObject data = gson.fromJson(reader, JsonObject.class);
            this.appWindowConfig = data.getAsJsonObject("mainWindow");
        }
        catch (Exception ex) {
            this.MAIN_CONTROLLER.getLOGGER().log(
            "Exception occurred while creating Application Window Config" +
            ex.getMessage());
        }
    }

    /**
     * <h3>loadGameConfigs</h3>
     * Loads the game configurations from a specified JSON file.
     * @param configFile The path to the configuration file.
     */
    public void loadGameConfigs(MainController MAIN_CONTROLLER, String configFile, boolean isLoaded) {
        if (isLoaded) {
            try (FileInputStream fileInputStream = new FileInputStream(configFile);
                 Reader reader = new InputStreamReader(fileInputStream)) {
                JsonObject data = gson.fromJson(reader, JsonObject.class);

                this.gameWindowConfig = data.getAsJsonObject("gameWindow");
                this.mapConfig = data.getAsJsonObject("map");
                this.heroConfig = data.getAsJsonObject("hero");
                this.npcConfig = data.getAsJsonObject("npc");
                this.objectsConfig = data.getAsJsonArray("objects");
                this.enemiesConfig = data.getAsJsonArray("monsters");

                MAIN_CONTROLLER.getLOGGER().log("\ngameWindowConfig:" + this.gameWindowConfig);
                MAIN_CONTROLLER.getLOGGER().log("mapConfig:" + this.mapConfig);
                MAIN_CONTROLLER.getLOGGER().log("heroConfig:" + this.heroConfig);
                MAIN_CONTROLLER.getLOGGER().log("npcConfig:" + this.npcConfig);
                MAIN_CONTROLLER.getLOGGER().log("objectsConfig:" + this.objectsConfig);
                MAIN_CONTROLLER.getLOGGER().log("enemiesConfig:" + this.enemiesConfig);

            } catch (Exception ex) {
                this.MAIN_CONTROLLER.getLOGGER().log(
                "Exception occurred while loading game configurations" +
                ex.getMessage());
            }
        } else {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

            try (Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
                JsonObject data = gson.fromJson(reader, JsonObject.class);

                this.gameWindowConfig = data.getAsJsonObject("gameWindow");
                this.mapConfig = data.getAsJsonObject("map");
                this.heroConfig = data.getAsJsonObject("hero");
                this.npcConfig = data.getAsJsonObject("npc");
                this.objectsConfig = data.getAsJsonArray("objects");
                this.enemiesConfig = data.getAsJsonArray("monsters");

            } catch (Exception ex) {
                this.MAIN_CONTROLLER.getLOGGER().log(
                "Exception occurred while loading game configurations" +
                ex.getMessage());
            }
        }
    }

    // ----- GETTERS -----

    // Application Config
    public JsonObject getAppWindowConfig() { return appWindowConfig; }

    // Game Configs
    public JsonObject getMapConfig() { return mapConfig; }
    public JsonObject getGameWindowConfig() { return gameWindowConfig; }
    public JsonObject getHeroConfig() { return heroConfig; }
    public JsonObject getNpcConfig() { return npcConfig; }
    public JsonArray getObjectsConfig() { return objectsConfig; }
    public JsonArray getEnemiesConfig() { return enemiesConfig; }
}
