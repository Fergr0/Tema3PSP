package com.example.actividad3_8;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/* Cliente UDP que envía un objeto Persona al servidor y recibe otro objeto Persona modificado */
public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;
        final int BUFFER_SIZE = 1024;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner entrada = new Scanner(System.in)) {

            System.out.println("Introduzca el nombre de la persona:");
            String nombre = entrada.nextLine();

            System.out.println("Introduzca la edad de la persona:");
            int edad = entrada.nextInt();

            // Crear y serializar el objeto Persona
            Persona persona = new Persona(nombre, edad);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(persona);

            byte[] datosEnvio = byteOut.toByteArray();
            DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, InetAddress.getByName(SERVIDOR), PUERTO);
            socket.send(paqueteEnvio);

            System.out.println("Objeto Persona enviado al servidor: " + persona);

            // Recibir respuesta del servidor
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteRespuesta);

            // Deserializar la respuesta
            ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            Persona personaModificada = (Persona) in.readObject();

            // Mostrar los datos del objeto Persona recibido
            System.out.println("Objeto Persona recibido del servidor: " + personaModificada);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
