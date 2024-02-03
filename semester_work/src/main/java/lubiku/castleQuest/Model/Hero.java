package lubiku.castleQuest.Model;

import lubiku.castleQuest.Controller.Managers.HeroManager;
import lubiku.castleQuest.Controller.Handlers.KeyHandler;
import lubiku.castleQuest.Model.Parents.Entity;
import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.View.GamePanel;

import java.awt.*;

/**
 * <h2>Hero</h2>
 *
 * The Hero class represents a hero character in a game and it extends the "Entity" class and encapsulates the hero's behavior and interactions within the game.
 * */
public class Hero extends Entity {

    private HeroManager HERO_MANAGER;
    private static GamePanel GAME_PANEL;
    private static KeyHandler keyHandler;

    // Screen
    private static int SCREEN_X_POSITION, SCREEN_Y_POSITION;


    // Keys
    private int hasKey = 0;
    private int hasSpecialKey = 0;

    // Map
    private boolean hasMap;

    // Map
    private boolean hasEnchantedSword;
    private boolean showEnchantedSwordMessage = false;
    private long enchantedSwordMessageStartTime;

    /**
     * <h3>Hero</h3>
     * Creates a new instance of the Hero class.
     * @param GAME_PANEL The game panel where the hero will be displayed.
     * @param keyHandler The key handler for capturing user input.
     */
    public Hero(GamePanel GAME_PANEL, KeyHandler keyHandler) {
        super(GAME_PANEL);

        // Apply Other Dependencies
        Hero.GAME_PANEL = GAME_PANEL;
        Hero.keyHandler = keyHandler;

        this.setHeroManager(new HeroManager(this));

        // Collision Detection Variables
        this.setSolidArea(new Rectangle(7, 12, 35, 33));
        this.setSolidAreaDefaultX(7);
        this.setSolidAreaDefaultY(12);

        // AttackArea
        this.setAttackArea(new Rectangle(0,0, 32, 32));

        // Set Default Direction
        this.setDirection("down");
    }

    /**
     * <h3>updateHero</h3>
     * Updates the hero based on the user's input and handles interactions with NPCs, objects, monsters, and events.
     * Also handles sprite animation and health management.
     */
    public void updateHero() {
        if (this.getHealth() < 1) { GAME_PANEL.setGameState(GAME_PANEL.getGameOverGameState()); }
        if (this.isAttacking()) { this.attack(); }
        else if (keyHandler.isUpPressed() || keyHandler.isDownPressed() ||
                keyHandler.isLeftPressed() || keyHandler.isRightPressed() || keyHandler.isENTERPressed()) {

            if (keyHandler.isUpPressed()) { this.setDirection("up"); }
            else if (keyHandler.isDownPressed()) { this.setDirection("down"); }
            else if (keyHandler.isLeftPressed()) { this.setDirection("left"); }
            else if (keyHandler.isRightPressed()) { this.setDirection("right"); }

            // Check Tile Collision
            this.setCollision(false);
            GAME_PANEL.getCOLLISION_HANDLER().checkTile(this);

            // Check NPC Collision
            boolean contactNPC = GAME_PANEL.getCOLLISION_HANDLER().checkEntity(this, GAME_PANEL.getNpc());
            interactNPC(contactNPC);

            // Check Object Collision
            int objectIndex = GAME_PANEL.getCOLLISION_HANDLER().checkObject(this, true);
            this.pickUpObject(objectIndex);

            // Check Monster Collision
            int monsterIndex = GAME_PANEL.getCOLLISION_HANDLER().checkMonster(this);
            contactMonster(monsterIndex);

            // Check Event
            GAME_PANEL.getEVENT_HANDLER().checkEvent();

            // Without collision allow entity to move to the direction
            allowMoveEntity(this, this.isCollision(), this.getDirection(), keyHandler.isENTERPressed());

            // Handling the changes of images
            this.setSpriteFrameCounter(getSpriteFrameCounter() + 1);
            if (getSpriteFrameCounter() % 16 == 0) {
                if (getSpriteNumber() == 1) { setSpriteNumber(2); }
                else if (getSpriteNumber() == 2) { setSpriteNumber(1); }
            }
        }
        handleInvisibility(this, this.isInvisible());
    }

    /**
     * <h3>interactNPC</h3>
     * Handles the interaction between the hero and an NPC. If the hero is in contact with an NPC and the ENTER key is pressed,
     * the game state switches to the dialogue state and the NPC speaks. If the hero is not in contact with an NPC and the ENTER
     * key is pressed, the hero goes into attack mode.
     *
     * @param contactNPC True if the hero is in contact with an NPC, false otherwise.
     */
    private void interactNPC(boolean contactNPC) {
        if (contactNPC && keyHandler.isENTERPressed()) {
            GAME_PANEL.setGameState(GAME_PANEL.getDialogueGameState());
            GAME_PANEL.getNpc().speak();
        }
        else if (!contactNPC && keyHandler.isENTERPressed()) { this.setAttacking(true); }
    }

