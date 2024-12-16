package com.example.actividad3_7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*Esta clase cliente se encarga de enviar un objeto a un servidor y recibir otro objeto de ese tipo que el servidor le devuelve
* ademas muestra algunos atributos por consola y controla errores tales como que el servidor no esté arrancado */

public class Cliente {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO)) { // Crear conexión con el servidor
            System.out.println("Conectado al servidor.");

            // Enviar objetos al servidor hasta que se introduzca un 0
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                while (true) {
                    System.out.println("Introduzca un número (0 para salir):");
                    int numero = entrada.nextInt();

                    Numeros n1 = new Numeros(numero);
                    out.writeObject(n1); // Enviar el objeto al servidor

                    if (numero == 0) {
                        System.out.println("Finalizando conexión.");
                        break;
                    }

                    // Recibir respuesta del servidor (objeto actualizado)
                    Numeros respuesta = (Numeros) in.readObject();

                    // Mostrar cuadrado y cubo
                    System.out.println("Número: " + respuesta.getNumero());
                    System.out.println("Cuadrado: " + respuesta.getCuadrado());
                    System.out.println("Cubo: " + respuesta.getCubo());
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error al recibir el objeto del servidor: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
