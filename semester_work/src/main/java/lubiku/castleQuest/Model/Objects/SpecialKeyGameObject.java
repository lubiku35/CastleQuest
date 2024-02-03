package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>SpecialKeyObject</h2>
 * Represents a special key object in the game, extending the base Object class.
 */
public class SpecialKeyGameObject extends GameObject {

    /**
     * <h3>SpecialKeyObject Constructor</h3>
     * Constructs a new SpecialKeyObject instance.
     * Sets the object name to "SpecialKey" and loads the special key image.
     */
    public SpecialKeyGameObject() {
        this.setObjName("SpecialKey");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/specialKey.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
