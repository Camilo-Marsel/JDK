package com.example.JDK.models;

import java.util.List;

public class Cabina {
    private int indiceCabina; // Índice de la cabina
    private List<Passenger> pasajeros; // Lista de los 16 pasajeros en la cabina
    private String estadoCabina; // Estado de la cabina ("Buen estado", "Cabina averiada", "Destruida")

    // Constructor
    public Cabina(int indiceCabina, List<Passenger> pasajeros, String estadoCabina) {
        this.indiceCabina = indiceCabina;
        this.pasajeros = pasajeros;
        this.estadoCabina = estadoCabina;
    }

    // Getter y Setter para el índice de la cabina
    public int getIndiceCabina() {
        return indiceCabina;
    }

    public void setIndiceCabina(int indiceCabina) {
        this.indiceCabina = indiceCabina;
    }

    // Getter y Setter para los pasajeros
    public List<Passenger> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Passenger> pasajeros) {
        this.pasajeros = pasajeros;
    }

    // Getter y Setter para el estado de la cabina
    public String getEstadoCabina() {
        return estadoCabina;
    }

    public void setEstadoCabina(String estadoCabina) {
        this.estadoCabina = estadoCabina;
    }
}
