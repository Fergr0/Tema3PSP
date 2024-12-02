package com.example.actividad3_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO)) { // Crear conexión con el servidor
            System.out.println("Conectado al servidor.");

            // Abro la entrada y salida del canal o flujo
            try (
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                // Recibir mensaje del servidor
                String mensajeServidor = in.readUTF();
                System.out.println("Mensaje del servidor: " + mensajeServidor);

                // Convertir el mensaje a minúsculas y enviarlo al servidor
                String mensajeMinusculas = mensajeServidor.toLowerCase();
                out.writeUTF(mensajeMinusculas);
                System.out.println("Mensaje enviado al servidor: " + mensajeMinusculas);
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
