package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.Model.Objects.*;
import lubiku.castleQuest.Model.Parents.GameObject;

/**
 * <h2>ObjectManager</h2>
 * The Object Manager class implements the Configurator interface and is responsible for configuring and managing objects in the game.
 * */
public class ObjectManager implements Configurator {
    private int TILE_SIZE;

    // Objects
    private GameObject[] gameObjects;

    /**
     * <h3>ObjectManager</h3>
     * Constructor which creates a new ObjectManager.
     */
    public ObjectManager() { }

    @Override
    public void configureJsonObject(JsonObject configurator) { }

    /**
     * <h3>configureJsonArray</h3>
     * Implementation of the `configureJsonArray` method in the `Configurator` interface.
     * Configures and sets up objects based on the provided JSON array.
     * Supports various object types such as keys, gates, chests, maps, potions, etc.
     * @param config The JSON array containing the object configurations.
     */
    @Override
    public void configureJsonArray(JsonArray config) {
        System.out.println(config);
        this.setObjects(new GameObject[config.size()]);
        for (int i = 0; i < config.size(); i++) {
            JsonObject objectConfig = config.get(i).getAsJsonObject();

            String objectType = objectConfig.get("type").getAsString();
            int positionX = objectConfig.get("position").getAsJsonObject().get("positionX").getAsInt();
            int positionY = objectConfig.get("position").getAsJsonObject().get("positionY").getAsInt();

            switch (objectType) {
                case "KeyObject" -> gameObjects[i] = new KeyGameObject();
                case "SpecialKeyObject" -> gameObjects[i] = new SpecialKeyGameObject();
                case "GateObject" -> gameObjects[i] = new GateGameObject();
                case "ChestObject" -> gameObjects[i] = new ChestGameObject();
                case "MapObject" -> gameObjects[i] = new MapGameObject();
                case "SpeedPotionObject" -> gameObjects[i] = new SpeedPotionGameObject();
                case "HealPotionObject" -> gameObjects[i] = new HealPotionGameObject();
                default -> System.out.println("Wrong Object Type");
            }

            gameObjects[i].setOBJECT_X_POSITION(positionX * this.getTILE_SIZE());
            gameObjects[i].setOBJECT_Y_POSITION(positionY * this.getTILE_SIZE());
        }
    }

    // ----- GETTERS -----
    public int getTILE_SIZE() { return TILE_SIZE; }
    public GameObject[] getObjects() { return gameObjects; }

    // ----- SETTERS -----
    public void setTILE_SIZE(int TILE_SIZE) { this.TILE_SIZE = TILE_SIZE; }
    public void setObjects(GameObject[] gameObjects) { this.gameObjects = gameObjects; }
}
