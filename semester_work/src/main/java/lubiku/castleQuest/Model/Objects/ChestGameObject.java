package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>ChestObject</h2>
 * Represents a chest object in the game, extending the base Object class.
 */
public class ChestGameObject extends GameObject {

    /**
     * <h3>ChestObject Constructor</h3>
     * Constructs a new ChestObject instance.
     * Sets the object name to "Chest" and loads the chest image.
     */
    public ChestGameObject() {
        this.setObjName("Chest");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/chest.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
