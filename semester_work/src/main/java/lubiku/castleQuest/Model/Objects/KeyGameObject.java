package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>KeyObject</h2>
 * Represents a key object in the game, extending the base Object class.
 */
public class KeyGameObject extends GameObject {

    /**
     * <h3>KeyObject Constructor</h3>
     * Constructs a new KeyObject instance.
     * Sets the object name to "Key" and loads the key image.
     */
    public KeyGameObject() {
        this.setObjName("Key");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/key.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
