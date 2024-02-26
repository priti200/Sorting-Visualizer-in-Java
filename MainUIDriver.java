import javax.swing.JFrame; // Importing JFrame class for creating a window
import javax.swing.JLabel; // Importing JLabel class for displaying text
import javax.swing.JPanel; // Importing JPanel class for holding components
import javax.swing.JFormattedTextField; // Importing JFormattedTextField class for formatted text input
import javax.swing.JSlider; // Importing JSlider class for a slider component
import javax.swing.BoxLayout; // Importing BoxLayout class for flexible layout management
import javax.swing.BorderFactory; // Importing BorderFactory class for creating borders for components
import javax.swing.event.ChangeListener; // Importing ChangeListener interface for listening to slider changes
import javax.swing.event.ChangeEvent; // Importing ChangeEvent class for slider change events
import java.awt.Font; // Importing Font class for specifying font characteristics
import java.awt.Dimension; // Importing Dimension class for specifying component sizes
import java.awt.EventQueue; // Importing EventQueue class for managing events in a Swing application
import java.awt.GridLayout; // Importing GridLayout class for a grid-based layout
import java.awt.image.BufferStrategy; // Importing BufferStrategy class for double buffering
import java.beans.PropertyChangeListener; // Importing PropertyChangeListener interface for listening to property changes
import java.beans.PropertyChangeEvent; // Importing PropertyChangeEvent class for property change events
import java.text.NumberFormat; // Importing NumberFormat class for formatting numbers


