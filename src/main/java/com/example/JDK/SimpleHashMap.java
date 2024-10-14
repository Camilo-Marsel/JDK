package com.example.JDK;

import java.util.Collections;
import java.util.Comparator;
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

    private static final int NUM_BUCKETS = 625;
    private int bucketActual = 0;
    private static final int MAX_PASSENGERS_PER_BUCKET = 16;

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
            //Orden
            Collections.sort(passengers, Comparator.comparingInt(Passenger::getFamilyID));

            Map<Integer, List<Passenger>> families = new HashMap<>();

            for (Passenger passenger : passengers) {
                families.computeIfAbsent(passenger.getFamilyID(), k -> new ArrayList<>()).add(passenger);
            }
            for (List<Passenger> family : families.values()) {
                List<Passenger> organizedFamily = organizeFamily(family);

                // Asignar los pasajeros organizados a las cabinas
                for (Passenger passenger : organizedFamily) {
                    addToHashmap(passenger);
                }
            }
            printHashmapAndBuckets();

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private static void addToHashmap(Passenger passenger) {

        int familyID = passenger.getFamilyID();
        boolean added = false;

        // Primero intentar agregar al bucket basado en FamilyID
        int bucketIndex = familyID % NUM_BUCKETS;

        for (int i = 0; i < NUM_BUCKETS; i++) {
            List<Passenger> bucket = hashmap.get((bucketIndex + i) % NUM_BUCKETS);

            // Si la cabina está llena, pasar a la siguiente
            if (bucket.size() >= MAX_PASSENGERS_PER_BUCKET) {
                continue;
            }

            // Si el pasajero es menor de 18, comprobar si ya hay un adulto de su familia en la cabina
            if (passenger.getAge() < 18) {
                boolean hasAdultFamilyMember = bucket.stream()
                        .anyMatch(p -> p.getFamilyID() == familyID && p.getAge() >= 18);

                // Si no hay adultos en la cabina, seguir buscando
                if (!hasAdultFamilyMember) {
                    continue;
                }
            }

            // Agregar el pasajero al bucket/cabina
            bucket.add(passenger);
            added = true;
            break;  // Ya se ha agregado, salir del ciclo
        }

        // Si no se pudo agregar bajo las reglas, simplemente ubicar al pasajero en cualquier bucket vacío
        if (!added) {
            for (int i = 0; i < NUM_BUCKETS; i++) {
                List<Passenger> bucket = hashmap.get((bucketIndex + i) % NUM_BUCKETS);
                if (bucket.size() < MAX_PASSENGERS_PER_BUCKET) {
                    bucket.add(passenger);
                    break;
                }
            }
        }
    }

    private static List<Passenger> organizeFamily(List<Passenger> family) {
        List<Passenger> adults = new ArrayList<>();
        List<Passenger> minors = new ArrayList<>();

        // Separar mayores y menores de edad
        for (Passenger passenger : family) {
            if (passenger.getAge() >= 18) {
                adults.add(passenger);
            } else {
                minors.add(passenger);
            }
        }

        List<Passenger> organizedFamily = new ArrayList<>();
        int i = 0, j = 0;

        // Intercalar adultos y menores
        while (i < adults.size() && j < minors.size()) {
            organizedFamily.add(adults.get(i++));  // Agregar un mayor
            organizedFamily.add(minors.get(j++));  // Agregar un menor
        }

        // Agregar los adultos restantes (si ya no hay más menores)
        while (i < adults.size()) {
            organizedFamily.add(adults.get(i++));
        }

        // Agregar los menores restantes (si ya no hay más adultos)
        while (j < minors.size()) {
            organizedFamily.add(minors.get(j++));
        }

        return organizedFamily;
    }
    // Función para imprimir el contenido del hashmap y el estado de los buckets
    private static void printHashmapAndBuckets() {
        System.out.println("Hashmap Contents:");
        for (int bucketIndex : hashmap.keySet()) {
            System.out.print("Cabina " + bucketIndex + ": ");
            List<Passenger> bucket = hashmap.get(bucketIndex);
            for (Passenger passenger : bucket) {
                System.out.print(passenger.getName()+" "+ passenger.getSurname()+" "+ passenger.getAge()+" "+ passenger.getFamilyID()+";");  // Acceder directamente al nombre
            }
            System.out.println();
        }

        /*System.out.println("\nBucket Table Status:");
        for (int bucketIndex = 0; bucketIndex < NUM_BUCKETS; bucketIndex++) {
            System.out.println("Bucket " + bucketIndex + ": " + (hashmap.get(bucketIndex).isEmpty() ? "Empty" : "Contains items"));
        }*/
    }
}
