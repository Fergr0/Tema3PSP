package com.example.Actividad3_10;

import java.io.*;
import java.net.*;

public class Servidor {
    private static final int PUERTO = 55555; // Puerto en el que escuchará el servidor

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado, esperando jugadores...");

            // Bucle para aceptar múltiples conexiones de los jugadores
            while (true) {
                Socket socketCliente = servidor.accept(); // Acepto la conexión de un cliente
                System.out.println("Jugador conectado desde: " + socketCliente.getInetAddress());

                // Inicio un nuevo hilo para manejar al cliente
                new Thread(new ManejadorDeCliente(socketCliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo los errores del servidor
        }
    }
}
