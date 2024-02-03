package Model.Objects;

import lubiku.castleQuest.Model.Objects.KeyGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link KeyGameObject}.
 */
public class KeyGameObjectTest {

    /**
     * Test method for creating a {@link KeyGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateKeyObject() {
        KeyGameObject keyObject = new KeyGameObject();
        assertNotNull(keyObject);
        assertEquals("Key", keyObject.getObjName());
        assertNotNull(keyObject.getObjImage1());
        assertTrue(keyObject.isObjCollision());
    }
}
