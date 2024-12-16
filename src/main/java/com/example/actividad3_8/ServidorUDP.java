package com.example.actividad3_8;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/* Servidor UDP que recibe un objeto Persona, lo modifica y lo envía de vuelta al cliente */
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

                // Deserializar el objeto Persona recibido
                ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                ObjectInputStream in = new ObjectInputStream(byteIn);
                Persona personaRecibida = (Persona) in.readObject();

                System.out.println("Objeto Persona recibido: " + personaRecibida);

                // Modificar los datos del objeto Persona
                personaRecibida.setNombre(personaRecibida.getNombre() + " Modificado");
                personaRecibida.setEdad(personaRecibida.getEdad() + 10);

                System.out.println("Objeto Persona modificado: " + personaRecibida);

                // Serializar y enviar el objeto modificado de vuelta al cliente
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(personaRecibida);

                byte[] datosRespuesta = byteOut.toByteArray();
                DatagramPacket paqueteSalida = new DatagramPacket(datosRespuesta, datosRespuesta.length, paqueteEntrada.getAddress(), paqueteEntrada.getPort());
                socket.send(paqueteSalida);

                System.out.println("Objeto Persona enviado de vuelta al cliente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
