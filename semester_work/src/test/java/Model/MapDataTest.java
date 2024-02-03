package Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Controller.Managers.MapManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MapDataTest {
    private static final MapManager MAP_MANAGER = new MapManager();
    private static final String TEST_CONFIG = "Config\\TestMap.json";
    private static final Gson gson = new Gson();
    private static JsonObject TestMapConfig;

    @BeforeAll
    public static void beforeAll() {
        InputStream inputStream = MapDataTest.class.getClassLoader().getResourceAsStream(TEST_CONFIG);

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            System.out.println("Reading JSON file: " + TEST_CONFIG);

            JsonObject data = gson.fromJson(reader, JsonObject.class);
            JsonObject mapConfig = data.getAsJsonObject("map");
            if (mapConfig != null) {
                setTestMapConfig(mapConfig);
                System.out.println("TestMapConfig set successfully: " + TestMapConfig);
            } else {
                System.out.println("Failed to retrieve 'map' configuration from JSON.");
            }
        } catch (Exception ex) {
            System.out.println("Error occurred while reading JSON file: " + ex.getMessage());
        }
    }

    @Order(value = 1)
    @Test
    public void setUpMap() { MAP_MANAGER.configureJsonObject(getTestMapConfig());}

    @Order(value = 2)
    @Test
    public void checkData() {
        Assertions.assertNotNull(MAP_MANAGER.getJsonMapTiles(), "jsonMapTiles should not be null");
        Assertions.assertNotNull(MAP_MANAGER.getMapTiles(), "mapTiles should not be null");
        Assertions.assertEquals(50, MAP_MANAGER.getJsonMapTiles().size(), "jsonMapTiles should have size 50");

        int expectedRowSize = 50;
        for (int[] row : MAP_MANAGER.getMapTiles()) { Assertions.assertEquals(expectedRowSize, row.length, "Each row in mapTiles should have size 50"); }
    }

    public static JsonObject getTestMapConfig() { return TestMapConfig; }
    public static void setTestMapConfig(JsonObject testMapConfig) { TestMapConfig = testMapConfig; }
}
