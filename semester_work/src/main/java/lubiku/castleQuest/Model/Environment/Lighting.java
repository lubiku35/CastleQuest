package lubiku.castleQuest.Model.Environment;

import lubiku.castleQuest.View.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <h2>Lighting</h2>
 * The Lighting class provides a lighting effect that changes the view of the hero in the game.
 * It creates a darkness filter with a light circle at the center, resulting in a spotlight-like effect.
 * The darkness filter is applied to the game panel to modify the visual appearance of the hero's surroundings.
 * This class encapsulates the logic for creating the darkness filter and applying the light circle effect.
 */
public class Lighting {

    private GamePanel GAME_PANEL;
    private final BufferedImage darknessFilter;

    /**
     * <h3>Lighting</h3>
     * Constructs a Lighting object for applying a light circle effect to the game panel.
     * @param GAME_PANEL   The GamePanel object to apply the lighting effect on.
     * @param circleSize   The size of the light circle.
     */
    public Lighting(GamePanel GAME_PANEL, int circleSize) {
        this.GAME_PANEL = GAME_PANEL;
        this.darknessFilter = createDarknessFilter();
        applyLightCircle(circleSize);
    }

    /**
     * <h3>createDarknessFilter</h3>
     * Creates a new BufferedImage for storing the darkness filter.
     * @return The created BufferedImage for the darkness filter.
     */
    private BufferedImage createDarknessFilter() {
        return new BufferedImage(GAME_PANEL.getSCREEN_WIDTH(), GAME_PANEL.getSCREEN_HEIGHT(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * <h3>applyLightCircle</h3>
     * Applies the light circle effect to the darkness filter.
     * @param circleSize   The size of the light circle.
     */
    private void applyLightCircle(int circleSize) {
        Graphics2D graphics2D = darknessFilter.createGraphics();

        Area screenArea = createScreenArea();
        RadialGradientPaint radialGradientPaint = createRadialGradientPaint(circleSize);

        graphics2D.setPaint(radialGradientPaint);
        graphics2D.fill(screenArea);

        graphics2D.dispose();
    }

    /**
     * <h3>createScreenArea</h3>
     * Creates an Area object representing the screen area.
     * @return The created Area object representing the screen area.
     */
    private Area createScreenArea() { return new Area(new Rectangle2D.Double(0, 0, GAME_PANEL.getSCREEN_WIDTH(), GAME_PANEL.getSCREEN_HEIGHT())); }

    /**
     * <h3>createRadialGradientPaint</h3>
     * Creates a RadialGradientPaint object for the light circle effect.
     * @param circleSize   The size of the light circle.
     * @return The created RadialGradientPaint object.
     */
    private RadialGradientPaint createRadialGradientPaint(int circleSize) {
        int centerX = GAME_PANEL.getSCREEN_WIDTH() / 2;
        int centerY = GAME_PANEL.getSCREEN_HEIGHT() / 2;

        Color[] color = {
            new Color(0,0,0,0F),
            new Color(0,0,0,0.1F),
            new Color(0,0,0,0.25F),
            new Color(0,0,0,0.42F),
            new Color(0,0,0,0.52F),
            new Color(0,0,0,0.64F),
            new Color(0,0,0,0.76F),
            new Color(0,0,0,0.82F),
            new Color(0,0,0,0.87F),
            new Color(0,0,0,0.92F),
            new Color(0,0,0,0.96F),
            new Color(0,0,0,0.98F)
        };

        float[] fraction = {
            0F,
            0.1F,
            0.25F,
            0.42F,
            0.52F,
            0.64F,
            0.76F,
            0.82F,
            0.87F,
            0.92F,
            0.96F,
            0.98F
        };

        return new RadialGradientPaint(centerX, centerY, (float) circleSize / 2, fraction, color);
    }

    /**
     * <h3>draw</h3>
     * Draws the darkness filter on the specified Graphics2D object (Hero).
     * @param graphics2D   The Graphics2D object to draw on.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(this.darknessFilter, 0,0, null);
    }
}
