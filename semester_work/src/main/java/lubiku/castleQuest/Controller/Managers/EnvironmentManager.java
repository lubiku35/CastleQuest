package lubiku.castleQuest.Controller.Managers;

import lubiku.castleQuest.Model.Environment.Lighting;
import lubiku.castleQuest.View.GamePanel;

import java.awt.*;

/**
 * <h2>EnvironmentManager</h2>
 * The EnvironmentManager class is responsible for managing the game environment, including lighting effects.
 * It provides methods to set up and control the lighting in the game.
 */
public class EnvironmentManager {

    private GamePanel GAME_PANEL;
    private Lighting lighting;

    /**
     * <h3>EnvironmentManager</h3>
     * Constructs an EnvironmentManager object with the specified GamePanel.
     * @param GAME_PANEL the GamePanel instance to associate with the EnvironmentManager
     */
    public EnvironmentManager(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;
    }

    /**
     * <h3>setUpLighting</h3>
     * Sets up the lighting effect in the game environment.
     * This method creates a new instance of the Lighting class and initializes it with the specified parameters.
     * @see Lighting
     */
    public void setUpLighting() {
        lighting = new Lighting(this.GAME_PANEL, 450);
    }

    /**
     * <h3>draw</h3>
     * Draws the lighting effect on the specified Graphics2D object.
     * @param graphics2D the Graphics2D object on which to draw the lighting effect
     */
    public void draw(Graphics2D graphics2D) {
        lighting.draw(graphics2D);
    }

}
