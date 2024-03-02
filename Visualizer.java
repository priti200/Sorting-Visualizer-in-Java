
// Import statements for required Java classes and libraries
import javax.swing.JOptionPane; // For displaying error messages
import java.awt.image.BufferStrategy; // For buffering strategy in Swing
import java.awt.Color; // For defining colors in the GUI
import java.awt.Graphics; // For drawing graphics in the GUI
import java.util.Random; // For generating random numbers
import java.util.concurrent.TimeUnit; // For handling time units

// Class to display the sorting algorithm visualization
public class Visualizer {
    private static final int PADDING = 20; // Padding for the display
    private static final int MAX_BAR_HEIGHT = 350, MIN_BAR_HEIGHT = 30; // Max and min bar heights

    private Integer[] array; // Array to hold the data
    private int capacity, speed; // Capacity of the array and speed of visualization
    private Bar[] bars; // Array of bars for visualization
    private boolean hasArray; // Flag to check if array is created

    // Variables for tracking statistics
    private long startTime, time; // Start time and total time for sorting
    private int comp, swapping; // Number of comparisons and swaps

    // Colors for visualization
    private Color originalColor, swappingColor, comparingColor;

    private BufferStrategy bs; // BufferStrategy for Swing
    private Graphics g; // Graphics object for drawing

    private SortedListener listener; // Listener for sorting events

    // Constructor
    public Visualizer(int capacity, int fps, SortedListener listener) {
        this.capacity = capacity;
        this.speed = (int) (1000.0 / fps);
        this.listener = listener;
        startTime = time = comp = swapping = 0;

        originalColor = ColorManager.BAR_WHITE;
        comparingColor = Color.YELLOW;
        swappingColor = ColorManager.BAR_RED;

        bs = listener.getBufferStrategy();

        hasArray = false;
    }

    // Method to create a random array for visualization
    public void createRandomArray(int canvasWidth, int canvasHeight) {
        array = new Integer[capacity];
        bars = new Bar[capacity];
        hasArray = true;

        double x = PADDING;
        int y = canvasHeight - PADDING;

        double width = (double) (canvasWidth - PADDING * 2) / capacity;

        g = bs.getDrawGraphics();
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        Random rand = new Random();
        int value;
        Bar bar;
        for (int i = 0; i < array.length; i++) {
            value = rand.nextInt(MAX_BAR_HEIGHT) + MIN_BAR_HEIGHT;
            array[i] = value;

            bar = new Bar((int) x, y, (int) width, value, originalColor);
            bar.draw(g);
            bars[i] = bar;

            x += width;
        }

        bs.show();
        g.dispose();
    }

    // Method to get color based on the value of a bar
    private Color getBarColor(int value) {
        int interval = (int) (array.length / 5.0);
        if (value < interval)
            return ColorManager.BAR_ORANGE;
        else if (value < interval * 2)
            return ColorManager.BAR_YELLOW;
        else if (value < interval * 3)
            return ColorManager.BAR_GREEN;
        else if (value < interval * 4)
            return ColorManager.BAR_CYAN;
        return ColorManager.BAR_BLUE;
    }

    // Bubble sort visualization
    public void bubbleSort() {
        if (!isCreated())
            return;

        g = bs.getDrawGraphics();

        startTime = System.nanoTime();
        SortingAlgorithms.bubbleSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;
        int count = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            count = 0;
            for (int j = 0; j < i; j++) {
                colorPair(j, j + 1, comparingColor);

                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    count++;
                    swapping++;
                }

                comp++;
            }

            bars[i].setColor(getBarColor(i));
            bars[i].draw(g);
            bs.show();

