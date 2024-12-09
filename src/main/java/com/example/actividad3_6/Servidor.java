package com.example.actividad3_6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*Servidor que le pasa a un cliente la cadena que el cliente le pase al servidor y este la devuelve en minusculas, si es un * finaliza*/

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP iniciado en el puerto " + PUERTO);

            while (true) {
                // Recibir paquete del cliente
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Mensaje recibido del cliente: " + mensaje);

                // Verificar si el mensaje es un asterisco para terminar
                if (mensaje.equals("*")) {
                    System.out.println("El cliente ha enviado un asterisco. Cerrando servidor.");
                    break;
                }

                // Convertir mensaje a minúsculas
                String mensajeMinusculas = mensaje.toLowerCase();

                // Enviar mensaje convertido al cliente
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();
                byte[] bufferRespuesta = mensajeMinusculas.getBytes();

                DatagramPacket paqueteRespuesta = new DatagramPacket(
                        bufferRespuesta, bufferRespuesta.length, direccionCliente, puertoCliente);
                socket.send(paqueteRespuesta);

                System.out.println("Mensaje en minúsculas enviado al cliente: " + mensajeMinusculas);
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}