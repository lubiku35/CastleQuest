package Model.Objects;

import lubiku.castleQuest.Model.Objects.ChestGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChestGameObject}.
 */
public class ChestGameObjectTest {

    /**
     * Test method for creating a {@link ChestGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateChestObject() {
        ChestGameObject chestObject = new ChestGameObject();
        assertNotNull(chestObject);
        assertEquals("Chest", chestObject.getObjName());
        assertNotNull(chestObject.getObjImage1());
        assertTrue(chestObject.isObjCollision());
    }
}
