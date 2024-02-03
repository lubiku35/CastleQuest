package lubiku.castleQuest.View.Panels;

import lubiku.castleQuest.View.Frames.AppFrame;
import lubiku.castleQuest.View.GameInterfaceUtils.InterfaceComponents;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * <h2>LoadGamePanel</h2>
 * The LoadGamePanel class is a JPanel subclass that represents the panel for loading a game configuration file.
 * */
public class LoadGamePanel extends JPanel {
    private static final InterfaceComponents INTERFACE_COMPONENTS = new InterfaceComponents();
    private JButton startButton;
    private JButton backButton;
    private JComboBox<String> configFileComboBox;
    private final AppFrame APP_FRAME;

    public LoadGamePanel(AppFrame APP_FRAME) {
        this.APP_FRAME = APP_FRAME;

        this.setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        System.out.println(getClass().getClassLoader());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Configuration File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

        // Determine the appropriate directory based on the operating system
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        String configDirectory;
        if (os.contains("win")) {
            configDirectory = System.getenv("TEMP") + File.separator + "CastleQuestSaves";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            configDirectory = System.getProperty("java.io.tmpdir") + File.separator + "CastleQuestSaves";
        } else {
            // Use a default directory if the operating system is not recognized
            configDirectory = "."; // Set the appropriate default directory path
        }

        File configDir = new File(configDirectory);
        if (!configDir.exists()) { configDir.mkdirs(); }

        fileChooser.setCurrentDirectory(configDir);
        // Create the label and combo box for the configuration file selection
        JLabel select = INTERFACE_COMPONENTS.createLabel("Select a configuration file:");
        configFileComboBox = new JComboBox<>();

        // Add the available config files to the combo box
        File[] configFiles = getAvailableConfigFiles(configDir);
        for (File configFile : configFiles) {
            if (!configFile.getName().equals("dark_labyrinth.json") && !configFile.getName().equals("halls_of_echos.json") && !configFile.getName().equals("MainWindow.json")) {
                configFileComboBox.addItem(configFile.getName());
            }
        }

        // Create the start and back buttons
        startButton = INTERFACE_COMPONENTS.createButton("Start");
        backButton = INTERFACE_COMPONENTS.createButton("Back");

        INTERFACE_COMPONENTS.addGridComponentToPanel(this, select, 0, 0);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, configFileComboBox, 0, 1);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, startButton, 0, 2);
        INTERFACE_COMPONENTS.addGridComponentToPanel(this, backButton, 0, 3);

        // Add action listeners to the buttons
        startButton.addActionListener(e -> handleStartButtonClick(configDir));;

        backButton.addActionListener(e -> handleBackButtonClick());

        this.APP_FRAME.add(this);

        this.APP_FRAME.pack();
        this.APP_FRAME.setLocationRelativeTo(null);
    }

    // Helper method to get the available config files in the current directory
    private File[] getAvailableConfigFiles(File directory) {
        File[] configFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        return configFiles != null ? configFiles : new File[0];
    }

    private void handleBackButtonClick() {
        this.APP_FRAME.getContentPane().removeAll();
        this.APP_FRAME.initializeStartPanel();
        this.revalidate();
        this.repaint();
    }

    private void handleStartButtonClick(File directory) {
        String selectedConfigFile = (String) configFileComboBox.getSelectedItem();
        assert selectedConfigFile != null;

        String extractedConfigName = selectedConfigFile.substring(selectedConfigFile.indexOf('_') + 1, selectedConfigFile.lastIndexOf('_'));
        String configFilePath = directory.getAbsolutePath() + File.separator + selectedConfigFile; // Full path to the config file
        String playerName = selectedConfigFile.substring(0, selectedConfigFile.indexOf("_"));
        this.APP_FRAME.getMAIN_CONTROLLER().getLOGGER().log("Player choice: " + selectedConfigFile + ", return to MainController - CONFIG_FILE: " + extractedConfigName);
        this.handleMainController(extractedConfigName.toUpperCase().replace("_", " "), configFilePath, playerName);
        this.revalidate();
        this.repaint();
        this.APP_FRAME.setVisible(false);
    }

    private void handleMainController(String MAP_NAME, String CONFIG_FILE, String PLAYER_NAME) {
        this.APP_FRAME.getMAIN_CONTROLLER().setMAP_NAME(MAP_NAME);
        this.APP_FRAME.getMAIN_CONTROLLER().setPLAYER_NAME(PLAYER_NAME);
        this.APP_FRAME.getMAIN_CONTROLLER().setGAME_CONFIG_FILE(CONFIG_FILE);
        this.APP_FRAME.getMAIN_CONTROLLER().initializeGameFrame(true);
    }
}
