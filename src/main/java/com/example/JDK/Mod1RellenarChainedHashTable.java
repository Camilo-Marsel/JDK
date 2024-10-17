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
import java.util.function.Function;

public class Mod1RellenarChainedHashTable {
    private final int size;
    private final int maxPassengersPerBucket; // Máximo de pasajeros por cabina
    private final List<Function<Passenger, Integer>> hashFunctions;
    private final List<List<Passenger>> table;

    // Constructor de la tabla hash encadenada
    public Mod1RellenarChainedHashTable(int size, int maxPassengersPerBucket, List<Function<Passenger, Integer>> hashFunctions) {
        this.size = size;
        this.maxPassengersPerBucket = maxPassengersPerBucket;
        this.hashFunctions = hashFunctions;
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new ArrayList<>());
        }
    }

    // Método para agregar pasajeros a la tabla hash
    public void add(Passenger passenger) {
        // Aplicar cada función hash al FamilyID del pasajero
        List<Integer> hashValues = new ArrayList<>();
        for (Function<Passenger, Integer> hashFunction : hashFunctions) {
            hashValues.add(hashFunction.apply(passenger) % size);
        }
        // Usar el valor hash más pequeño como índice para el bucket
        int index = hashValues.stream().min(Integer::compareTo).orElse(0);

        // Verificar si la cabina tiene espacio (menos de maxPassengersPerBucket)
        for (int i = 0; i < size; i++) {
            int currentIndex = (index + i) % size;
            List<Passenger> bucket = table.get(currentIndex);
            if (bucket.size() < maxPassengersPerBucket) {
                bucket.add(passenger);
                return; // Salir después de agregar el pasajero
            }
        }
        // Si todas las cabinas están llenas, no se puede agregar más pasajeros
        System.out.println("No hay espacio disponible para agregar al pasajero: " + passenger.getName() + " " + passenger.getSurname());
    }

    // Método para mostrar el contenido de la tabla hash
    public void display() {
        for (int i = 0; i < table.size(); i++) {
            System.out.print("Cabina " + i + ": ");
            for (Passenger passenger : table.get(i)) {
                System.out.print(passenger.getName() + " " + passenger.getSurname() + " " + passenger.getAge() + " " + passenger.getFamilyID() + "; ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            // Ruta del archivo JSON
            File file = new File("src/main/resources/passengers.json");
            ObjectMapper mapper = new ObjectMapper();

            // Leer los registros del archivo JSON
            List<Passenger> passengers = mapper.readValue(file, new TypeReference<>() {
            });

            // Definir las funciones hash
            List<Function<Passenger, Integer>> hashFunctions = new ArrayList<>();
            hashFunctions.add(Passenger::getFamilyID);  // Hash basado en FamilyID
            hashFunctions.add(Passenger::getAge);       // Hash basado en la edad
            hashFunctions.add(p -> p.getName().length()); // Hash basado en la longitud del nombre

            // Crear una instancia de la tabla hash encadenada
            Mod1RellenarChainedHashTable hashTable = new Mod1RellenarChainedHashTable(625, 16, hashFunctions);  // 625 cabinas, 16 pasajeros por cabina

            // Organizar pasajeros por familia
            Map<Integer, List<Passenger>> families = new HashMap<>();
            for (Passenger passenger : passengers) {
                families.computeIfAbsent(passenger.getFamilyID(), k -> new ArrayList<>()).add(passenger);
            }

            // Organizar cada familia e insertar en la tabla hash
            for (List<Passenger> family : families.values()) {
                List<Passenger> organizedFamily = organizeFamily(family);
                for (Passenger passenger : organizedFamily) {
                    hashTable.add(passenger);
                }
            }

            // Mostrar el contenido de la tabla hash
            hashTable.display();

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // Función para organizar una familia (misma lógica que antes)
    private static List<Passenger> organizeFamily(List<Passenger> family) {
        List<Passenger> adults = new ArrayList<>();
        List<Passenger> minors = new ArrayList<>();

        // Separar adultos y menores de edad
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

        // Agregar los adultos restantes
        while (i < adults.size()) {
            organizedFamily.add(adults.get(i++));
        }

        // Agregar los menores restantes
        while (j < minors.size()) {
            organizedFamily.add(minors.get(j++));
        }

        return organizedFamily;
    }
}
