package com.example.actividad3_6;

import java.io.InterruptedIOException;
import java.net.*;
import java.util.Scanner;

/*ESte cliente tiene como caracteristica que si no recibe respuesta del servidor en 5s se bloquea y se cierra indicando que se perdieron paquetes */

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 12345; // Puerto del servidor
        final int TIMEOUT = 5000; // Tiempo de espera en milisegundos
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            socket.setSoTimeout(TIMEOUT);
            System.out.println("Cliente UDP iniciado. Conectado al servidor en el puerto " + PUERTO);

            while (true) {
                System.out.print("Introduce una cadena (o * para salir): ");
                String mensaje = scanner.nextLine();

                // Enviar mensaje al servidor
                byte[] bufferEnvio = mensaje.getBytes();
                InetAddress direccionServidor = InetAddress.getByName(SERVIDOR);
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        bufferEnvio, bufferEnvio.length, direccionServidor, PUERTO);
                socket.send(paqueteEnvio);

                // Verificar si el mensaje es un asterisco para finalizar
                if (mensaje.equals("*")) {
                    System.out.println("Finalizando cliente.");
                    break;
                }

                try {
                    // Recibir respuesta del servidor
                    DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paqueteRecibido);

                    String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                    System.out.println("Respuesta del servidor: " + respuesta);
                } catch (InterruptedIOException e) {
                    // Manejar pérdida de paquete
                    System.out.println("Tiempo de espera agotado. Paquete perdido.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}