            if (count == 0)
                break;
        }

        finishAnimation();

        g.dispose();
    }

    // Selection sort visualization
    public void selectionSort() {
        if (!isCreated())
            return;

        g = bs.getDrawGraphics();

        startTime = System.nanoTime();
        SortingAlgorithms.selectionSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            int max = array[i], index = i;
            for (int j = 0; j <= i; j++) {
                if (max < array[j]) {
                    max = array[j];
                    index = j;
                }

                colorPair(index, j, comparingColor);
                comp++;
            }

            swap(i, index);
            swapping++;

            bars[i].setColor(getBarColor(i));
            bars[i].draw(g);
            bs.show();
        }

        finishAnimation();

        g.dispose();
    }

    // Insertion sort visualization
    public void insertionSort() {
        if (!isCreated())
            return;

        g = bs.getDrawGraphics();

        startTime = System.nanoTime();
        SortingAlgorithms.insertionSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;

        Bar bar;
        for (int i = 1; i < array.length; i++) {
            bars[i].setColor(getBarColor(i));

            int index = i - 1, element = array[i];
            while (index >= 0 && element < array[index]) {
                array[index + 1] = array[index];

                bar = bars[index + 1];
                bar.clear(g);
                bar.setValue(bars[index].getValue());
                colorBar(index + 1, swappingColor);

                index--;
                comp++;
                swapping++;
            }
            comp++;

            index++;

            array[index] = element;

            bar = bars[index];
            bar.clear(g);
            bar.setValue(element);
            bar.setColor(getBarColor(index));
            bar.draw(g);

            bs.show();
        }

        finishAnimation();

        g.dispose();
    }

    // Quick sort visualization
    public void quickSort() {
        if (!isCreated())
            return;

        g = bs.getDrawGraphics();

        startTime = System.nanoTime();
        SortingAlgorithms.quickSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;

        quickSort(0, array.length - 1);

        finishAnimation();
        g.dispose();
    }

    // Recursive quick sort method
    private void quickSort(int start, int end) {
        if (start < end) {
            int pivot = partition(start, end);

            bars[pivot].setColor(getBarColor(pivot));
            bars[pivot].draw(g);
            bs.show();

            quickSort(start, pivot - 1);
            quickSort(pivot + 1, end);
        }
    }

    // Partition method for quick sort
    private int partition(int start, int end) {
        int pivot = array[end];
        Bar bar = bars[end];
        Color oldColor = bar.getColor();
        bar.setColor(comparingColor);
        bar.draw(g);
        bs.show();

        int index = start - 1;
        for (int i = start; i < end; i++) {
            if (array[i] < pivot) {
                index++;
                swap(index, i);
                swapping++;
            }
            comp++;
        }

        bar.setColor(oldColor);
        bar.draw(g);
        bs.show();

        index++;
        swap(index, end);
        swapping++;

        return index;
    }

    // Merge sort visualization
    public void mergeSort() {
        if (!isCreated())
            return;

        g = bs.getDrawGraphics();

        startTime = System.nanoTime();
        SortingAlgorithms.mergeSort(array.clone());
        time = System.nanoTime() - startTime;

        comp = swapping = 0;

        mergeSort(0, array.length - 1);

        finishAnimation();
        g.dispose();
    }

    // Recursive merge sort method
    private void mergeSort(int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(start, mid);
            mergeSort(mid + 1, end);
            merge(start, mid, end);
        }
    }

    // Merge method for merge sort
    private void merge(int start, int mid, int end) {
        int leftSize = mid - start + 1;
        int rightSize = end - mid;

        Integer[] leftArray = new Integer[leftSize];
        Integer[] rightArray = new Integer[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[start + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = start;
        while (i < leftSize && j < rightSize) {
            colorPair(start + i, mid + 1 + j, comparingColor);
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
            comp++;
            swapping++;
        }

        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
            swapping++;
        }

        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
            swapping++;
        }

        for (int x = start; x <= end; x++) {
            bars[x].setValue(array[x]);
            bars[x].setColor(getBarColor(x));
            bars[x].draw(g);
            bs.show();
        }
    }

    // Helper method to swap two elements in the array
    private void swap(int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;

        bars[i].clear(g);
        bars[j].clear(g);

        bars[j].setValue(bars[i].getValue());
        bars[i].setValue(temp);

        colorPair(i, j, swappingColor);
    }

    // Helper method to color two bars during comparison
    private void colorPair(int i, int j, Color color) {
        Color color1 = bars[i].getColor(), color2 = bars[j].getColor();

        bars[i].setColor(color);
        bars[i].draw(g);

        bars[j].setColor(color);
        bars[j].draw(g);

        bs.show();

        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ex) {
        }

        bars[i].setColor(color1);
        bars[i].draw(g);

        bars[j].setColor(color2);
        bars[j].draw(g);

        bs.show();
    }

    // Helper method to color a single bar
    private void colorBar(int index, Color color) {
        Bar bar = bars[index];
        Color oldColor = bar.getColor();

        bar.setColor(color);
        bar.draw(g);
        bs.show();

        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ex) {
        }

        bar.setColor(oldColor);
        bar.draw(g);

        bs.show();
    }

    // Method to perform final animation after sorting is complete
    private void finishAnimation() {
        for (int i = 0; i < bars.length; i++) {
            colorBar(i, comparingColor);
            bars[i].setColor(getBarColor(i));
            bars[i].draw(g);
            bs.show();
        }

        listener.onArraySorted(time, comp, swapping);
    }

    // Method to draw the array (for restoring purposes)
    public void drawArray() {
        if (!hasArray)
            return;

        g = bs.getDrawGraphics();

        for (int i = 0; i < bars.length; i++) {
            bars[i].draw(g);
        }

        bs.show();
        g.dispose();
    }

    // Helper method to check if array is created
    private boolean isCreated() {
        if (!hasArray)
            JOptionPane.showMessageDialog(null, "You need to create an array!", "No Array Created Error",
                    JOptionPane.ERROR_MESSAGE);
        return hasArray;
    }

    // Setter methods for capacity and FPS
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFPS(int fps) {
        this.speed = (int) (1000.0 / fps);
    }

    // Interface for SortedListener
    public interface SortedListener {
        void onArraySorted(long elapsedTime, int comparison, int swapping);

        BufferStrategy getBufferStrategy();
    }
}
