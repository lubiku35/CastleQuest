package Model.Objects;
import lubiku.castleQuest.Model.Objects.MapGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MapGameObject}.
 */
public class MapGameObjectTest {

    /**
     * Test method for creating a {@link MapGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateMapObject() {
        MapGameObject mapObject = new MapGameObject();
        assertNotNull(mapObject);
        assertEquals("Map", mapObject.getObjName());
        assertNotNull(mapObject.getObjImage1());
        assertTrue(mapObject.isObjCollision());
    }
}
