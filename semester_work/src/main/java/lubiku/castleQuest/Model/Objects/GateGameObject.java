package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>GateObject</h2>
 * Represents a gate object in the game, extending the base Object class.
 */
public class GateGameObject extends GameObject {

    /**
     * <h3>GateObject Constructor</h3>
     * Constructs a new GateObject instance.
     * Sets the object name to "Gate", loads the gate image, and sets the collision property to true.
     */
    public GateGameObject() {
        this.setObjName("Gate");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/gate.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
        this.setObjCollision(true);
    }
}
