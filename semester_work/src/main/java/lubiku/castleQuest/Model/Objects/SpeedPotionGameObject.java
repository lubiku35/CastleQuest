package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>SpeedPotionObject</h2>
 * Represents a speed potion object in the game, extending the base Object class.
 */
public class SpeedPotionGameObject extends GameObject {

    /**
     * <h3>SpeedPotionObject Constructor</h3>
     * Constructs a new SpeedPotionObject instance.
     * Sets the object name to "SpeedPotion", loads the speed potion image, and enables collision.
     */
    public SpeedPotionGameObject() {
        this.setObjName("SpeedPotion");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/speedPotion.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
        this.setObjCollision(true);
    }
}
