package com.example.actividad3_5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente_actividad_1 {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor.");

            String cadena;
            while (true) {
                // Leer cadena desde teclado
                System.out.print("Introduce una cadena (o * para salir): ");
                cadena = scanner.nextLine();

                // Enviar cadena al servidor
                out.writeUTF(cadena);

                // Verificar si la cadena es un asterisco para finalizar
                if (cadena.contains("*")) {
                    System.out.println("Cerrando conexión con el servidor.");
                    break;
                }

                // Recibir longitud desde el servidor
                int longitud = in.readInt();
                System.out.println("El servidor dice que la longitud de la cadena es: " + longitud);
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
