import javax.swing.JPanel; // JPanel represents a generic container for holding components.
import javax.swing.JLabel; // JLabel represents a display area for a short text string or an image.
import javax.swing.ImageIcon; // ImageIcon represents an icon from an image file
import java.awt.event.MouseListener; // Abstract window toolkit's event class have MouseListener method that deals with mouse events.
import java.awt.event.MouseEvent; // MouseEvent method represents a mouse event.

public class ButtonPanel extends JPanel { // declares the ButtonPanel class, which extends JPanel to create a custom
                                          // Panel for buttons.

    public static final long serialVersionUID = 1L; // serialVersionUID provides a version number for serialization
                                                    // to ensure compatibility between different versions of the class

    private static final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 80; // defines constants for the width and height of
                                                                     // the buttons
    private JLabel[] buttons; // declaring an array to hold the button labels
    private SortButtonListener listener; // listener interface to handle button click events.
    private int number = 6; // number of buttons

    public ButtonPanel(SortButtonListener listener) { // Constructor for the ButtonPanel class, which initializes the
                                                      // panel with the specified listener.
        super();

        this.listener = listener; // Assigns the listener to the instance variable.

        buttons = new JLabel[number]; // Initializes the buttons array with the specified number of buttons.

        for (int i = 0; i < buttons.length; i++) // array to initialize each button
            buttons[i] = new JLabel(); // Creates a new JLabel for each button.

        // Initializes each button with a specific image and id.
        initButtons(buttons[0], "create_button", 0);
        initButtons(buttons[1], "bubble_button", 1);
        initButtons(buttons[2], "selection_button", 2);
        initButtons(buttons[3], "insertion_button", 3);
        initButtons(buttons[4], "merge_button", 4);
        initButtons(buttons[5], "quick_button", 5);

        setLayout(null); // Sets the layout manager to null to allow manual positioning of the buttons.

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds(20, 20 + (BUTTON_HEIGHT + 5) * i, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(buttons[i]);
        }
    }

    private void initButtons(JLabel button, String name, int id) { // Sets the icon for the button based on the
                                                                   // specified image file.
        button.setIcon(new ImageIcon(String.format("buttons/%s.png", name)));
        button.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(String.format("buttons/%s_entered.png", name)));
            }

            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon(String.format("buttons/%s.png", name)));
            }

            public void mousePressed(MouseEvent e) {
                button.setIcon(new ImageIcon(String.format("buttons/%s_pressed.png", name)));
            }

            public void mouseReleased(MouseEvent e) {
                listener.sortButtonClicked(id); // method of the listener when the mouse is released.
                button.setIcon(new ImageIcon(String.format("buttons/%s_entered.png", name)));
            }
        });
    }

    public interface SortButtonListener { // Defines a sortButtonClicked method to handle button click events.
        void sortButtonClicked(int id);
    }
}