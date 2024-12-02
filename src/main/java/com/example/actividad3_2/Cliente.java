package com.example.actividad3_2;

import java.io.IOException;
import java.net.Socket;

/*Esta clase simula ser el cliente que se conecta al servidor que creamos antes y nos da la info
* de los puertos a los que se coneta su socket, es decir el que usa el cliente y al que se conecta del servidor*/

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345;// Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO)) {//Creo el server al que se conectara, y el puerto
            // Mostrar información de la conexión
            System.out.println("Cliente conectado al servidor:");
            System.out.println(" - Dirección IP remota: " + socket.getInetAddress().getHostAddress());
            System.out.println(" - Puerto remoto: " + socket.getPort());
            System.out.println(" - Puerto local: " + socket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}

