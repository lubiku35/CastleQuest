package Model;

import lubiku.castleQuest.Model.NPC.NPC;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NPCChangeDirectionTest {
    private static NPC npc;
    @BeforeEach
    void setUp() { npc = new NPC(); }
    @Order(value = 1)
    @Test
    public void testChangeDirectionUp() {
        npc.setDirection("up");
        npc.changeNPCDirectionToHero(npc.getDirection());
        Assertions.assertEquals("down", npc.getDirection(), "Direction should be changed to 'down'");
    }

    @Order(value = 2)
    @Test
    public void testChangeDirectionDown() {
        npc.setDirection("down");
        npc.changeNPCDirectionToHero(npc.getDirection());
        Assertions.assertEquals("up", npc.getDirection(), "Direction should be changed to 'up'");
    }

    @Order(value = 3)
    @Test
    public void testChangeDirectionRight() {
        npc.setDirection("right");
        npc.changeNPCDirectionToHero(npc.getDirection());
        Assertions.assertEquals("left", npc.getDirection(), "Direction should be changed to 'left'");
    }

    @Order(value = 4)
    @Test
    public void testChangeDirectionLeft() {
        npc.setDirection("left");
        npc.changeNPCDirectionToHero(npc.getDirection());
        Assertions.assertEquals("right", npc.getDirection(), "Direction should be changed to 'right'");
    }
}
