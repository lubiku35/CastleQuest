package Model;

import lubiku.castleQuest.Model.Parents.Entity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityMovementTest {
    private static Entity entity;
    @BeforeEach
    void setUp() {
        entity = new Entity();
        setUpEntity(entity);
    }

    protected static void setUpEntity(Entity entity) {
        entity.setDirection("random");
        entity.setENTITY_SPEED(3);
        entity.setENTITY_Y_POSITION(48);
        entity.setENTITY_X_POSITION(48);
        entity.setName("TestEntity");
        entity.setMaximumHealth(6);
        entity.setHealth(6);
    }

    @Order(value = 1)
    @Test
    void testMoveUp() {
        entity.allowMoveEntity(entity, false, "up", false);
        Assertions.assertEquals(45, entity.getENTITY_Y_POSITION());
    }

    @Order(value = 2)
    @Test
    void testMoveLeft() {
        entity.allowMoveEntity(entity, false, "left", false);
        Assertions.assertEquals(45, entity.getENTITY_X_POSITION());
    }

    @Order(value = 3)
    @Test
    void testMoveDown() {
        entity.allowMoveEntity(entity, false, "down", false);
        Assertions.assertEquals(51, entity.getENTITY_Y_POSITION());
    }

    @Order(value = 4)
    @Test
    void testMoveRight() {
        entity.allowMoveEntity(entity, false, "right", false);
        Assertions.assertEquals(51, entity.getENTITY_X_POSITION());
    }

    @Order(value = 5)
    @Test
    void testCollision() {
        entity.allowMoveEntity(entity, true, "up", false);
        Assertions.assertEquals(48, entity.getENTITY_Y_POSITION());
    }

    @Order(value = 6)
    @Test
    void testEnterPressed() {
        entity.allowMoveEntity(entity, false, "up", true);
        Assertions.assertEquals(48, entity.getENTITY_Y_POSITION());
    }
}
