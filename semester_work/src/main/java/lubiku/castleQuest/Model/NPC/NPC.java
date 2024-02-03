package lubiku.castleQuest.Model.NPC;

import lubiku.castleQuest.Controller.Handlers.CollisionHandler;
import lubiku.castleQuest.Controller.Managers.NPCManager;
import lubiku.castleQuest.Model.Parents.Entity;
import lubiku.castleQuest.View.GamePanel;

import java.awt.*;
import java.util.Random;

/**
 * <h2>NPC</h2>
 * The NPC class represents a non-playable character (NPC) in the game.
 * It extends the Entity class and is responsible for handling the behavior and interactions of NPCs.
 * */
public class NPC extends Entity {

    private NPCManager NPC_MANAGER;
    private static GamePanel gamePanel;
    private int actionLockCounter = 0;
    private int dialogueIndex = 0;

    /**
     * <h3>NPC</h3>
     * Creates a new instance of the NPC class.
     * @param gamePanel        The game panel where the NPC will be displayed.
     */
    public NPC(GamePanel gamePanel) {
        super(gamePanel);
        NPC.gamePanel = gamePanel;

        // Collision Detection Variables
        this.setSolidArea(new Rectangle(7, 12, 35, 33));
        this.setSolidAreaDefaultX(7);
        this.setSolidAreaDefaultY(12);

        // Set Default Direction
        this.setDirection("right");
    }


    /**
     *  <h3>NPC</h3>
     *  Constructs an empty NPC object primary for testing purposes.
     */
    public NPC() { }

    /**
     * <h3>setNpcAction</h3>
     * Sets the NPC's action by randomly choosing a direction.
     * The action is locked for a certain duration before the next action can be performed.
     */
    public void setNpcAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) { this.setDirection("up"); }
            else if (i <= 50) { this.setDirection("down"); }
            else if (i <= 75) { this.setDirection("left"); }
            else { this.setDirection("right"); }

            actionLockCounter = 0;
        }
    }

    /**
     * <h3>updateNPC</h3>
     * Updates the NPC's state and behavior.
     * This method handles NPC movement, collision checks, and image changes.
     */
    public void updateNPC() {
        // AI NPC MOVEMENT
        this.setNpcAction();

        // Check Tile Collision
        this.setCollision(false);
        this.getCOLLISION_HANDLER().checkTile(this);

        // Get Hero INDEX

        boolean contact = this.getCOLLISION_HANDLER().checkEntity(this, gamePanel.getHero());
        interactNPC(contact);

        // Check Monster Collision
        this.getCOLLISION_HANDLER().checkMonster(this);


        // Without collision allow entity to move to the direction
        allowMoveEntity(this, this.isCollision(), this.getDirection(), false);

        // Handling the changes of images
        this.setSpriteFrameCounter(getSpriteFrameCounter() + 1);
        if (getSpriteFrameCounter() % 16 == 0) {
            if (getSpriteNumber() == 1) {
                setSpriteNumber(2);
            } else if (getSpriteNumber() == 2) {
                setSpriteNumber(1);
            }
        }
    }

    /**
     * <h3>interactNPC</h3>
     * Interacts with the NPC when in contact with another entity.
     * @param contact Indicates whether there is contact with the NPC.
     */
    public void interactNPC(boolean contact) {
        if (contact) { }
    }

    /**
     * <h3>generateDialogues</h3>
     * Generates the dialogues for the NPC.
     * This method initializes the dialogues array with preset strings.
     */
    private void generateDialogues() {
        this.getDialogues()[0] = "Hello brave hero...";
        this.getDialogues()[1] = "Okay, But... You are against scary monsters. Still want to go ?";
        this.getDialogues()[2] = "All right then, keep your secrets";
    }

    /**
     * <h3>speak</h3>
     * Generates dialogues for the NPC and updates the NPC's behavior accordingly.
     * If there are remaining dialogues, it sets the current dialogue on the game panel's UI and increments the dialogue index.
     * It also changes the NPC's direction based on the hero's direction.
     */
    public void speak() {
        this.generateDialogues();
        if (dialogueIndex >= 3) {
            return;
        }
        if (this.getDialogues()[dialogueIndex] != null) {
            gamePanel.getUI().setCurrentDialogue(this.getDialogues()[dialogueIndex]);
            dialogueIndex++;
        }
        changeNPCDirectionToHero(gamePanel.getHero().getDirection());
    }

    /**
     * <h3>changeNPCDirectionToHero</h3>
     * Changes the direction of the NPC based on the direction of the hero.
     * @param direction The direction of the hero.
     */
    public void changeNPCDirectionToHero(String direction) {
        switch (direction) {
            case "up" -> this.setDirection("down");
            case "down" -> this.setDirection("up");
            case "right" -> this.setDirection("left");
            case "left" -> this.setDirection("right");
        }
    }

    // ----- GETTERS -----
    public NPCManager getNPC_MANAGER() { return NPC_MANAGER; }

    // CUSTOM
    private CollisionHandler getCOLLISION_HANDLER() { return gamePanel.getMAIN_CONTROLLER().getCOLLISION_HANDLER(); }

    // ----- SETTERS -----
    public void setNPC_MANAGER(NPCManager NPC_MANAGER) { this.NPC_MANAGER = NPC_MANAGER; }
}