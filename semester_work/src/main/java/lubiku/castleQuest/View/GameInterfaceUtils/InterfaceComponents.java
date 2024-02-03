package lubiku.castleQuest.View.GameInterfaceUtils;

import javax.swing.*;
import java.awt.*;

/**
 * <h2>InterfaceComponents</h2>
 * The InterfaceComponents class provides methods for creating and setting up various user interface components,
 * such as buttons, text fields, labels, and grid components, with consistent preferences and styles.
 * */
public class InterfaceComponents {

    private final Font Consolas = new Font("Consolas", Font.BOLD, 24);
    public InterfaceComponents() { }

    // --- BUTTONS ---

    /**
     * <h3>createButton</h3>
     * Creates and set-ups a new button with preferences from 'setUpButtonPreferences'
     * @return The created button.
     */
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        setUpButtonPreferences(button);
        return button;
    }

    /**
     * <h3>setUpButtonPreferences</h3>
     * Sets up the preferences for the button.
     * @param button The start button to set up.
     */
    private void setUpButtonPreferences(JButton button) {
        // Button colors
        button.setBackground(new Color(139, 69, 19)); // Brown background
        button.setForeground(new Color(240, 230, 220)); // White text color
        button.setBorder(BorderFactory.createLineBorder(new Color(205, 133, 63), 2)); // Dark brown border

        // Button font
        button.setFont(this.Consolas);

        // Button size
        button.setPreferredSize(new Dimension(200, 50));

        // Button hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(new Color(184, 139, 11));}
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(new Color(139, 69, 19)); }
        });
    }

    // --- TEXT FIELDS ---

    /**
     * <h3>createTextField</h3>
     * Creates a text field with the specified number of columns.
     * @param columns The number of columns for the text field.
     * @return The created text field.
     */
    public JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);

        // TextField font
        textField.setFont(this.Consolas);
        return textField;
    }

    // --- LABELS ---

    /**
     * <h3>createLabel</h3>
     * Creates a label with the specified text.
     * @param text The text to be displayed on the label.
     * @return The created label.
     */
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        this.Consolas.deriveFont(Font.PLAIN);
        label.setFont(this.Consolas);
        return label;
    }

    // --- GRID BAG LAYOUT ---

    /**
     * <h3>addGridComponentToPanel</h3>
     * Adds a component to the panel with specified grid position and constraints.
     * @param panel     The panel to add the component to.
     * @param component The component to add.
     * @param gridX     The column position in the grid.
     * @param gridY     The row position in the grid.
     */
    public void addGridComponentToPanel(JPanel panel, JComponent component, int gridX, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(component, gbc);
    }
}
