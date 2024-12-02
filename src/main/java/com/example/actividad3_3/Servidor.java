package com.example.actividad3_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor
        final int MAX_CLIENTES = 1; // Número máximo de clientes permitidos

        try (ServerSocket servidor = new ServerSocket(PUERTO)) { // Crear el servidor
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            int clientesConectados = 0;

            while (clientesConectados < MAX_CLIENTES) {
                System.out.println("Esperando cliente...");
                Socket cliente = servidor.accept(); // Aceptar conexión

                // Enviar y recibir mensajes
                try (
                        DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
                        DataInputStream in = new DataInputStream(cliente.getInputStream())
                ) {
                    System.out.println("Cliente conectado.");

                    // Enviar mensaje al cliente
                    out.writeUTF("HOLA CLIENTE");
                    System.out.println("Mensaje enviado al cliente.");

                    // Recibir mensaje en minúsculas del cliente
                    String mensajeCliente = in.readUTF();
                    System.out.println("Mensaje del cliente: " + mensajeCliente);
                }

                clientesConectados++;
            }

            System.out.println("Se han conectado " + MAX_CLIENTES + " clientes. Cerrando servidor.");
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
