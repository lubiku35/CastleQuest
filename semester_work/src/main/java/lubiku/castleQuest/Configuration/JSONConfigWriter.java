package lubiku.castleQuest.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lubiku.castleQuest.Controller.MainController;
import lubiku.castleQuest.Model.Parents.GameObject;
import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.View.GamePanel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * <h2>JSONConfigWriter</h2>
 *
 The JSONConfigWriter class is responsible for creating a JSON configuration file for a game.
 It takes input from the MainController and GamePanel classes to collect the necessary data.
 It then converts this data into a JSON string and writes it to a temporary save file using the Gson library.
 * */
public class JSONConfigWriter {
    private static final String CONFIG_DIRECTORY = "./src/main/resources/Config/";

    private MainController MAIN_CONTROLLER;
    private GamePanel GAME_PANEL;
    protected Map<String, Object> gameWindow;
    protected Map<String, Object> map;
    protected Map<String, Object> hero;
    protected Map<String, Object> npc;
    protected List<Map<String, Object>> objects;
    protected List<Map<String, Object>> monsters;

    private int incrementer = 0;
    public JSONConfigWriter(MainController MAIN_CONTROLLER, GamePanel GAME_PANEL) {
        this.MAIN_CONTROLLER = MAIN_CONTROLLER;
        this.GAME_PANEL = GAME_PANEL;
    }

    public void createJsonSaveFile() {
        // Create the JSON config object
        ConfigCollector configCollector = new ConfigCollector();
        configCollector.getCurrentData(this.MAIN_CONTROLLER, this.GAME_PANEL);

        gameWindow = createGameWindowJsonObjectData(configCollector);
        map = createMapJsonObjectData(configCollector);
        hero = createHeroJsonObjectData(configCollector);
        npc = createNpcJsonObjectData(configCollector);
        objects = createObjectsJsonObjectData(configCollector);
        monsters = createMonstersJsonObjectData(configCollector);

        // Create the Config object and set the gameWindow property
        Config config = new Config();

        config.setGameWindow(gameWindow);
        config.setMap(map);
        config.setHero(hero);
        config.setNpc(npc);
        config.setObjects(objects);
        config.setMonsters(monsters);

        // Convert JSON config object to JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert Config object to JSON string
        String jsonString = gson.toJson(config);

        Random random = new Random();
        int randomInt = random.nextInt(9999);


        // Determine the appropriate temporary directory based on the operating system
        String os = System.getProperty("os.name").toLowerCase();
        String tempDirectory;
        if (os.contains("win")) { tempDirectory = System.getenv("TEMP"); }
        else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            tempDirectory = System.getenv("TMPDIR");
            if (tempDirectory == null) { tempDirectory = "/tmp"; }
        } else {
            // Use a default temporary directory if the operating system is not recognized
            tempDirectory = System.getProperty("java.io.tmpdir");
        }

