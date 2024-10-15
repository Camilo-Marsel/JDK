package com.example.JDK;

import com.example.JDK.models.Cabina;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Mod2BloomFilterEstadoCabinas {
    private int size; // Número de bits en el array del Bloom Filter
    private int hashCount; // Cantidad de funciones hash a usar
    private int[] bitArray; // Array de bits que almacena los elementos presentes

    /**
     * Constructor para la clase BloomFilterCabinas.
     *
     * @param size      Número de bits en el array del Bloom Filter
     * @param hashCount Número de funciones hash a usar
     */
    public Mod2BloomFilterEstadoCabinas(int size, int hashCount) {
        this.size = size;
        this.hashCount = hashCount;
        this.bitArray = new int[size]; // Inicializamos el array de bits con todos los bits en 0
    }

    /**
     * Método privado que simula las funciones hash sobre el identificador de cabina.
     *
     * @param cabina Cabina a ser hasheada
     * @return Lista de resultados de los hashes
     */
    private List<Integer> hashes(Cabina cabina) {
        List<Integer> hashResults = new ArrayList<>(); // Lista para almacenar los resultados de los hashes
        String item = "Cabina-" + cabina.getNumero();  // Convertimos el número de cabina a una cadena identificable
        byte[] itemBytes = item.getBytes(StandardCharsets.UTF_8); // Convertimos el identificador de la cabina a bytes

        for (int i = 0; i < hashCount; i++) {
            // Simulamos diferentes funciones hash sumando el índice 'i' y aplicando módulo del tamaño
            int hashResult = (itemBytes.hashCode() + i) % size;
            hashResults.add(Math.abs(hashResult)); // Almacenamos el resultado del hash
        }
        return hashResults; // Retornamos la lista de resultados de hashes
    }

    /**
     * Método para agregar una cabina al Bloom Filter.
     *
     * @param cabina Cabina a agregar
     */
    public void add(Cabina cabina) {
        for (int hashVal : hashes(cabina)) {
            // Establecemos a 1 el bit en la posición correspondiente al hash
            bitArray[hashVal] = 1;
        }
    }

    /**
     * Método para verificar si una cabina podría estar en el conjunto afectado.
     *
     * @param cabina Cabina a verificar
     * @return True si la cabina podría estar afectada, de lo contrario false
     */
    public boolean check(Cabina cabina) {
        for (int hashVal : hashes(cabina)) {
            if (bitArray[hashVal] == 0) {
                // Si algún bit correspondiente al hash está en 0, la cabina no está afectada
                return false;
            }
        }
        // Si todos los bits están en 1, la cabina podría estar afectada (con cierta probabilidad de falso positivo)
        return true;
    }

    public static void main(String[] args) {
        // Inicializamos el Bloom Filter con tamaño 50 y 3 funciones hash
        Mod2BloomFilterEstadoCabinas bf = new Mod2BloomFilterEstadoCabinas(50, 3);

        // Creamos algunas cabinas
        Cabina cabina1 = new Cabina(1, 16);
        Cabina cabina2 = new Cabina(2, 16);
        Cabina cabina3 = new Cabina(3, 16);

        // Agregamos cabinas al Bloom Filter
        bf.add(cabina1);
        bf.add(cabina2);

        // Verificamos si las cabinas están en el conjunto
        System.out.println("Cabina 1 afectada: " + bf.check(cabina1)); // True, ya que la agregamos
        System.out.println("Cabina 2 afectada: " + bf.check(cabina2)); // True, ya que la agregamos
        System.out.println("Cabina 3 afectada: " + bf.check(cabina3)); // False, no fue agregada
    }
}
