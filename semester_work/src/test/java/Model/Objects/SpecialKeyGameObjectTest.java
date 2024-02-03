package Model.Objects;
import lubiku.castleQuest.Model.Objects.SpecialKeyGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link SpecialKeyGameObject}.
 */
public class SpecialKeyGameObjectTest {

    /**
     * Test method for creating a {@link SpecialKeyGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateSpecialKeyObject() {
        SpecialKeyGameObject specialKeyObject = new SpecialKeyGameObject();
        assertNotNull(specialKeyObject);
        assertEquals("SpecialKey", specialKeyObject.getObjName());
        assertNotNull(specialKeyObject.getObjImage1());
        assertTrue(specialKeyObject.isObjCollision());
    }
}
