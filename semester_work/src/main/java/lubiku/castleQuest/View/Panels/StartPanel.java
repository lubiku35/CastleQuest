package lubiku.castleQuest.View.Panels;

import lubiku.castleQuest.View.Frames.AppFrame;
import lubiku.castleQuest.View.GameInterfaceUtils.InterfaceComponents;
import lubiku.castleQuest.View.Panels.LoadGamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * <h2>StartPanel</h2>
 * The "StartPanel" class is a JPanel subclass that represents the start panel of an application. It is responsible for configuring the panel's background, layout, and components.
 * */
public class StartPanel extends JPanel {

    // Interface Components
    private static final InterfaceComponents INTERFACE_COMPONENTS = new InterfaceComponents();

    // Parent Application
    private final AppFrame APP_FRAME;

    public StartPanel(AppFrame APP_FRAME) {
        this.APP_FRAME = APP_FRAME;
        this.setUpStartPanel();
    }

    /**
     * <h3>setUpStartPanel</h3>
     * Sets up the Start Panel of the application.
     * It configures the background, layout, components, and action listener for the start panel.
     */
    private void setUpStartPanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());

        // Create Components
        JTextField playerNameField = INTERFACE_COMPONENTS.createTextField(20);
        JButton startButton = INTERFACE_COMPONENTS.createButton("Enter the Game");
        JButton loadGameButton = INTERFACE_COMPONENTS.createButton("Load Game");

        // Handle Action Listener
        startButton.addActionListener(e -> handleStartButtonClick(playerNameField));
        loadGameButton.addActionListener(e -> handleLoadGameButtonClick());

        // Construct Grid
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, INTERFACE_COMPONENTS.createLabel("Enter Your nickname:"), 0, 0);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, playerNameField, 0, 1);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, startButton, 0, 2);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, loadGameButton, 0, 3);

        this.APP_FRAME.add(this);
        this.APP_FRAME.pack();
        this.APP_FRAME.setLocationRelativeTo(null);
    }

    /**
     * <h3>handleStartButtonClick</h3>
     * Handles the click event of the start button.
     * Retrieves the player's name from the text field and inits the chooseMapPanel if the nickname is valid.
     * @param playerNameField The text field containing the player's nickname.
     * @see ChooseMapPanel
     */
    private void handleStartButtonClick(JTextField playerNameField) {
        String playerName = playerNameField.getText();
        if (playerName.length() < 3 || playerName.length() > 14) {
            // Throw an Error Message
            JOptionPane.showMessageDialog(this, "Nickname must be between 3 and 14 characters long", "Invalid Nickname", JOptionPane.ERROR_MESSAGE);
        } else {
            this.APP_FRAME.getContentPane().removeAll();
            this.APP_FRAME.initializeChooseMapPanel(playerName);
            this.revalidate();
            this.repaint();
        }
    }

    private void handleLoadGameButtonClick() {
        this.APP_FRAME.getContentPane().removeAll();
        // Example: Create an instance of the LoadGamePanel and add it to the frame's content pane
        LoadGamePanel loadGamePanel = new LoadGamePanel(this.APP_FRAME);
        this.revalidate();
        this.repaint();
    }
}
