package com.example.actividad3_5;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor
        final int MAX_CLIENTES = 3; // Número máximo de clientes permitidos

        try (ServerSocket servidor = new ServerSocket(PUERTO)) { // Crear el servidor
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            int clientesConectados = 0;

            while (clientesConectados < MAX_CLIENTES) {
                System.out.println("Esperando cliente...");
                Socket cliente = servidor.accept(); // Aceptar conexión

                int numeroCliente = clientesConectados + 1; // Número de cliente actual
                System.out.println("Cliente " + numeroCliente + " conectado.");

                // Enviar mensaje al cliente indicando su número
                try (DataOutputStream out = new DataOutputStream(cliente.getOutputStream())) {
                    out.writeUTF("Eres el cliente número " + numeroCliente);
                    System.out.println("Mensaje enviado al cliente " + numeroCliente);
                }

                clientesConectados++;
            }

            System.out.println("Se han conectado " + MAX_CLIENTES + " clientes. Cerrando servidor.");
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
