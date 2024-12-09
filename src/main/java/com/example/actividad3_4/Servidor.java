package com.example.actividad3_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*Este java simula un servidor en el puerto 12345 esperando la conexion de un cliente, una vez se conecte el cliente, este puede enviarle un número y el
* servidor le devolvera el cuadrado de dicho numero al cliente para que pueda asi mostrarlo*/

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

                    // Recibir número del cliente
                    int numeroCliente = in.readInt();
                    System.out.println("Número recibido del cliente: " + numeroCliente);

                    // Calcular el cuadrado y enviarlo al cliente
                    int cuadrado = numeroCliente * numeroCliente;
                    out.writeInt(cuadrado);
                    System.out.println("Cuadrado enviado al cliente: " + cuadrado);
                }

                clientesConectados++;
            }

            System.out.println("Se han conectado " + MAX_CLIENTES + " clientes. Cerrando servidor.");
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
