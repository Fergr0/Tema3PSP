package com.example.actividad3_5;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO)) { // Crear conexión con el servidor
            System.out.println("Conectado al servidor.");

            // Recibir mensaje del servidor
            try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
                String mensaje = in.readUTF();
                System.out.println("Mensaje del servidor: " + mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