        // Create the saves directory if it does not exist
        File savesDirectory = new File(tempDirectory, "CastleQuestSaves");
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }

        // Set the save file path to the temporary directory
        String filename = savesDirectory.getPath() + File.separator + configCollector.getHERO_NAME() + "_" + configCollector.getMAP_NAME().toLowerCase().replace(" ", "_") + "_" + randomInt + ".json";

        // Write the JSON string to the save file
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonString);
            MAIN_CONTROLLER.getLOGGER().log("Created save file " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> createGameWindowJsonObjectData(ConfigCollector configCollector) {
        // Retrieve values using getters
        int originalTileSize = configCollector.getORIGINAL_TILE_SIZE();
        int scale = configCollector.getSCALE();
        int maxScreenColumns = configCollector.getMAX_SCREEN_COLUMNS();
        int maxScreenRows = configCollector.getMAX_SCREEN_ROWS();
        int maxWorldColumns = configCollector.getMAX_WORLD_COLUMNS();
        int maxWorldRows = configCollector.getMAX_WORLD_ROWS();
        int fps = configCollector.getFPS();

        // Create a map to store the gameWindow configuration
        Map<String, Object> gameWindowConfig = new HashMap<>();
        gameWindowConfig.put("ORIGINAL_TILE_SIZE", originalTileSize);
        gameWindowConfig.put("SCALE", scale);
        gameWindowConfig.put("MAX_SCREEN_COLUMNS", maxScreenColumns);
        gameWindowConfig.put("MAX_SCREEN_ROWS", maxScreenRows);
        gameWindowConfig.put("MAX_WORLD_COLUMNS", maxWorldColumns);
        gameWindowConfig.put("MAX_WORLD_ROWS", maxWorldRows);
        gameWindowConfig.put("FPS", fps);

        return gameWindowConfig;
    }

    private Map<String, Object> createMapJsonObjectData(ConfigCollector configCollector) {
        // Retrieve values using getters
        int[][] mapTiles = configCollector.getMAP_TILES();

        // Create a map to store the gameWindow configuration
        Map<String, Object> mapConfig = new HashMap<>();

        mapConfig.put("tiles", mapTiles);

        return mapConfig;
    }

    private Map<String, Object> createHeroJsonObjectData(ConfigCollector configCollector) {
        // Retrieve values using getters
        String heroName = configCollector.getHERO_NAME();
        int heroXPosition = configCollector.getHERO_X_POSITION() / 48 + 1;
        int heroYPosition = configCollector.getHERO_Y_POSITION() / 48 + 1;
        int heroSpeed = configCollector.getHERO_SPEED();
        int heroMaximumHealth = configCollector.getHERO_MAXIMUM_HEALTH();
        int heroCurrentHealth = configCollector.getHERO_CURRENT_HEALTH();
        int hasKey = configCollector.getHAS_KEY();
        int hasSpecialKey = configCollector.getHAS_SPECIAL_KEY();
        boolean hasMap = configCollector.isHAS_MAP();
        boolean hasEnchantedSword = configCollector.isHAS_ENCHANTED_SWORD();

        // Create a map to store the hero configuration
        Map<String, Object> heroConfig = new HashMap<>();

        heroConfig.put("HERO_NAME", heroName);
        heroConfig.put("HERO_X_POSITION", heroXPosition);
        heroConfig.put("HERO_Y_POSITION", heroYPosition);
        heroConfig.put("HERO_SPEED", heroSpeed);
        heroConfig.put("MAXIMUM_HEALTH", heroMaximumHealth);
        heroConfig.put("CURRENT_HEALTH", heroCurrentHealth);
        heroConfig.put("HAS_KEY", hasKey);
        heroConfig.put("HAS_SPECIAL_KEY", hasSpecialKey);
        heroConfig.put("HAS_MAP", hasMap);
        heroConfig.put("HAS_ENCHANTED_SWORD", hasEnchantedSword);

        return heroConfig;
    }

    private Map<String, Object> createNpcJsonObjectData(ConfigCollector configCollector) {
        // Retrieve values using getters
        int npcXPosition = configCollector.getNPC_X_POSITION() / 48;
        int npcYPosition = configCollector.getNPC_Y_POSITION() / 48;
        int npcSpeed = configCollector.getNPC_SPEED();

        // Create a map to store the NPC configuration
        Map<String, Object> npcConfig = new HashMap<>();

        npcConfig.put("NPC_X_POSITION", npcXPosition);
        npcConfig.put("NPC_Y_POSITION", npcYPosition);
        npcConfig.put("NPC_SPEED", npcSpeed);

        return npcConfig;
    }

    private List<Map<String, Object>> createObjectsJsonObjectData(ConfigCollector configCollector) {
        GameObject[] gameObjects = configCollector.getObjects();

        // Create a list to store the object configurations
        List<Map<String, Object>> objectsList = new ArrayList<>();

        // Iterate over the gameObjects array
        for (GameObject gameObject : gameObjects) {
            // Create a map to store the object configuration
            Map<String, Object> objectConfig = new HashMap<>();

            // Set the object type
            objectConfig.put("type", gameObject.getObjName() + "Object");

            // Create a nested map for the position
            Map<String, Integer> positionMap = new HashMap<>();
            positionMap.put("positionX", gameObject.getOBJECT_X_POSITION() / 48);
            positionMap.put("positionY", gameObject.getOBJECT_Y_POSITION() / 48);

            // Add the position map to the object configuration
            objectConfig.put("position", positionMap);

            // Add the object configuration to the objects list
            objectsList.add(objectConfig);
        }

        return objectsList;
    }

    private List<Map<String, Object>> createMonstersJsonObjectData(ConfigCollector configCollector) {
        Monster[] monsters = configCollector.getMonsters();

        // Create a list to store the monster configurations
        List<Map<String, Object>> monstersList = new ArrayList<>();

        // Iterate over the monsters array
        for (Monster monster : monsters) {
            // Create a map to store the monster configuration
            Map<String, Object> monsterConfig = new HashMap<>();

            // Set the monster properties
            monsterConfig.put("NAME", monster.getName());
            monsterConfig.put("SPEED", monster.getENTITY_SPEED());
            monsterConfig.put("MAXIMUM_HEALTH", monster.getMaximumHealth());
            monsterConfig.put("CURRENT_HEALTH", monster.getHealth());

            // Create a nested map for the position
            Map<String, Integer> positionMap = new HashMap<>();
            positionMap.put("positionX", monster.getENTITY_X_POSITION() / 48);
            positionMap.put("positionY", monster.getENTITY_Y_POSITION() / 48);

            // Add the position map to the monster configuration
            monsterConfig.put("position", positionMap);

            // Add the monster configuration to the monsters list
            monstersList.add(monsterConfig);
        }

        return monstersList;
    }

    private static class Config {
        // Configuration Objects
        Map<String, Object> gameWindow;
        Map<String, Object> map;
        Map<String, Object> hero;
        Map<String, Object> npc;
        List<Map<String, Object>> objects;
        List<Map<String, Object>> monsters;

        public void setGameWindow(Map<String, Object> gameWindow) { this.gameWindow = gameWindow; }
        public void setMap(Map<String, Object> map) { this.map = map; }
        public void setHero(Map<String, Object> hero) { this.hero = hero; }
        public void setNpc(Map<String, Object> npc) { this.npc = npc; }
        public void setObjects(List<Map<String, Object>> objects) { this.objects = objects; }
        public void setMonsters(List<Map<String, Object>> monsters) { this.monsters = monsters; }
    }
}
