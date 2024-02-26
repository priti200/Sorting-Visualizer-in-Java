public class SortingAlgorithms {
    // Selection Sort Algorithm
    public static <T extends Comparable<T>> void selectionSort(T[] data) {
        for (int i = 0; i < data.length; i++) {
            int min = i;
            for (int scan = i; scan < data.length; scan++) {
                if (data[scan].compareTo(data[min]) < 0) {
                    min = scan;
                }
            }
            swap(data, i, min);
        }
    }

    // Insertion Sort Algorithm
    public static <T extends Comparable<T>> void insertionSort(T[] data) {
        for (int i = 1; i < data.length; i++) {
            T current = data[i];
            int position = i;
            while (position > 0 && data[position - 1].compareTo(current) > 0) {
                data[position] = data[position - 1];
                position--;
            }
            data[position] = current;
        }
    }

    // Bubble Sort Algorithm
    public static <T extends Comparable<T>> void bubbleSort(T[] data) {
        for (int position = data.length - 1; position > 0; position--) {
            for (int scan = 0; scan < position; scan++) {
                if (data[scan].compareTo(data[scan + 1]) > 0) {
                    swap(data, scan, scan + 1);
                }
            }
        }
    }

    // Quick Sort Algorithm
    public static <T extends Comparable<T>> void quickSort(T[] data) {
        quickSort(data, 0, data.length - 1);
    }

    // Recursive Quick Sort Helper Method
    private static <T extends Comparable<T>> void quickSort(T[] data, int start, int end) {
        if (start < end) {
            int middle = partition(data, start, end);
            quickSort(data, start, middle - 1);
            quickSort(data, middle + 1, end);
        }
    }

    // Partition Method for Quick Sort
    private static <T extends Comparable<T>> int partition(T[] data, int start, int end) {
        int pivot = (start + end) / 2;
        int left = start + 1;
        int right = end;
        T pivotElem = data[pivot];
        swap(data, start, pivot);
        while (left <= right) {
            while (left <= right && data[left].compareTo(pivotElem) <= 0) {
                left++;
            }
            while (data[right].compareTo(pivotElem) > 0) {
                right--;
            }
            if (left < right) {
                swap(data, left, right);
            }
        }
        swap(data, start, right);
        return right;
    }

    // Merge Sort Algorithm
    public static <T extends Comparable<T>> void mergeSort(T[] data) {
        mergeSort(data, 0, data.length - 1);
    }

    // Recursive Merge Sort Helper Method
    private static <T extends Comparable<T>> void mergeSort(T[] data, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(data, start, middle);
            mergeSort(data, middle + 1, end);
            merge(data, start, middle, end);
        }
    }

    // Merge Method for Merge Sort
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] data, int start, int middle, int end) {
        T[] temp = (T[]) (new Comparable[end - start + 1]);
        int left = start;
        int right = middle + 1;
        int index = 0;
        while (left <= middle && right <= end) {
            if (data[left].compareTo(data[right]) < 0) {
                temp[index] = data[left];
                left++;
            } else {
                temp[index] = data[right];
                right++;
            }
            index++;
        }
        while (left <= middle) {
            temp[index] = data[left];
            left++;
            index++;
        }
        while (right <= end) {
            temp[index] = data[right];
            right++;
            index++;
        }
        for (int i = 0; i < temp.length; i++) {
            data[start + i] = temp[i];
        }
    }

    // Swap Method to swap two elements in an array
    private static <T extends Comparable<T>> void swap(T[] data, int element1, int element2) {
        T temp = data[element2];
        data[element2] = data[element1];
        data[element1] = temp;
    }
}