// MainUI class extends JFrame and implements several interfaces
public class MainUIDriver extends JFrame implements PropertyChangeListener,
	   ChangeListener, Display.SortedListener,
	   SortingButtons.SortButtonListener, MyAnimator.VisualizerProvider
{
	// Serialization ID
	public static final long serialVersionUID = 10L;

	// Constants for window dimensions, array capacity, and FPS
	private static final int WIDTH = 1280, HEIGHT = 720;
	private static final int CAPACITY = 50, FPS = 100;

	// Panels and components for UI
	private JPanel mainPanel, inputPanel, sliderPanel, inforPanel;
	private SortingButtons buttonPanel;
	private JLabel capacityLabel, fpsLabel, timeLabel, compLabel, swapLabel;
	private JFormattedTextField capacityField;
	private JSlider fpsSlider;
	private MyAnimator canvas;
	private Display visualizer;

	// Main method to start the program
	public static void main(String[] args)
	{
		// Run GUI on the Event Dispatch Thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainUIDriver().setVisible(true);
			}
		});
	}

	// Constructor to initialize the JFrame
	public MainUIDriver()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMaximumSize(new Dimension(WIDTH, HEIGHT + 200));
		setMinimumSize(new Dimension(WIDTH, HEIGHT + 20));
		setPreferredSize(new Dimension(WIDTH, HEIGHT + 20));
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(ColorPicker.BACKGROUND);
		setTitle("Sorting Algorithm's Visualizer");

		initialize(); // Initialize the UI components
	}

	// Initialize UI components
	private void initialize()
	{
		mainPanel = new JPanel(); // Main panel for layout
		mainPanel.setLayout(null); // Use null layout for custom positioning
		mainPanel.setBackground(ColorPicker.BACKGROUND); // Set background color
		add(mainPanel); // Add mainPanel to the JFrame

		// Add buttons panel for sorting algorithms
		buttonPanel = new SortingButtons(this);
		buttonPanel.setBounds(0, 150, 250, HEIGHT);
		buttonPanel.setBackground(ColorPicker.BACKGROUND);
		mainPanel.add(buttonPanel);

		// Add canvas for visualization of sorting
		canvas = new MyAnimator(this);
		int cWidth = WIDTH - 250 - 10;
		int cHeight = HEIGHT - 80;
		canvas.setFocusable(false);
		canvas.setMaximumSize(new Dimension(cWidth, cHeight));
		canvas.setMinimumSize(new Dimension(cWidth, cHeight));
		canvas.setPreferredSize(new Dimension(cWidth, cHeight));
		canvas.setBounds(250, 60, cWidth, cHeight);
		mainPanel.add(canvas);
		pack(); // Pack the components

		// Initialize the sorting visualizer
		visualizer = new Display(CAPACITY, FPS, this);
		visualizer.createRandomArray(canvas.getWidth(), canvas.getHeight());

		// Create an input field for capacity
		capacityLabel = new JLabel("Capacity");
		capacityLabel.setForeground(ColorPicker.TEXT);
		capacityLabel.setFont(new Font(null, Font.BOLD, 15));

		NumberFormat format = NumberFormat.getNumberInstance();
		Eraser formatter = new Eraser(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(200);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		capacityField = new JFormattedTextField(formatter);
		capacityField.setValue(CAPACITY);
		capacityField.setColumns(3);
		capacityField.setFont(new Font(null, Font.PLAIN, 15));
		capacityField.setForeground(ColorPicker.TEXT);
		capacityField.setBackground(ColorPicker.CANVAS_BACKGROUND);
		capacityField.setCaretColor(ColorPicker.BAR_YELLOW);
		capacityField.setBorder(BorderFactory.createLineBorder(ColorPicker.FIELD_BORDER, 1));
		capacityField.addPropertyChangeListener("value", this);

		capacityLabel.setLabelFor(capacityField);

		inputPanel = new JPanel(new GridLayout(1, 0));
		inputPanel.add(capacityLabel);
		inputPanel.add(capacityField);
		inputPanel.setBackground(ColorPicker.BACKGROUND);
		inputPanel.setBounds(25, 20, 170, 30);
		mainPanel.add(inputPanel);

		// Create slider for FPS (Frames Per Second)
		fpsLabel = new JLabel("Frames Per Second");
		fpsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		fpsLabel.setFont(new Font(null, Font.BOLD, 15));
		fpsLabel.setForeground(ColorPicker.TEXT);

		fpsSlider = new JSlider(JSlider.HORIZONTAL, 50, 350, FPS);
		fpsSlider.setMajorTickSpacing(100);
		fpsSlider.setMinorTickSpacing(20);
		fpsSlider.setPaintTicks(true);
		fpsSlider.setPaintLabels(true);
		fpsSlider.setPaintTrack(true);
		fpsSlider.setForeground(ColorPicker.TEXT);
		fpsSlider.addChangeListener(this);

		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		sliderPanel.setBackground(ColorPicker.BACKGROUND);
		sliderPanel.add(fpsLabel);
		sliderPanel.add(fpsSlider);

		sliderPanel.setBounds(10, 80, 220, 100);
		mainPanel.add(sliderPanel);

		// Initialize statistics panel
		timeLabel = new JLabel("Elapsed Time: 0 µs");
		timeLabel.setFont(new Font(null, Font.PLAIN, 15));
		timeLabel.setForeground(ColorPicker.TEXT_RED);

		compLabel = new JLabel("Comparisons: 0");
		compLabel.setFont(new Font(null, Font.PLAIN, 15));
		compLabel.setForeground(ColorPicker.TEXT_YELLOW);

		swapLabel = new JLabel("Swaps: 0");
		swapLabel.setFont(new Font(null, Font.PLAIN, 15));
		swapLabel.setForeground(ColorPicker.TEXT_GREEN);

		inforPanel = new JPanel(new GridLayout(1, 0));
		inforPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		inforPanel.add(timeLabel);
		inforPanel.add(compLabel);
		inforPanel.add(swapLabel);
		inforPanel.setBackground(ColorPicker.BACKGROUND);
		inforPanel.setBounds(410, 20, 800, 30);
		mainPanel.add(inforPanel);
	}

	// Property change listener for capacity field
	public void propertyChange(PropertyChangeEvent e)
	{
		int value = ((Number)capacityField.getValue()).intValue();
		visualizer.setCapacity(value);
	}

	// Change listener for FPS slider
	public void stateChanged(ChangeEvent e)
	{
		if (!fpsSlider.getValueIsAdjusting())
		{
			int value = (int) fpsSlider.getValue();
			visualizer.setFPS(value);
		}
	}

	// Sort button click listener
	public void sortButtonClicked(int id)
	{
		switch (id)
		{
			case 0:  // Create button
				visualizer.createRandomArray(canvas.getWidth(), canvas.getHeight());
				break;
			case 1:  // Bubble sort button
				visualizer.bubbleSort();
				break;
			case 2:  // Selection sort button
				visualizer.selectionSort();
				break;
			case 3:  // Insertion sort button
				visualizer.insertionSort();
				break;
			case 4:  // Quick sort button
				visualizer.quickSort();
				break;
			case 5:  // Merge sort button
				visualizer.mergeSort();
				break;
		}
	}

	// Draw array
	public void onDrawArray()
	{
		if (visualizer != null)
			visualizer.drawArray();
	}

	// Update statistics when sorting is completed
	public void onArraySorted(long elapsedTime, int comp, int swapping)
	{
		timeLabel.setText("Elapsed Time: " + (int)(elapsedTime/1000.0) + " µs");
		compLabel.setText("Comparisons: " + comp);
		swapLabel.setText("Swaps: " + swapping);
	}

	// Get buffer strategy for drawing
	public BufferStrategy getBufferStrategy()
	{
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null)
		{
			canvas.createBufferStrategy(2);
			bs = canvas.getBufferStrategy();
		}

		return bs;
	}
}
