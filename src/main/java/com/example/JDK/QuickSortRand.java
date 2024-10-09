import java.util.Arrays;
import java.util.Random;

public class QuickSortRand {

    public static void randomizedQuicksort(int[] arr, int low, int high) {
        if (low < high) {
            // Randomly select a pivot and partition the array
            int pivotIndex = randomizedPartition(arr, low, high);
            // Recursively apply the same logic to the left and right subarrays
            randomizedQuicksort(arr, low, pivotIndex - 1);
            randomizedQuicksort(arr, pivotIndex + 1, high);
        }
    }

    public static int randomizedPartition(int[] arr, int low, int high) {
        Random rand = new Random();
        int pivotIndex = rand.nextInt(high - low + 1) + low;
        // Swap the pivot with the last element
        int temp = arr[pivotIndex];
        arr[pivotIndex] = arr[high];
        arr[high] = temp;
        return partition(arr, low, high);
    }

    public static int partition(int[] arr, int low, int high) {
        // The last element is the pivot (randomly chosen)
        int pivot = arr[high];
        int i = low - 1;  // Index of smaller element

        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (arr[j] <= pivot) {
                i++;  // Increment index of the smaller element
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Swap the pivot element with the element at i+1
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(-10000, 10000);  // Random integers between -10000 and 10000
        }

        long startTime = System.currentTimeMillis();
        randomizedQuicksort(arr, 0, arr.length - 1);
        // System.out.println(Arrays.toString(arr));
        long endTime = System.currentTimeMillis();
        
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}