package com.example.actividad3_8.actividad3_7UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/* Cliente UDP que envía un objeto Numeros al servidor y recibe otro objeto Numeros actualizado */
public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;
        final int BUFFER_SIZE = 1024;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner entrada = new Scanner(System.in)) {

            while (true) {
                System.out.println("Introduzca un número (0 para salir):");
                int numero = entrada.nextInt();

                // Crear y serializar el objeto Numeros
                Numeros n1 = new Numeros(numero);
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(n1);

                byte[] datosEnvio = byteOut.toByteArray();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, InetAddress.getByName(SERVIDOR), PUERTO);
                socket.send(paqueteEnvio);

                if (numero == 0) {
                    System.out.println("Finalizando cliente.");
                    break;
                }

                // Recibir respuesta del servidor
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRespuesta);

                // Deserializar la respuesta
                ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                ObjectInputStream in = new ObjectInputStream(byteIn);
                Numeros respuesta = (Numeros) in.readObject();

                // Mostrar cuadrado y cubo
                System.out.println("Respuesta del servidor: " + respuesta);
                System.out.println("Número: " + respuesta.getNumero());
                System.out.println("Cuadrado: " + respuesta.getCuadrado());
                System.out.println("Cubo: " + respuesta.getCubo());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
