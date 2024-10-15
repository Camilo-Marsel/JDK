package com.example.JDK.models;
import java.util.ArrayList;
import java.util.List;

public class Cabina {
    private int numero; // Identificador de la cabina
    private int capacidadMaxima; // Número máximo de pasajeros por cabina
    private List<Passenger> pasajeros; // Lista de pasajeros en la cabina

    // Constructor
    public Cabina(int numero, int capacidadMaxima) {
        this.numero = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.pasajeros = new ArrayList<>();
    }

    // Método para agregar un pasajero a la cabina
    public boolean agregarPasajero(Passenger pasajero) {
        if (estaLlena()) {
            return false; // Si la cabina está llena, no se puede agregar
        }
        pasajeros.add(pasajero);
        return true;
    }

    // Método para verificar si la cabina está llena
    public boolean estaLlena() {
        return pasajeros.size() >= capacidadMaxima;
    }

    // Obtener la lista de pasajeros
    public List<Passenger> getPasajeros() {
        return pasajeros;
    }

    // Obtener el número de la cabina
    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cabina ").append(numero).append(": ");
        for (Passenger pasajero : pasajeros) {
            sb.append(pasajero.getName()).append(" ").append(pasajero.getSurname()).append("; ");
        }
        return sb.toString();
    }
}
