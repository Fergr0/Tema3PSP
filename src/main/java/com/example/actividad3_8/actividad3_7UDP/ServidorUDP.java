package com.example.actividad3_8.actividad3_7UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/* Servidor UDP que recibe un objeto Numeros, calcula el cuadrado y cubo y lo envía de vuelta */
public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 12345;

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP iniciado y esperando conexiones...");

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(paqueteEntrada);
                System.out.println("Paquete recibido de: " + paqueteEntrada.getAddress());

                // Deserializar el objeto recibido
                ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                ObjectInputStream in = new ObjectInputStream(byteIn);
                Numeros recibido = (Numeros) in.readObject();

                System.out.println("Objeto recibido: " + recibido);
                int numero = recibido.getNumero();

                // Finalizar si el número es 0 o menor
                if (numero <= 0) {
                    System.out.println("Número recibido es 0 o menor. Finalizando servidor.");
                    break;
                }

                // Calcular cuadrado y cubo
                long cuadrado = (long) numero * numero;
                long cubo = (long) numero * numero * numero;

                // Crear un nuevo objeto Numeros
                Numeros respuesta = new Numeros(numero, cuadrado, cubo);

                // Serializar y enviar el objeto de respuesta
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(respuesta);

                byte[] datosRespuesta = byteOut.toByteArray();
                DatagramPacket paqueteSalida = new DatagramPacket(datosRespuesta, datosRespuesta.length, paqueteEntrada.getAddress(), paqueteEntrada.getPort());
                socket.send(paqueteSalida);

                System.out.println("Respuesta enviada: " + respuesta);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}

