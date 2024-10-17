package com.example.JDK.view;

import com.example.JDK.controller.CabinaController;
import com.example.JDK.models.Passenger;
import com.example.JDK.models.Cabina;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class CabinaView {
    private final CabinaController cabinaController; // Cambiar a no estático
    private final Scanner scanner;
    static List<Cabina> cabinas;

    // Constructor que recibe el controlador
    public CabinaView(CabinaController cabinaController) {
        this.cabinaController = cabinaController;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("Selecciona una opción:");
            System.out.println("1. Mostrar cabins");
            System.out.println("2. Aplicar evento de meteoritos");
            System.out.println("3. Aplicar evento de despresurización");
            System.out.println("4. Aplicar evento de impacto láser");
            System.out.println("5. Aplicar evento de fallo en el sistema de energía");
            System.out.println("6. Aplicar evento de colisión espacial");
            System.out.println("7. Aplicar evento de fallo en el sistema de soporte de vida");
            System.out.println("8. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    mostrarCabinas(cabinas);
                    break;
                case 2:
                    cabinaController.eventoMeteoritos();
                    break;
                case 3:
                    cabinaController.eventoDespresurizacion();
                    break;
                case 4:
                    cabinaController.eventoImpactoLaser();
                    break;
                case 5:
                    cabinaController.eventoFalloSistemaEnergia();
                    break;
                case 6:
                    cabinaController.eventoColisionEspacial();
                    break;
                case 7:
                    cabinaController.eventoFalloSistemaVida();
                    break;
                case 8:
                    System.out.println("JDK se despide!");
                    return;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
            }
        }
    }
    public static void mostrarCabinas(List<Cabina> cabinas) {
        for (Cabina cabina : cabinas) {
            System.out.println("Cabina " + cabina.getIndiceCabina() + " - Estado: " + cabina.getEstadoCabina());
            // Opcional: Mostrar los pasajeros en cada cabina
            for (Passenger passenger : cabina.getPasajeros()) {
                System.out.println("    Pasajero: " + passenger.getName() + " " + passenger.getSurname() + " Edad: " + passenger.getAge());
            }
        }
    }
    public static void main(String[] args) {
        try {
            // Definir las funciones hash
            List<Function<Passenger, Integer>> hashFunctions = new ArrayList<>();
            hashFunctions.add(Passenger::getFamilyID);
            hashFunctions.add(Passenger::getAge);
            hashFunctions.add(p -> p.getName().length());

            // Inicializar el controlador
            CabinaController controller = new CabinaController(625, 16, hashFunctions);

            // Leer pasajeros desde el archivo JSON
            List<Passenger> passengers = controller.readPassengers("src/main/resources/passengers.json");

            // Organizar pasajeros por familia
            Map<Integer, List<Passenger>> families = new HashMap<>();
            for (Passenger passenger : passengers) {
                families.computeIfAbsent(passenger.getFamilyID(), k -> new ArrayList<>()).add(passenger);
            }

            // Organizar cada familia e insertar en las cabinas
            for (List<Passenger> family : families.values()) {
                List<Passenger> organizedFamily = controller.organizeFamily(family);
                for (Passenger passenger : organizedFamily) {
                    controller.addPassenger(passenger);
                }
            }

            // Inicializar las cabinas
            cabinas = controller.initializeCabins();
            // hasta aqui modulo 1


            //modulo 2 inicia
            mostrarCabinas(cabinas);
            CabinaView cabinaView = new CabinaView(controller); // Cambia a 'controller'
            cabinaView.mostrarMenu();
            //modulo 2 termina
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo JSON: " + e.getMessage());
        }
    }
}

