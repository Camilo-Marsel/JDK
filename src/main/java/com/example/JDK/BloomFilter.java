import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BloomFilter {
    private int size; // Number of bits in the Bloom filter's bit array
    private int hashCount; // Number of hash functions to use
    private int[] bitArray; // Bit array to store the presence of elements

    /**
     * Constructor for the BloomFilter class.
     *
     * @param size      Number of bits in the Bloom filter's bit array
     * @param hashCount Number of hash functions to use
     */
    public BloomFilter(int size, int hashCount) {
        this.size = size;
        this.hashCount = hashCount;
        this.bitArray = new int[size]; // Initialize the bit array with all bits set to 0
    }

    /**
     * Private method to simulate hash functions on the item.
     *
     * @param item Item to be hashed
     * @return List of hash results
     */
    private List<Integer> hashes(String item) {
        List<Integer> hashResults = new ArrayList<>(); // List to store results of hash functions
        byte[] itemBytes = item.getBytes(StandardCharsets.UTF_8); // Convert item to bytes

        for (int i = 0; i < hashCount; i++) {
            // Simulate different hash functions by adding an index and taking modulo the size
            int hashResult = (itemBytes.hashCode() + i) % size;
            hashResults.add(hashResult); // Store the hash result
        }
        return hashResults; // Return list of hash results
    }

    /**
     * Method to add an item to the Bloom filter.
     *
     * @param item Item to be added
     */
    public void add(String item) {
        for (int hashVal : hashes(item)) {
            // Set the bit at the position of each hash result to 1
            bitArray[hashVal] = 1;
        }
    }

    /**
     * Method to check if an item might be in the set.
     *
     * @param item Item to be checked
     * @return True if the item might be in the set, otherwise false
     */
    public boolean check(String item) {
        for (int hashVal : hashes(item)) {
            if (bitArray[hashVal] == 0) {
                // If any bit corresponding to the hash results is 0, the item is not in the set
                return false;
            }
        }
        // If all bits are set to 1, the item might be in the set (with some probability of false positive)
        return true;
    }

    public static void main(String[] args) {
        // Initialize Bloom Filter with size 10 and 3 hash functions
        BloomFilter bf = new BloomFilter(10, 3);

        // Add some email addresses
        String[] emails = {"test@example.com", "hello@world.com", "foo@bar.com"};
        for (String email : emails) {
            bf.add(email); // Add email addresses to the Bloom filter
        }

        // Check if an email address is in the Bloom Filter
        String[] testEmails = {"test@example.com", "not_in_set@example.com"};
        for (String email : testEmails) {
            if (bf.check(email)) {
                System.out.println("'" + email + "' is possibly in the set."); // Email might be in the set
            } else {
                System.out.println("'" + email + "' is definitely not in the set."); // Email is not in the set
            }
        }
    }
}