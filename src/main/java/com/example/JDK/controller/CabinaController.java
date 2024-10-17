package com.example.JDK.controller;

import com.example.JDK.models.Passenger;
import com.example.JDK.models.Cabina;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class CabinaController {
    private final int size;
    private final int maxPassengersPerBucket;
    private final List<Function<Passenger, Integer>> hashFunctions;
    private final List<List<Passenger>> table;
    private List<Cabina> cabinas; // Lista de cabinas

    // Constructor de la tabla hash encadenada
    public CabinaController(int size, int maxPassengersPerBucket, List<Function<Passenger, Integer>> hashFunctions) {
        this.size = size;
        this.maxPassengersPerBucket = maxPassengersPerBucket;
        this.hashFunctions = hashFunctions;
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new ArrayList<>());
        }
        this.cabinas = initializeCabins(); // Inicializar las cabinas en el constructor
    }

    // Método para agregar pasajeros a la tabla hash
    public void addPassenger(Passenger passenger) {
        List<Integer> hashValues = new ArrayList<>();
        for (Function<Passenger, Integer> hashFunction : hashFunctions) {
            hashValues.add(Math.abs(hashFunction.apply(passenger)) % size);
        }
        int index = hashValues.stream().min(Integer::compareTo).orElse(0);

        for (int i = 0; i < size; i++) {
            int currentIndex = (index + i) % size;
            List<Passenger> bucket = table.get(currentIndex);
            if (bucket.size() < maxPassengersPerBucket) {
                bucket.add(passenger);
                return;
            }
        }
        System.out.println("No hay espacio disponible para agregar al pasajero: " + passenger.getName() + " " + passenger.getSurname());
    }

    // Método para organizar una familia de pasajeros
    public List<Passenger> organizeFamily(List<Passenger> family) {
        List<Passenger> adults = new ArrayList<>();
        List<Passenger> minors = new ArrayList<>();

        for (Passenger passenger : family) {
            if (passenger.getAge() >= 18) {
                adults.add(passenger);
            } else {
                minors.add(passenger);
            }
        }

        List<Passenger> organizedFamily = new ArrayList<>();
        int i = 0, j = 0;

        while (i < adults.size() && j < minors.size()) {
            organizedFamily.add(adults.get(i++));
            organizedFamily.add(minors.get(j++));
        }

        while (i < adults.size()) {
            organizedFamily.add(adults.get(i++));
        }

        while (j < minors.size()) {
            organizedFamily.add(minors.get(j++));
        }

        return organizedFamily;
    }

    // Método para mostrar el contenido de la tabla hash
    public void displayCabinas() {
        for (int i = 0; i < table.size(); i++) {
            System.out.print("Cabina " + i + ": ");
            for (Passenger passenger : table.get(i)) {
                System.out.print(passenger.getName() + " " + passenger.getSurname() + " " + passenger.getAge() + " " + passenger.getFamilyID() + "; ");
            }
            System.out.println();
        }
    }

    // Método para leer pasajeros desde un archivo JSON
    public List<Passenger> readPassengers(String filePath) throws IOException {
        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<>() {});
    }

    // Método para inicializar las cabinas
    public List<Cabina> initializeCabins() {
        List<Cabina> cabins = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            List<Passenger> passengersInBucket = table.get(i);
            Cabina cabina = new Cabina(i, passengersInBucket, "Buen estado");
            cabins.add(cabina);
        }

        return cabins;
    }

    // Método para aplicar un evento aleatorio que afecta entre 1 y 50 cabinas
    private void aplicarEvento(String tipoEvento) {
        Random rand = new Random();
        int numCabinasAfectadas = rand.nextInt(50) + 1;  // Afecta entre 1 y 50 cabinas

        for (int i = 0; i < numCabinasAfectadas; i++) {
            int cabinaIndex = rand.nextInt(cabinas.size());  // Seleccionar cabina aleatoria
            Cabina cabina = cabinas.get(cabinaIndex);

            // Cambiar el estado aleatoriamente entre "Cabina averiada" o "Cabina destruida"
            String nuevoEstado = rand.nextBoolean() ? "Cabina averiada" : "Cabina destruida";
            cabina.setEstadoCabina(nuevoEstado);

            System.out.println("Evento " + tipoEvento + " afectó a la cabina " + cabinaIndex + ": " + nuevoEstado);
        }
    }

    // Ejemplo de 6 eventos diferentes que afectan las cabinas
    public void eventoMeteoritos() {
        aplicarEvento("Meteoritos");
    }

    public void eventoDespresurizacion() {
        aplicarEvento("Despresurización");
    }

    public void eventoImpactoLaser() {
        aplicarEvento("Impacto Láser");
    }

    public void eventoFalloSistemaEnergia() {
        aplicarEvento("Fallo en el Sistema de Energía");
    }

    public void eventoColisionEspacial() {
        aplicarEvento("Colisión Espacial");
    }

    public void eventoFalloSistemaVida() {
        aplicarEvento("Fallo en el Sistema de Soporte de Vida");
    }
}