    /**
     * <h3>contactMonster</h3>
     * Handles the interaction between the hero and a monster. If the hero is in contact with a monster and the monster is not
     * currently invisible, the hero's health is reduced by 1 and the hero becomes invisible.
     *
     * @param i The index of the monster in the array.
     */
    private void  contactMonster(int i) {
        if (i != 999) {
            if (!this.isInvisible()) {
                this.setHealth(this.getHealth() - 1);
                this.setInvisible(true);
            }
        }
    }

    /**
     * <h3>damageMonster</h3>
     * Deals damage to the monster based on the hero's attack power. If the monster's health drops to 0 or below, the monster
     * is marked as dying and the appropriate logs are generated.
     *
     * @param monsterIndex The index of the monster in the array.
     */
    private void damageMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            if (!this.getMonsters()[monsterIndex].isInvisible()) {
                this.updateMonsterHealth(monsterIndex);
                
                this.getMonsters()[monsterIndex].setInvisible(true);
                this.getMonsters()[monsterIndex].reactionToDamage(this.getMonsters()[monsterIndex]);
                
                // Handle kill of monster
                if (this.getMonsters()[monsterIndex].getHealth() <= 0) {
                    this.getMonsters()[monsterIndex].setDying(true);
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " killed the monster " + this.getMonsters()[monsterIndex].getName());
                }
            }
        }
    }

    /**
     * <h3>attack</h3>
     * Performs the attack action of the hero, including updating the sprite animation, calculating attack area,
     * checking monster collision, and dealing damage to the monster.
     */
    private void attack() {
        this.setSpriteFrameCounter(this.getSpriteFrameCounter() + 1);
        if (this.getSpriteFrameCounter() <= 5) {
            this.setSpriteNumber(1);
        }
        if (this.getSpriteFrameCounter() > 5 && this.getSpriteFrameCounter() <= 25) {
            this.setSpriteNumber(2);

            // Saving the current variables
            int currentEntityX = this.getENTITY_X_POSITION();
            int currentEntityY = this.getENTITY_Y_POSITION();
            int solidAreaWidth = this.getSolidArea().width;
            int solidAreaHeight = this.getSolidArea().height;

            switch (this.getDirection()) {
                case "up" -> this.setENTITY_Y_POSITION(this.getENTITY_Y_POSITION() - this.getAttackArea().height);
                case "down" -> this.setENTITY_Y_POSITION(this.getENTITY_Y_POSITION() + this.getAttackArea().height);
                case "left" -> this.setENTITY_X_POSITION(this.getENTITY_X_POSITION() - this.getAttackArea().width);
                case "right" -> this.setENTITY_X_POSITION(this.getENTITY_X_POSITION() + this.getAttackArea().width);
            }
            // attackArea becomes solidArea
            this.setSolidArea(new Rectangle(this.getSolidArea().x, this.getSolidArea().y, this.getAttackArea().width, this.getAttackArea().height));

            // Check Monster Collision with updated solidArea
            int monsterIndex = GAME_PANEL.getCOLLISION_HANDLER().checkMonster(this);
            this.damageMonster(monsterIndex);

            // Restore to the original data
            this.setENTITY_X_POSITION(currentEntityX);
            this.setENTITY_Y_POSITION(currentEntityY);
            this.setSolidArea(new Rectangle(this.getSolidArea().x, this.getSolidArea().y, solidAreaWidth, solidAreaHeight));
        }
        if (this.getSpriteFrameCounter() > 25) {
            this.setSpriteNumber(1);
            this.setSpriteFrameCounter(0);
            this.setAttacking(false);
        }
    }

    /**
     * <h3>updateMonsterHealth</h3>
     * Updates the health of the monster based on the attack power of the hero.
     * @param monsterIndex The index of the monster in the array.
     */
    private void updateMonsterHealth(int monsterIndex) {
        if (this.hasEnchantedSword) { this.getMonsters()[monsterIndex].setHealth(this.getMonsters()[monsterIndex].getHealth() - 3); }
        else { this.getMonsters()[monsterIndex].setHealth(this.getMonsters()[monsterIndex].getHealth() - 1); }
    }

    /**
     * <h3>drawHero</h3>
     * Draws the hero on the screen with the current sprite and additional information, such as the hero's name.
     * @param graphics2D The graphics object to draw on.
     */
    public void drawHero(Graphics2D graphics2D) {
        // Configure SCREEN_X_POSITION, SCREEN_Y_POSITION To the center of the screen
        this.setSCREEN_X_POSITION((GAME_PANEL.getSCREEN_WIDTH() / 2) - (GAME_PANEL.getTILE_SIZE() / 2));
        this.setSCREEN_Y_POSITION((GAME_PANEL.getSCREEN_HEIGHT() / 2) - (GAME_PANEL.getTILE_SIZE() / 2));

        this.setUpCurrentHeroSprite(this, this.getDirection(), this.isAttacking());

        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 12));
        
        int stringLength = (int)graphics2D.getFontMetrics().getStringBounds(this.getName(), graphics2D).getWidth();
        int centerTextPosition = GAME_PANEL.getTILE_SIZE() - stringLength;
        graphics2D.drawString(this.getName(), this.getSCREEN_X_POSITION() + (centerTextPosition / 2), this.getSCREEN_Y_POSITION() - 10);
        
        // Change opacity level based on isInvisible status
        if (this.isInvisible()) { graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); }

        graphics2D.drawImage(this.getCurrentImage(), this.getTempScreenX(), this.getTempScreenY(), null);

        // Reset Alpha
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     * <h3>pickUpObject</h3>
     * Picks up an object at the specified index and performs the corresponding actions based on the object's type.
     * @param index The index of the object to be picked up.
     */
    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[index].getObjName();

            switch (objectName) {
                case "SpeedPotion" -> {
                    this.setENTITY_SPEED(this.getENTITY_SPEED() + 2);
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " took Speed Potion");
                    this.removeObjectFromPane(index);
                }
                case "HealPotion" -> {
                    if (this.getHealth() > 4){ this.setHealth(this.getMaximumHealth()); }
                    else { this.setHealth(this.getHealth() + 2); }
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " took Heal Potion");
                    this.removeObjectFromPane(index);
                }
                case "SpecialKey" -> {
                    this.setHasSpecialKey(this.getHasSpecialKey() + 1);
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " took Special Key");
                    this.removeObjectFromPane(index);
                }
                case "Key" -> {
                    this.setHasKey(this.getHasKey() + 1);
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " took Key");
                    this.removeObjectFromPane(index);
                }
                case "Gate" -> {
                    System.out.println("GATE");
                    if (this.getHasSpecialKey() == 1) {
                        GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " opened the gate and Won");
                        this.removeObjectFromPane(index);
                        this.setHasSpecialKey(this.getHasSpecialKey() - 1);
                        GAME_PANEL.setGameState(GAME_PANEL.getGameFinishedGameState());
                    }
                }
                case "Chest" -> {
                    System.out.println("Chest");
                    if (keyHandler.isEPressed()) {
                        System.out.println("Collision Chest + E");
                    }
                    if (this.getHasKey() == 1 && keyHandler.isEPressed()) {
                        GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " opened chest and found EnchantedSword");
                        this.setHasEnchantedSword(true);
                        // Player found the enchanted sword
                        showEnchantedSwordMessage = true;
                        enchantedSwordMessageStartTime = System.currentTimeMillis();
                        this.removeObjectFromPane(index);
                        this.setHasKey(this.getHasKey() - 1);
                    }
                }
                case "Map" -> {
                    this.removeObjectFromPane(index);
                    this.setHasMap(true);
                    GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("Player " + this.getName() + " found the map");
                }
            }
        }
    }

    /**
     * <h3>removeObjectFromPane</h3>
     * Removes the object at the specified index from the pane.
     * @param index The index of the object to be removed.
     */
    private void removeObjectFromPane(int index) { Hero.GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[index] = null; }



    // ----- GETTERS -----
    public HeroManager getHeroManager() { return HERO_MANAGER; }

    public int getSCREEN_X_POSITION() { return SCREEN_X_POSITION; }
    public int getSCREEN_Y_POSITION() { return SCREEN_Y_POSITION; }
    public int getHasKey() { return hasKey; }
    public int getHasSpecialKey() { return hasSpecialKey; }
    public boolean isHasMap() { return hasMap; }
    public boolean isShowEnchantedSwordMessage() { return showEnchantedSwordMessage; }
    public long getEnchantedSwordMessageStartTime() { return enchantedSwordMessageStartTime; }
    public boolean isHasEnchantedSword() { return hasEnchantedSword; }

    // CUSTOM
    private Monster[] getMonsters() { return GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getMONSTER_MANAGER().getMonsters(); }
    
    // ----- SETTERS -----
    public void setHeroManager(HeroManager heroManager) { HERO_MANAGER = heroManager; }

    public void setSCREEN_X_POSITION(int SCREEN_X_POSITION) { Hero.SCREEN_X_POSITION = SCREEN_X_POSITION; }
    public void setSCREEN_Y_POSITION(int SCREEN_Y_POSITION) { Hero.SCREEN_Y_POSITION = SCREEN_Y_POSITION; }
    public void setHasKey(int hasKey) { this.hasKey = hasKey; }
    public void setHasSpecialKey(int hasSpecialKey) { this.hasSpecialKey = hasSpecialKey; }

    public void setHasMap(boolean hasMap) { this.hasMap = hasMap; }
    public void setHasEnchantedSword(boolean hasEnchantedSword) { this.hasEnchantedSword = hasEnchantedSword; }
    public void setShowEnchantedSwordMessage(boolean showEnchantedSwordMessage) { this.showEnchantedSwordMessage = showEnchantedSwordMessage; }

}
