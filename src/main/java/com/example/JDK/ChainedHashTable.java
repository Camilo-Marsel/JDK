import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ChainedHashTable {

    private int size;
    private List<Function<String, Integer>> hashFunctions;
    private List<List<String>> table;

    public ChainedHashTable(int size, List<Function<String, Integer>> hashFunctions) {
        this.size = size;
        this.hashFunctions = hashFunctions;
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new ArrayList<>());
        }
    }

    public void add(String key) {
        // Apply each hash function to the key to get a list of hash values
        List<Integer> hashValues = new ArrayList<>();
        for (Function<String, Integer> hashFunction : hashFunctions) {
            hashValues.add(hashFunction.apply(key) % size);
        }
        // Use the smallest hash value as the index for the key
        int index = hashValues.stream().min(Integer::compareTo).orElse(0);
        // Append the key to the chain at the calculated index
        table.get(index).add(key);
    }

    public void display() {
        for (int i = 0; i < table.size(); i++) {
            System.out.println("Bucket " + i + ": " + table.get(i));
        }
    }

    public static void main(String[] args) {
        // Define the size of the hash table
        int size = 4;

        // Define a list of hash functions
        // For simplicity, we use lambda functions that apply different simple hashing strategies
        List<Function<String, Integer>> hashFunctions = new ArrayList<>();
        hashFunctions.add(key -> {
            int sum = 0;
            for (char c : key.toCharArray()) {
                sum += (int) c;
            }
            return sum;
        });
        hashFunctions.add(key -> key.length());
        hashFunctions.add(key -> key.isEmpty() ? 0 : (int) key.charAt(0));

        // Create an instance of ChainedHashTable
        ChainedHashTable hashTable = new ChainedHashTable(size, hashFunctions);

        // Add values to the hash table
        hashTable.add("apple");
        hashTable.add("banana");
        hashTable.add("cherry");
        hashTable.add("grape");
        hashTable.add("lemon");
        hashTable.add("peanut");

        // Display the hash table and bucket status
        hashTable.display();
    }
}