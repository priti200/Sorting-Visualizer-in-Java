
import java.awt.Canvas; // java.awt.Canvas represents a blank rectangular area where other components or graphics can be drawn.
import java.awt.Graphics; //Graphics  provides the base class for all graphics contexts  that allow an application to draw onto components

public class MyCanvas extends Canvas { // declaration of MyCanvas class which extends 'Canvas' class to create a
                                          // custom
                                          // canvas for drawing.
    public static final long serialVersionUID = 2L; // SerialVersionUID provides a version number for serialization to
                                                    // ensure compatability between different versions of the class.

    private VisualizerProvider listener; // A listener interface to provide the visual content for the Canvas.

    public MyCanvas(VisualizerProvider listener) { // Constructor for MyCanvas class which initializes the canvas
                                                      // with
        // specified listener.
        super(); // for inhereting the methods of Parent class Canvas
        this.listener = listener; // Assigning the listener to the instance variable
    }

    public void paint(Graphics graphic) { // this method overrides the paint method of the Canvas class to draw the
                                          // visual content.
        super.paint(graphic); // Calls the superclass paint method to ensure proper initialization
        clear(graphic); // Clears the Canvas by filling it with the canvas background color.

        listener.onDrawArray(); // Calls the onDrawArray method of the listener to draw the array.
    }

    public void clear(Graphics graphic) { // Custom method to clear the Canvas
        graphic.setColor(ColorManager.CANVAS_BACKGROUND); // sets the color of the graphics context to the canvas
                                                          // background color.
        graphic.fillRect(0, 0, getWidth(), getHeight()); // Draws a filled rectangle representing the
                                                         // canvas with the background color.
    }

    public interface VisualizerProvider { // Defines the VisualProvider interface which has a single method
                                          // 'onDrawArray'
        // to provide the visual content
        void onDrawArray(); // Method to be implemented by classes that provide visual content for the
    }
}
