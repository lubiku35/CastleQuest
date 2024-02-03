package Model.Objects;

import lubiku.castleQuest.Model.Objects.GateGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link GateGameObject}.
 */
public class GateGameObjectTest {

    /**
     * Test method for creating a {@link GateGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateGateObject() {
        GateGameObject gateObject = new GateGameObject();
        assertNotNull(gateObject);
        assertEquals("Gate", gateObject.getObjName());
        assertNotNull(gateObject.getObjImage1());
        assertTrue(gateObject.isObjCollision());
    }
}
