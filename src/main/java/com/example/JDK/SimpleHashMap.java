package com.example.JDK;

import com.example.JDK.models.Passenger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHashMap {

    private static final int NUM_BUCKETS = 100;
    private static final int MAX_PASSENGERS_PER_BUCKET = 50;

    // Inicializamos el hashmap con listas vacías para cada bucket
    private static Map<Integer, List<Passenger>> hashmap = new HashMap<>();

    static {
        for (int i = 0; i < NUM_BUCKETS; i++) {
            hashmap.put(i, new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        try {
            // Ruta del archivo JSON
            File file = new File("src/main/resources/passengers.json");  // Asegúrate de que el archivo esté en la ruta correcta.
            ObjectMapper mapper = new ObjectMapper();

            // Leer los registros del archivo JSON
            List<Passenger> passengers = mapper.readValue(file, new TypeReference<List<Passenger>>() {});

            // Proceso de agregar pasajeros al hashmap
            for (Passenger passenger : passengers) {
                addToHashmap(passenger);
            }

            printHashmapAndBuckets();

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // Función para agregar un pasajero al hashmap basado en su FamilyID
    private static void addToHashmap(Passenger passenger) {
        // Obtener el índice del bucket basado en el FamilyID del pasajero
        int bucketIndex = passenger.getFamilyID() % NUM_BUCKETS;

        List<Passenger> bucket = hashmap.get(bucketIndex);

        // Si el bucket ya tiene 10 pasajeros, busca otro bucket
        if (bucket.size() >= MAX_PASSENGERS_PER_BUCKET) {
            bucketIndex = (bucketIndex + 1) % NUM_BUCKETS;
            bucket = hashmap.get(bucketIndex);
        }

        // Agrega el pasajero al bucket
        bucket.add(passenger);
    }

    // Función para imprimir el contenido del hashmap y el estado de los buckets
    private static void printHashmapAndBuckets() {
        System.out.println("Hashmap Contents:");
        for (int bucketIndex : hashmap.keySet()) {
            System.out.print("Bucket " + bucketIndex + ": ");
            List<Passenger> bucket = hashmap.get(bucketIndex);
            for (Passenger passenger : bucket) {
                System.out.print(passenger.getFamilyID() + " ");  // Acceder directamente al nombre
            }
            System.out.println();
        }

        /*System.out.println("\nBucket Table Status:");
        for (int bucketIndex = 0; bucketIndex < NUM_BUCKETS; bucketIndex++) {
            System.out.println("Bucket " + bucketIndex + ": " + (hashmap.get(bucketIndex).isEmpty() ? "Empty" : "Contains items"));
        }*/
    }
}
