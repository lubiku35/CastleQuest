package lubiku.castleQuest.Controller.Handlers;

import lubiku.castleQuest.View.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>KeyHandler</h2>
 * The KeyHandler class is responsible for managing the keyboard input in the game.
 * It implements the KeyListener interface to handle key events and provides methods for checking if specific keys are pressed / released.
 */
public class KeyHandler implements KeyListener {
    private final Map<Integer, Boolean> keyMap = new HashMap<>();

    private final GamePanel GAME_PANEL;

    /**
     * <h3>KeyHandler</h3>
     * <p>Constructs a new KeyHandler object and initializes the map of keys to their default state <i>'false'</i></p>
     */
    public KeyHandler(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;

        // Movement Keys
        keyMap.put(KeyEvent.VK_W, false);
        keyMap.put(KeyEvent.VK_A, false);
        keyMap.put(KeyEvent.VK_S, false);
        keyMap.put(KeyEvent.VK_D, false);

        // Open Chest Key
        keyMap.put(KeyEvent.VK_E, false);

        // Pause Game Key
        keyMap.put(KeyEvent.VK_P, false);

        // Map Game Key
        keyMap.put((KeyEvent.VK_M), false);

        keyMap.put(KeyEvent.VK_ENTER, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * <h3>keyPressed</h3>
     * Handles the key press events in the game.
     * @param e The KeyEvent object representing the key press event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getNormalGameState()) { handleNormalGameState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getPausedGameState()) { handlePausedGameState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getCharacterGameState()) { handleCharacterGameState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getDialogueGameState()) { handleDialogueGameState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getTitleScreenState()) { handleTitleScreenState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getMapGameState()) { handleMapGameState(keyCode); }
        else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getGameOverGameState()) {
            handleGameOverState(keyCode);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("GAME OVER");
        } else if (this.GAME_PANEL.getGameState() == this.GAME_PANEL.getGameFinishedGameState()) {
            handleGameFinishedState(keyCode);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("GAME FINISHED");
        }
    }

    /**
     * <h3>handleNormalGameState</h3>
     * Handles key press events in the NormalGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleNormalGameState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getPausedGameState()); }
        if (keyMap.containsKey(keyCode)) { keyMap.put(keyCode, true); }
        if (keyCode == KeyEvent.VK_Q) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getCharacterGameState()); }
        if (keyCode == KeyEvent.VK_M && this.GAME_PANEL.getHero().isHasMap()) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getMapGameState()); }
    }


    /**
     * <h3>handlePausedGameState</h3>
     * Handles key press events in the PausedGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handlePausedGameState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState());
        }
        if (keyMap.containsKey(keyCode)) {
            keyMap.put(keyCode, true);
        }

        if (keyCode == KeyEvent.VK_DOWN) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() + 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() > 3) {
                this.GAME_PANEL.getUI().setCommandNumber(3);
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() - 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() < 0) {
                this.GAME_PANEL.getUI().setCommandNumber(0);
            }
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            handlePausedGameStateCommands();
        }
    }

    /**
     * <h3>handlePausedGameStateCommands</h3>
     * Handles the commands in the PausedGameState.
     */
    private void handlePausedGameStateCommands() {
        int commandNumber = this.GAME_PANEL.getUI().getCommandNumber();
        if (commandNumber == 0) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState()); }
        else if (commandNumber == 1) { this.GAME_PANEL.getMAIN_CONTROLLER().provideSaveGame(); }
        else if (commandNumber == 2) {
            if (this.GAME_PANEL.getMAIN_CONTROLLER().getPLAYER_NAME() != null || !Objects.equals(this.GAME_PANEL.getMAIN_CONTROLLER().getPLAYER_NAME(), ""))
            {
                this.GAME_PANEL.getMAIN_CONTROLLER().backToChooseMapPanel(this.GAME_PANEL.getMAIN_CONTROLLER().getPLAYER_NAME());
            }  else {
                this.GAME_PANEL.getMAIN_CONTROLLER().backToChooseMapPanel(this.GAME_PANEL.getHero().getName());

            }
        }
        else if (commandNumber == 3) {
            System.exit(0);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("SYSTEM EXIT");
        }
    }


    /**
     * <h3>handleCharacterGameState</h3>
     * Handles key press events in the CharacterGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleCharacterGameState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState()); }
        if (keyMap.containsKey(keyCode)) { keyMap.put(keyCode, true); }
    }

    /**
     * <h3>handleMapGameState</h3>
     * Handles key press events in the CharacterGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleMapGameState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState()); }
        if (keyMap.containsKey(keyCode)) { keyMap.put(keyCode, true); }
    }

    /**
     * <h3>handleDialogueGameState</h3>
     * Handles key press events in the DialogueGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleDialogueGameState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState()); }
        if (keyMap.containsKey(keyCode)) { keyMap.put(keyCode, true); }
    }

    /**
     * <h3>handleTitleScreenState</h3>
     * Handles key press events in the TitleScreenState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleTitleScreenState(int keyCode) {
        if (keyCode == KeyEvent.VK_DOWN) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() + 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() > 1) { this.GAME_PANEL.getUI().setCommandNumber(1); }
        }
        if (keyCode == KeyEvent.VK_UP) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() - 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() < 0) { this.GAME_PANEL.getUI().setCommandNumber(0); }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            handleTitleScreenStateCommands();
        }
    }

    /**
     * <h3>handleTitleScreenStateCommands</h3>
     * Handles the commands in the TitleScreenState.
     */
    private void handleTitleScreenStateCommands() {
        if (this.GAME_PANEL.getUI().getCommandNumber() == 0) { this.GAME_PANEL.setGameState(this.GAME_PANEL.getNormalGameState()); }
        if (this.GAME_PANEL.getUI().getCommandNumber() == 1) {
            System.exit(0);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("SYSTEM EXIT");
        }
    }

    /**
     * <h3>handleGameOverState</h3>
     * Handles key press events in the GameOverGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleGameOverState(int keyCode) {
        if (keyCode == KeyEvent.VK_DOWN) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() + 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() > 2) { this.GAME_PANEL.getUI().setCommandNumber(2); }
        }
        if (keyCode == KeyEvent.VK_UP) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() - 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() < 0) { this.GAME_PANEL.getUI().setCommandNumber(0); }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            handleGameOverStateCommands();
        }
    }

    /**
     * <h3>handleGameOverStateCommands</h3>
     * Handles the commands in the GameOverGameState.
     */
    private void handleGameOverStateCommands() {
        if (this.GAME_PANEL.getUI().getCommandNumber() == 0) {
            this.GAME_PANEL.getMAIN_CONTROLLER().restartGame();
        }
        if (this.GAME_PANEL.getUI().getCommandNumber() == 1) {
            this.GAME_PANEL.getMAIN_CONTROLLER().backToChooseMapPanel(this.GAME_PANEL.getMAIN_CONTROLLER().getPLAYER_NAME());
        }
        if (this.GAME_PANEL.getUI().getCommandNumber() == 2) {
            System.exit(0);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("SYSTEM EXIT");
        }
    }

    /**
     * <h3>handleGameFinishedState</h3>
     * Handles key press events in the GameFinishedGameState.
     * @param keyCode The key code of the pressed key.
     */
    private void handleGameFinishedState(int keyCode) {
        if (keyCode == KeyEvent.VK_DOWN) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() + 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() > 2) {
                this.GAME_PANEL.getUI().setCommandNumber(2);
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            this.GAME_PANEL.getUI().setCommandNumber(this.GAME_PANEL.getUI().getCommandNumber() - 1);
            if (this.GAME_PANEL.getUI().getCommandNumber() < 0) {
                this.GAME_PANEL.getUI().setCommandNumber(0);
            }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            handleGameFinishedStateCommands();
        }
    }

    /**
     * <h3>handleGameFinishedStateCommands</h3>
     * Handles the commands in the GameFinishedGameState.
     */
    private void handleGameFinishedStateCommands() {
        int commandNumber = this.GAME_PANEL.getUI().getCommandNumber();
        if (commandNumber == 0) {
            this.GAME_PANEL.getMAIN_CONTROLLER().restartGame();
        }
        else if (commandNumber == 1) {
            this.GAME_PANEL.getMAIN_CONTROLLER().backToChooseMapPanel(this.GAME_PANEL.getMAIN_CONTROLLER().getPLAYER_NAME());
        }
        else if (commandNumber == 2) {
            System.exit(0);
            GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("SYSTEM EXIT");
        }

    }


    /**
     * Invoked when a key is released.
     * Sets the value of the corresponding key in the map to false.
     * @param e The key event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyMap.containsKey(keyCode)) { keyMap.put(keyCode, false); }
    }


    // ----- GETTERS -----

    // Movement

    /**
     * Checks if the "W" key is pressed.
     * @return True if the "W" key is pressed, false otherwise.
     */
    public boolean isUpPressed() { return keyMap.get(KeyEvent.VK_W); }
    /**
     * Checks if the "S" key is pressed.
     * @return True if the "S" key is pressed, false otherwise.
     */
    public boolean isDownPressed() { return keyMap.get(KeyEvent.VK_S); }
    /**
     * Checks if the "A" key is pressed.
     * @return True if the "A" key is pressed, false otherwise.
     */
    public boolean isLeftPressed() { return keyMap.get(KeyEvent.VK_A); }
    /**
     * Checks if the "D" key is pressed.
     * @return True if the "D" key is pressed, false otherwise.
     */
    public boolean isRightPressed() { return keyMap.get(KeyEvent.VK_D); }

    // Other
    /**
     * Checks if the "E" key is pressed.
     * @return True if the "E" key is pressed, false otherwise.
     */
    public boolean isEPressed() { return keyMap.get(KeyEvent.VK_E); }
    /**
     * Checks if the "P" key is pressed.
     * @return True if the "P" key is pressed, false otherwise.
     */
    public boolean isPPressed() { return keyMap.get(KeyEvent.VK_ESCAPE); }
    /**
     * Checks if the "ENTER" key is pressed.
     * @return True if the "ENTER" key is pressed, false otherwise.
     */
    public boolean isENTERPressed() { return keyMap.get(KeyEvent.VK_ENTER); }
    /**
     * Checks if the "M" key is pressed.
     * @return True if the "M" key is pressed, false otherwise.
     */
    public boolean isMPressed() { return keyMap.get(KeyEvent.VK_M); }

    public Map<Integer, Boolean> getKeyMap() { return keyMap; }
}
