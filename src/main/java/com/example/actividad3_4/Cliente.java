package com.example.actividad3_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO)) { // Crear conexión con el servidor
            System.out.println("Conectado al servidor.");

            // Abro la entrada y salida del canal o flujo
            try (
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(System.in)
            ) {
                // Pedir al usuario que introduzca un número
                System.out.print("Introduce un número: ");
                int numero = scanner.nextInt();

                // Enviar el número al servidor
                out.writeInt(numero);
                System.out.println("Número enviado al servidor: " + numero);

                // Recibir el cuadrado del número del servidor
                int cuadrado = in.readInt();
                System.out.println("El cuadrado del número recibido del servidor es: " + cuadrado);
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
