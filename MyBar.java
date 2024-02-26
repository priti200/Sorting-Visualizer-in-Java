
// Import necessary classes from java.awt package, AWT stands for Abstract Window toolkit used to make the GUI in java.
import java.awt.Graphics; // Graphics class is used to draw graphics
import java.awt.Color; // Color class is used to represent colors
import java.awt.image.BufferStrategy; // BufferStrategy class is used for double buffering

// Define the Bar class
public class MyBar {
    // Constants and variables for defining the bar
    private final int MARGIN = 1; // Margin for the bar
    private int x, y, width, value; // Position, width, and height of the bar
    private Color color; // Color of the bar

    // Constructor to initialize the bar with specified properties
    // y: the bottom left corner

    public MyBar(int x, int y, int width, int value, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.value = value;
        this.color = color;
    }

    // Method to draw the bar on the screen
    public void draw(Graphics g) {
        g.setColor(color); // Set the color for drawing
        g.fillRect(x + MARGIN, y - value, width - MARGIN * 2, value); // Draw the filled rectangle
    }

    // Method to clear the space occupied by the bar
    public void clear(Graphics g) {
        g.setColor(ColorPicker.CANVAS_BACKGROUND); // Set the background color
        g.fillRect(x + MARGIN, y - value, width - MARGIN * 2, value); // Fill the rectangle with background color
    }

    // Setter method to set the value of the bar
    public void setValue(int value) {
        this.value = value;
    }

    // Getter method to get the value of the bar
    public int getValue() {
        return value;
    }

    // Setter method to set the color of the bar
    public void setColor(Color color) {
        this.color = color;
    }

    // Getter method to get the color of the bar
    public Color getColor() {
        return color;
    }
}
