import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHashMap {

    // Define the number of buckets in the hashmap
    private static final int NUM_BUCKETS = 4;

    // Initialize the hashmap with empty lists for each bucket
    private static Map<Integer, List<String>> hashmap = new HashMap<>();

    static {
        for (int i = 0; i < NUM_BUCKETS; i++) {
            hashmap.put(i, new ArrayList<>());
        }
    }
    // aqui nota organizar la funcion
    // Hash function to determine the bucket index for a given string
    // This is a simple hash function for demonstration purposes.
    private static int hashFunction(String key) {
        int sum = 0;
        for (char c : key.toCharArray()) {
            sum += (int) c;
        }
        return sum % NUM_BUCKETS;
    }

    // Function to add a value to the hashmap
    private static void addToHashmap(String value) {
        // Get the bucket index for the value
        int bucketIndex = hashFunction(value);
        // Append the value to the corresponding bucket
        hashmap.get(bucketIndex).add(value);
    }

    // Function to print the hashmap and bucket table status
    private static void printHashmapAndBuckets() {
        System.out.println("Hashmap Contents:");
        for (int bucketIndex : hashmap.keySet()) {
            System.out.println("Bucket " + bucketIndex + ": " + hashmap.get(bucketIndex));
        }

        System.out.println("\nBucket Table Status:");
        for (int bucketIndex = 0; bucketIndex < NUM_BUCKETS; bucketIndex++) {
            System.out.println("Bucket " + bucketIndex + ": " + (hashmap.get(bucketIndex).isEmpty() ? "Empty" : "Contains items"));
        }
    }
    // aqui toca poner que lea los registros del json
    public static void main(String[] args) {
        // Add values to the hashmap
        addToHashmap("apple");
        addToHashmap("banana");
        addToHashmap("cherry");
        addToHashmap("grape");
        addToHashmap("lemon");
        addToHashmap("peanut");
        addToHashmap("apple");

        // Print the hashmap and the status of each bucket
        printHashmapAndBuckets();
    }
}