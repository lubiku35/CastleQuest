package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.Model.NPC.NPC;
import lubiku.castleQuest.View.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>NPCManager</h2>
 * The NPCManager class manages and configures Non-Playable Characters (NPCs) in the game.
 * It extends the NPC class and implements the Configurator interface.
 * It handles loading NPC sprites, configuring NPC attributes, and managing NPC position and speed.
 */
public class NPCManager extends NPC implements Configurator {
    private NPC npc;
    private int TILE_SIZE;


    /**
     * <h3>NPCManager</h3>
     * Constructs an NPCManager object with the specified game panel.
     * @param gamePanel The GamePanel object to associate with the NPCManager.
     */
    public NPCManager(GamePanel gamePanel) {
        super(gamePanel);
    }

    /**
     * <h3>configureJsonObject</h3>
     * Configures the NPC attributes based on the provided JSON object.
     * @param config The JSON object containing the NPC configuration.
     */
    @Override
    public void configureJsonObject(JsonObject config) {
        this.npc.setENTITY_X_POSITION(config.get("NPC_X_POSITION").getAsInt() * this.getTILE_SIZE());
        this.npc.setENTITY_Y_POSITION(config.get("NPC_Y_POSITION").getAsInt() * this.getTILE_SIZE());
        this.npc.setENTITY_SPEED(config.get("NPC_SPEED").getAsInt());
    }
    @Override
    public void configureJsonArray(JsonArray configurator) { }

    /**
     * <h3>constructNPCSprites</h3>
     * Constructs the NPC sprites by loading them from their respective image paths.
     * Sets up the NPC's sprites for different directions (up, down, left, right).
     */
    public void constructNPCSprites () {
        this.npc.setImageUp1(setUpSprite("npcUp1"));
        this.npc.setImageUp2(setUpSprite("npcUp2"));
        this.npc.setImageDown1(setUpSprite("npcDown1"));
        this.npc.setImageDown2(setUpSprite("npcDown2"));
        this.npc.setImageLeft1(setUpSprite("npcLeft1"));
        this.npc.setImageLeft2(setUpSprite("npcLeft2"));
        this.npc.setImageRight1(setUpSprite("npcRight1"));
        this.npc.setImageRight2(setUpSprite("npcRight2"));
    }

    /**
     * <h3>setUpSprite</h3>
     * Sets up the sprite for the NPC based on the provided image path.
     * @param imagePath The path to the NPC sprite image.
     * @return The loaded BufferedImage object representing the NPC sprite.
     */
    public BufferedImage setUpSprite(String imagePath) {
        try { return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/NPC/" + imagePath + ".png"))); }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    // ----- GETTERS -----
    public int getTILE_SIZE() { return TILE_SIZE; }

    // ----- SETTERS -----
    public void setNpc(NPC npc) { this.npc = npc; }
    public void setTILE_SIZE(int TILE_SIZE) { this.TILE_SIZE = TILE_SIZE; }

}
