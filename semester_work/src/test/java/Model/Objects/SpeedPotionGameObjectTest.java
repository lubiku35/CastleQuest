package Model.Objects;
import lubiku.castleQuest.Model.Objects.SpeedPotionGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link SpeedPotionGameObject}.
 */
public class SpeedPotionGameObjectTest {

    /**
     * Test method for creating a {@link SpeedPotionGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateSpeedPotionObject() {
        SpeedPotionGameObject speedPotionObject = new SpeedPotionGameObject();
        assertNotNull(speedPotionObject);
        assertEquals("SpeedPotion", speedPotionObject.getObjName());
        assertNotNull(speedPotionObject.getObjImage1());
        assertTrue(speedPotionObject.isObjCollision());
    }
}

