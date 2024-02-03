package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;
import lubiku.castleQuest.View.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>HeartObject</h2>
 * Represents a heart object in the game, extending the base Object class.
 */
public class HeartGameObject extends GameObject {
    private GamePanel gamePanel;

    /**
     * <h3>HeartObject Constructor</h3>
     * Constructs a new HeartObject instance.
     * @param gamePanel the game panel containing the heart object.
     * Sets the object name to "Heart" and loads the different heart images for different health levels.
     */
    public HeartGameObject(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.setObjName("Heart");
        try {
            this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/Health/0_health.png"))));
            this.setObjImage2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/Health/50_health.png"))));
            this.setObjImage3(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/Health/100_health.png"))));
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
