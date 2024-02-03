package lubiku.castleQuest.View.Panels;

import lubiku.castleQuest.View.Frames.AppFrame;
import lubiku.castleQuest.View.GameInterfaceUtils.InterfaceComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * <h2>ChooseMapPanel</h2>
 * The ChooseMapPanel class represents a panel where the player can choose a map for the game.
 * It allows the player to select from available maps and initiates the game with the chosen map.
 */
public class ChooseMapPanel extends JPanel {

    // Interface Components
    private static final InterfaceComponents INTERFACE_COMPONENTS = new InterfaceComponents();

    // Parent Application
    private final AppFrame APP_FRAME;

    // Player Name
    private final String PLAYER_NAME;

    /**
     * <h3>ChooseMapPanel</h3>
     * Constructs a ChooseMapPanel object.
     * @param APP_FRAME    The parent application frame.
     * @param PLAYER_NAME  The name of the player.
     */
    public ChooseMapPanel(AppFrame APP_FRAME, String PLAYER_NAME) {
        this.PLAYER_NAME = PLAYER_NAME;
        this.APP_FRAME = APP_FRAME;
        this.setUpChooseMapPanel();
        this.APP_FRAME.getMAIN_CONTROLLER().getLOGGER().log("ChooseMapPanel successfully initialized.");
    }

    /**
     * <h3>setUpChooseMapPanel</h3>
     * Sets up the ChooseMapPanel by configuring the layout, adding components, and setting up event handlers.
     */
    private void setUpChooseMapPanel() {
        // SetUp Layout
        this.setLayout(new BorderLayout(50, 50));
        this.setBackground(Color.BLACK);

        // SetUp chooseMapLabel
        JLabel chooseMapLabel = INTERFACE_COMPONENTS.createLabel("Choose a Map");

        // Add margin to the top of chooseMapLabel
        int topMargin = 250;
        chooseMapLabel.setBorder(new EmptyBorder(topMargin, 0, 0, 0));
        chooseMapLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(chooseMapLabel, BorderLayout.NORTH);

        JPanel buttonsSubPanel = createButtonsSubPanel();
        this.add(buttonsSubPanel, BorderLayout.CENTER);

        APP_FRAME.add(this);
        APP_FRAME.pack();
        APP_FRAME.setLocationRelativeTo(null);
    }

    /**
     * <h3>createButtonsSubPanel</h3>
     * Creates the sub-panel containing the map selection buttons.
     * @return The buttons sub-panel.
     */
    private JPanel createButtonsSubPanel() {
        JPanel buttonsSubPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 50px horizontal gap
        buttonsSubPanel.setBackground(Color.BLACK);

        JButton map1Button = INTERFACE_COMPONENTS.createButton("DARK LABYRINTH");
        map1Button.addActionListener(e -> handleMap1Button());

        JButton map2Button = INTERFACE_COMPONENTS.createButton("HALLS OF ECHOS");
        map2Button.addActionListener(e -> handleMap2Button());

        buttonsSubPanel.add(map1Button);
        buttonsSubPanel.add(map2Button);

        return buttonsSubPanel;
    }

    /**
     * <h3>handleMap1Button</h3>
     * Handles the click event for the MAP 1 button.
     * Initiates the game with MAP1 configuration.
     */
    private void handleMap1Button() {
        this.APP_FRAME.getMAIN_CONTROLLER().getLOGGER().log("Player choice: DARK LABYRINTH, return to MainController - CONFIG_FILE: dark_labyrinth.json");
        this.handleMainController("DARK LABYRINTH", "Config/dark_labyrinth.json");
        this.revalidate();
        this.repaint();
        this.APP_FRAME.setVisible(false);
    }

    /**
     * <h3>handleMap2Button</h3>
     * Handles the click event for the MAP 2 button.
     * Initiates the game with MAP2 configuration.
     */
    private void handleMap2Button() {
        this.APP_FRAME.getMAIN_CONTROLLER().getLOGGER().log("Player choice: HALLS OF ECHOS, return to MainController - CONFIG_FILE: halls_of_echos.json");
        this.handleMainController("HALLS OF ECHOS", "Config/halls_of_echos.json");
        this.revalidate();
        this.repaint();
        this.APP_FRAME.setVisible(false);
    }

    /**
     * <h3>handleMainController</h3>
     * Handles the main controller by setting the chosen map name, player name, and game configuration file.
     * @param MAP_NAME     The name of the chosen map.
     * @param CONFIG_FILE  The file path of the game configuration.
     */
    private void handleMainController(String MAP_NAME, String CONFIG_FILE) {
        this.APP_FRAME.getMAIN_CONTROLLER().setMAP_NAME(MAP_NAME);
        this.APP_FRAME.getMAIN_CONTROLLER().setPLAYER_NAME(this.PLAYER_NAME);
        this.APP_FRAME.getMAIN_CONTROLLER().setGAME_CONFIG_FILE(CONFIG_FILE);
        this.APP_FRAME.getMAIN_CONTROLLER().initializeGameFrame(false);
    }
}
