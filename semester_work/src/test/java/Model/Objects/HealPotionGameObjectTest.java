package Model.Objects;

import lubiku.castleQuest.Model.Objects.HealPotionGameObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link HealPotionGameObject}.
 */
public class HealPotionGameObjectTest {

    /**
     * Test method for creating a {@link HealPotionGameObject}.
     * It verifies that the object is created with the correct properties.
     */
    @Test
    public void testCreateHealPotionObject() {
        HealPotionGameObject healPotionObject = new HealPotionGameObject();
        assertNotNull(healPotionObject);
        assertEquals("HealPotion", healPotionObject.getObjName());
        assertNotNull(healPotionObject.getObjImage1());
        assertTrue(healPotionObject.isObjCollision());
    }
}
