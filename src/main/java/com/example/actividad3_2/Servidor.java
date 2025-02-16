package com.example.actividad3_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*Esta clase es la que inicia un servidor en el puerto que le asignemos, como el ejercicio pide que acepte dos clientes, he optado
* por cerrar la conexion cuando se hayan conectado dos clientes, por eso la variable MAX_CLIENTES*/

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor
        final int MAX_CLIENTES = 2; // Número máximo de clientes permitidos

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {//Creo el servidor y le asigno el puerto
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            int clientesConectados = 0;

            while (clientesConectados < MAX_CLIENTES) {
                System.out.println("Esperando cliente...");
                Socket cliente = servidor.accept();//Cuando entra un cliente, lo acepto

                // Mostrar información de la conexión
                System.out.println("Cliente conectado:");
                System.out.println(" - Puerto local: " + cliente.getLocalPort());
                System.out.println(" - Puerto remoto: " + cliente.getPort());

                clientesConectados++;
            }

            System.out.println("Se han conectado " + MAX_CLIENTES + " clientes. Cerrando servidor.");
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
