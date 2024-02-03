package lubiku.castleQuest.View;

import lubiku.castleQuest.Model.Objects.HeartGameObject;
import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class UI extends PanelUI {
    private GamePanel GAME_PANEL;

    // Fonts
    private Font consolasPlain14, consolasBold35;

    // Images
    private  BufferedImage keyImage;
    private  BufferedImage specialKeyImage;
    private  BufferedImage mapImage;
    private  BufferedImage health0, health50, health100;
    private  int imageArea;
    private double playTime;

    // For current dialogue
    private String currentDialogue = "";

    // Formatting decimal numbers
    private final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    // For menu
    private int commandNumber = 0;

    // ----- CONSTRUCTOR -----
    public UI() { }

    /**
     * <h3>setUpUI</h3>
     * Sets up the UI with the specified GamePanel.
     * @param GAME_PANEL The GamePanel instance.
     */
    public void setUpUI(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;

        this.setConsolasPlain14(new Font("Consolas", Font.PLAIN, 14));
        this.setConsolasBold35(new Font("Consolas", Font.BOLD, 35));

        this.keyImage = this.getKeyImage();
        this.imageArea = this.getImageArea();
        this.specialKeyImage = this.getSpecialKeyImage();
        this.mapImage = this.getMapImage();

        GameObject hearth = new HeartGameObject(GAME_PANEL);
        health0 = hearth.getObjImage1();
        health50 = hearth.getObjImage2();
        health100 = hearth.getObjImage3();
    }

    /**
     * <h3>draw</h3>
     * Draws the UI based on the current game state.
     * @param graphics2D The Graphics2D object to draw on.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(this.getConsolasPlain14());

        if (GAME_PANEL.getGameState() == GAME_PANEL.getNormalGameState()) { playTime += (double) 1/60; drawUIStatsElements(graphics2D); drawPlayerLife(graphics2D);}
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getPausedGameState()) { drawUIStatsElements(graphics2D); drawUIBlackBlurPausingFrame(graphics2D); drawPlayerLife(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getDialogueGameState()) { drawDialogueScreen(graphics2D); drawPlayerLife(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getTitleScreenState()) { drawTitleScreen(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getCharacterGameState()) { drawCharacterScreen(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getGameOverGameState()) { drawGameOverScreen(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getGameFinishedGameState()) { setGameFinishedScreen(graphics2D); }
        else if (GAME_PANEL.getGameState() == GAME_PANEL.getMapGameState()) { setMapScreen(graphics2D, GAME_PANEL.getMAIN_CONTROLLER().getMAP_NAME()); }
    }

    /**
     * <h3>setMapScreen</h3>
     * Sets the map screen and displays the specified map image.
     * @param graphics2D The Graphics2D object to draw on.
     * @param mapName    The name of the map.
     */
    private void setMapScreen(Graphics2D graphics2D, String mapName) {
        BufferedImage mapImage = null;
        switch (mapName) {
            case "DARK LABYRINTH":
                try { mapImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Maps/dark_labyrinth.jpg")));
                } catch (IOException e) { throw new RuntimeException(e); }
            case "HALLS OF ECHOS":
                try { mapImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Maps/halls_of_echos.jpg")));
                } catch (IOException e) { throw new RuntimeException(e); }
        }

        // Character Image
        graphics2D.drawImage(
                mapImage,
                (GAME_PANEL.getSCREEN_WIDTH() / 2) - (GAME_PANEL.getTILE_SIZE() * 3),
                (GAME_PANEL.getSCREEN_HEIGHT() / 2) - (GAME_PANEL.getTILE_SIZE() * 3),
                GAME_PANEL.getTILE_SIZE() * 6,
                GAME_PANEL.getTILE_SIZE() * 6,
                null
        );
    }


    /**
     * <h3>setGameFinishedScreen</h3>
     * Sets the game finished screen and displays the player's victory.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void setGameFinishedScreen(Graphics2D graphics2D) {
        GAME_PANEL.setGameThread(null);

        // Draw semi-transparent rectangle over the entire screen
        drawSemiTransparentRectangle(graphics2D);


        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 72));
        graphics2D.setColor(new Color(139, 69, 19));

        // Heading
        graphics2D.drawString(
                "YOU WIN",
                getXCenteredText("YOU WIN", graphics2D),
                GAME_PANEL.getTILE_SIZE() * 3
        );

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 22));
        graphics2D.setColor(new Color(255, 255, 255));

        String playingTime = "Playing Time: " + decimalFormat.format(playTime);

        graphics2D.drawString(
                "Playing Time: " + decimalFormat.format(playTime),
                getXCenteredText(playingTime, graphics2D),
                (GAME_PANEL.getSCREEN_HEIGHT() / 2) + (GAME_PANEL.getSCREEN_HEIGHT() / 3)
        );

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32));

        // New Game 'button'
        String text = "NEW GAME";
        int x = getXCenteredText(text, graphics2D);
        int y = this.GAME_PANEL.getTILE_SIZE() * 7;
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 0) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        // MapMenu 'button'
        text = "BACK TO MAP MENU";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 1) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        // Exit 'button'
        text = "EXIT";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 2) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }
    }

    /**
     * <h3>drawGameOverScreen</h3>
     * Draws the game over screen on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void drawGameOverScreen(Graphics2D graphics2D) {
        // Draw semi-transparent rectangle over the entire screen
        drawSemiTransparentRectangle(graphics2D);



        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 72));
        graphics2D.setColor(new Color(139, 69, 19));

        // Heading
        graphics2D.drawString(
                "GAME OVER",
                getXCenteredText("GAME OVER", graphics2D),
                GAME_PANEL.getTILE_SIZE() * 3
        );

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32));
        graphics2D.setColor(new Color(255, 255, 255));

        // New Game 'button'
        String text = "RETRY";
        int x = getXCenteredText(text, graphics2D);
        int y = this.GAME_PANEL.getTILE_SIZE() * 7;
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 0) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        // Load Game 'button'
        text = "BACK TO MAP MENU";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 1) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        // Load Game 'button'
        text = "EXIT";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 2) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }
    }

    /**
     * <h3>drawCharacterScreen</h3>
     * Draws the character screen on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void drawCharacterScreen(Graphics2D graphics2D) {
        // Draw semi-transparent rectangle over the entire screen
        drawSemiTransparentRectangle(graphics2D);

        graphics2D.setColor(new Color(0, 0, 0, 200));
        graphics2D.fillRect(
                GAME_PANEL.getSCREEN_WIDTH() / 8,
                0,
                GAME_PANEL.getSCREEN_WIDTH() / 2 + (GAME_PANEL.getSCREEN_WIDTH() / 4),
                GAME_PANEL.getSCREEN_HEIGHT());

        BufferedImage swordImage = null;
        try { swordImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/sword.png")));
        } catch (IOException e) { throw new RuntimeException(e); }

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 38));
        graphics2D.setColor(new Color(139, 69, 19));

        // Heading
        graphics2D.drawString(
                GAME_PANEL.getHero().getName() + "'s Inventory",
                getXCenteredText(GAME_PANEL.getHero().getName() + "'s Inventory", graphics2D),
                GAME_PANEL.getTILE_SIZE() * 2
        );

        // Character Image
        graphics2D.drawImage(
                this.GAME_PANEL.getHero().getImageDown1(),
                (GAME_PANEL.getSCREEN_WIDTH() / 2) - (GAME_PANEL.getTILE_SIZE() / 2),
                GAME_PANEL.getTILE_SIZE() * 3, GAME_PANEL.getTILE_SIZE(), GAME_PANEL.getTILE_SIZE(),
                null
        );

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 22));
        graphics2D.setColor(new Color(255,255,255));

        // Weapon
        graphics2D.drawString(
                "Weapon: ",
                GAME_PANEL.getTILE_SIZE() * 3,
                GAME_PANEL.getTILE_SIZE() * 5
        );

        graphics2D.drawImage(
                swordImage,
                (GAME_PANEL.getTILE_SIZE() * 5),
                GAME_PANEL.getTILE_SIZE() * 4 + (GAME_PANEL.getTILE_SIZE() / 2),
                GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                null
        );

        // Map
        graphics2D.drawString(
                "Map: ",
                GAME_PANEL.getTILE_SIZE() * 3,
                GAME_PANEL.getTILE_SIZE() * 6
        );

        if (GAME_PANEL.getHero().isHasMap()) {
            graphics2D.drawImage(
                    this.mapImage,
                    (GAME_PANEL.getTILE_SIZE() * 5),
                    GAME_PANEL.getTILE_SIZE() * 5 + (GAME_PANEL.getTILE_SIZE() / 2),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    null
            );
        } else {
            graphics2D.drawString(
                    "X",
                    GAME_PANEL.getTILE_SIZE() * 5,
                    GAME_PANEL.getTILE_SIZE() * 6
            );
        }

        // Keys
        graphics2D.drawString(
                "Chest Key: ",
                GAME_PANEL.getTILE_SIZE() * 3,
                GAME_PANEL.getTILE_SIZE() * 7
        );

        if (this.getHeroKeyStat() >= 1) {
            graphics2D.drawImage(
                    this.keyImage,
                    (GAME_PANEL.getTILE_SIZE() * 6),
                    GAME_PANEL.getTILE_SIZE() * 6 + (GAME_PANEL.getTILE_SIZE() / 2),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    null
            );
        } else {
            graphics2D.drawString(
                    "X",
                    GAME_PANEL.getTILE_SIZE() * 6,
                    GAME_PANEL.getTILE_SIZE() * 7
            );
        }

        // SpecialKeys
        graphics2D.drawString(
                "Special Key: ",
                GAME_PANEL.getTILE_SIZE() * 3,
                GAME_PANEL.getTILE_SIZE() * 8
        );

        if (this.getHeroSpecialKeyStat() >= 1) {
            graphics2D.drawImage(
                    this.keyImage,
                    (GAME_PANEL.getTILE_SIZE() * 6),
                    GAME_PANEL.getTILE_SIZE() * 7 + (GAME_PANEL.getTILE_SIZE() / 2),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    GAME_PANEL.getTILE_SIZE() / 2 + (GAME_PANEL.getTILE_SIZE() / 4),
                    null
            );
        } else {
            graphics2D.drawString(
                    "X",
                    GAME_PANEL.getTILE_SIZE() * 7,
                    GAME_PANEL.getTILE_SIZE() * 8
            );
        }
    }

    /**
     * <h3>drawUIBlackBlurPausingFrame</h3>
     * Draws the black blur pausing frame on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void drawUIBlackBlurPausingFrame(Graphics2D graphics2D) {
        // Draw semi-transparent rectangle over the entire screen
        drawSemiTransparentRectangle(graphics2D);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32));
        graphics2D.setColor(new Color(139, 69, 19));
        String gamePaused = "GAME PAUSED";

        graphics2D.drawString(gamePaused, getXCenteredText(gamePaused, graphics2D),  (GAME_PANEL.getTILE_SIZE() * 2));

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32));
        graphics2D.setColor(new Color(255, 255, 255));

        String text = "CONTINUE";
        int x = getXCenteredText(text, graphics2D);
        int y = this.GAME_PANEL.getTILE_SIZE() * 7;
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 0) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        text = "SAVE";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 1) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        text = "BACK TO MAP MENU";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 2) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }

        text = "EXIT";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 3) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }
    }

    /**
     * <h3>drawUIStatsElements</h3>
     * Draws the UI stats elements on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void drawUIStatsElements(Graphics2D graphics2D) {
        graphics2D.drawString("Time: " + decimalFormat.format(playTime), GAME_PANEL.getSCREEN_WIDTH() - imageArea  * 2, imageArea);

        graphics2D.drawImage(keyImage, imageArea / 2, imageArea / 2, imageArea / 2, imageArea / 2, null);
        graphics2D.drawString(" " + this.getHeroKeyStat(), imageArea + 5, imageArea - 7);

        graphics2D.drawImage(specialKeyImage, imageArea / 2, imageArea + 10, imageArea / 2, imageArea / 2, null);
        graphics2D.drawString(" " + this.getHeroSpecialKeyStat(), imageArea + 5, imageArea + (imageArea / 2) + 3);

        this.showEnchantedSwordMessage(graphics2D);
    }

    /**
     * <h3>drawDialogueScreen</h3>
     * Draws the dialogue screen on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void drawDialogueScreen(Graphics2D graphics2D) {
        // Dialogue Window Preferences
        int x = GAME_PANEL.getTILE_SIZE()*2;
        int y = GAME_PANEL.getTILE_SIZE()/2;
        int width = GAME_PANEL.getSCREEN_WIDTH() - (GAME_PANEL.getTILE_SIZE()*4);
        int height = GAME_PANEL.getTILE_SIZE()*4;
        drawDialogueScreenWindow(graphics2D, x, y, width, height);

        x += GAME_PANEL.getTILE_SIZE();
        y += GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(currentDialogue, x, y);
    }

    /**
     * <h3>drawDialogueScreenWindow</h3>
     * Draws the dialogue screen window on the screen.
     * @param graphics2D The Graphics2D object to draw on.
     * @param x          The x-coordinate of the window.
     * @param y          The y-coordinate of the window.
     * @param width      The width of the window.
     * @param height     The height of the window.
     */
    private void drawDialogueScreenWindow(Graphics2D graphics2D, int x, int y, int width, int height) {
        Color color = new Color(0,0,10, 150);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 15,15);

        graphics2D.setColor(new Color(139, 69, 19));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(x+3, y+3, width-7, height-7, 15,15);
    }

    /**
    * <h3>drawPlayerLife</h3>
    * Draws the player's life hearts on the screen.
    * Draws blank hearts for remaining maximum health, and full or half-filled hearts for current health, up to maximum health.
    * @param graphics2D the Graphics2D object to draw the player's life on
    */
    private void drawPlayerLife(Graphics2D graphics2D) {
        int x = this.GAME_PANEL.getSCREEN_WIDTH() / 2 - 32;
        int y = this.GAME_PANEL.getTILE_SIZE();
        int i = 0;

        // DRAW BLANK HEARTS
        while (i < GAME_PANEL.getHero().getMaximumHealth() / 2) {
            graphics2D.drawImage(health0, x, y, null);
            i++;
            x+=this.GAME_PANEL.getTILE_SIZE() / 2;
        }

        x = this.GAME_PANEL.getSCREEN_WIDTH() / 2 - 32;
        y = this.GAME_PANEL.getTILE_SIZE();
        i = 0;

        // DRAW BLANK HEARTS
        while (i < GAME_PANEL.getHero().getHealth()) {
            graphics2D.drawImage(health50, x, y, null);
            i++;
            if (i < GAME_PANEL.getHero().getHealth()) { graphics2D.drawImage(health100, x, y, null); }
            i++;
            x+=this.GAME_PANEL.getTILE_SIZE() / 2;
        }
    }

    // --- TITLE SCREEN ---

    /**
     * <h3>drawTitleScreen</h3>
     * Draws the title screen with the game menu.
     * @param graphics2D the Graphics2D object to draw on
     */
    private void drawTitleScreen(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, this.GAME_PANEL.getSCREEN_WIDTH(), this.GAME_PANEL.getSCREEN_HEIGHT());
        createMenuGraphics(graphics2D);
    }

    /**
     * <h3>createMenuGraphics</h3>
     * Creates the graphics for the main menu screen.
     * @param graphics2D the graphics object to use for drawing
     */
    private void createMenuGraphics(Graphics2D graphics2D) {
        // TitleName
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String text = "CastleQuest";
        int x = getXCenteredText(text, graphics2D);
        int y = this.GAME_PANEL.getTILE_SIZE() * 3;

        // MAIN TEXT
        graphics2D.setColor(new Color(139, 69, 19));
        graphics2D.drawString(text, x, y);



        // HERO IMAGE
        x = this.GAME_PANEL.getSCREEN_WIDTH() / 2 - ((this.GAME_PANEL.getTILE_SIZE() * 2) / 2 );
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawImage(this.GAME_PANEL.getHero().getImageDown1(), x, y, GAME_PANEL.getTILE_SIZE() * 2, GAME_PANEL.getTILE_SIZE() * 2, null);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32));
        graphics2D.setColor(new Color(255, 255, 255));

        // New Game 'button'
        text = "NEW GAME";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE() * 3.5;
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 0) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }


        // Quit Game 'button'
        text = "QUIT";
        x = getXCenteredText(text, graphics2D);
        y += this.GAME_PANEL.getTILE_SIZE();
        graphics2D.drawString(text, x, y);
        if (this.commandNumber == 1) { graphics2D.drawString(">", x - GAME_PANEL.getTILE_SIZE(), y); }
    }

    /**
     * <h3>showEnchantedSwordMessage</h3>
     * Shows the enchanted sword message on the screen if it needs to be displayed.
     * @param graphics2D The Graphics2D object to draw on.
     */
    private void showEnchantedSwordMessage(Graphics2D graphics2D) {
        if (this.GAME_PANEL.getHero().isShowEnchantedSwordMessage()) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - this.GAME_PANEL.getHero().getEnchantedSwordMessageStartTime();

            // MENU
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28));
            graphics2D.setColor(new Color(255, 255, 255));

            // Draw the on-screen message
            drawCenteredXString(graphics2D, "Player " + this.GAME_PANEL.getHero().getName() + " found the Enchanted Sword!", this.GAME_PANEL.getSCREEN_HEIGHT() / 2);

            // Check if 2 seconds have passed
            if (elapsedTime >= 3500) {
                this.GAME_PANEL.getHero().setShowEnchantedSwordMessage(false);
            } else {
                // Calculate the transparency based on the elapsed time (for fading animation)
                float transparency = 1.0f - (float) elapsedTime / 3500.0f; // Adjust the division factor for desired animation speed
                transparency = Math.max(0.0f, Math.min(1.0f, transparency)); // Ensure the transparency value is within range

                // Set the transparency on the graphics context
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
                graphics2D.setComposite(alphaComposite);

                // Draw the message with the updated transparency
                drawCenteredXString(graphics2D, "Player " + this.GAME_PANEL.getHero().getName() + " found the Enchanted Sword!", this.GAME_PANEL.getSCREEN_HEIGHT() / 2);

                // Reset the transparency to fully opaque
                alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
                graphics2D.setComposite(alphaComposite);
            }
        }
    }

    // --- GLOBAL ---

    /**
     * <h3>getXCenteredText</h3>
     * Calculates the x-coordinate for drawing text centered on the screen horizontally.
     * @param text the text to be drawn
     * @param graphics2D the Graphics2D object to be used for drawing
     * @return the x-coordinate for drawing the text centered horizontally
     */
    private int getXCenteredText(String text, Graphics2D graphics2D) {
        int x = this.GAME_PANEL.getSCREEN_WIDTH() / 2;
        int stringLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return x - (stringLength / 2);
    }

    private void drawSemiTransparentRectangle(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 0, 0, 128)); // 128 is the alpha value for semi-transparency
        graphics2D.fillRect(0, 0, GAME_PANEL.getSCREEN_WIDTH(), GAME_PANEL.getSCREEN_HEIGHT());
    }

    private void drawCenteredXString(Graphics2D graphics2D, String string, int y) {
        graphics2D.drawString(
                string,
                getXCenteredText(string, graphics2D),
                y
        );
    }


    // To remove Long Code purposes
    private int getHeroKeyStat() { return GAME_PANEL.getHero().getHasKey(); }
    private int getHeroSpecialKeyStat() { return GAME_PANEL.getHero().getHasSpecialKey(); }
    public BufferedImage getKeyImage() { return GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[0].getObjImage1(); }
    public BufferedImage getSpecialKeyImage() { return GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[1].getObjImage1(); }
    public BufferedImage getMapImage() { return GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[4].getObjImage1(); }
    public int getImageArea() { return GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getOBJECT_MANAGER().getObjects()[0].getObjectSolidArea().width; }


    // ----- GETTERS -----
    public Font getConsolasPlain14() { return consolasPlain14; }
    public Font getConsolasBold35() { return consolasBold35; }

    // CommandNumber
    public int getCommandNumber() { return commandNumber; }


    // ----- SETTERS -----

    // PlayTime
    public void setPlayTime(double playTime) { this.playTime = playTime; }

    public void setGAME_PANEL(GamePanel GAME_PANEL) { this.GAME_PANEL = GAME_PANEL; }

    public void setConsolasPlain14(Font consolasPlain14) { this.consolasPlain14 = consolasPlain14; }
    public void setConsolasBold35(Font consolasBold35) { this.consolasBold35 = consolasBold35; }


    // Current Dialogue
    public void setCurrentDialogue(String currentDialogue) { this.currentDialogue = currentDialogue; }

    // CommandNumber
    public void setCommandNumber(int commandNumber) { this.commandNumber = commandNumber; }
}
