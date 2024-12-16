package com.example.actividad3_8.actividad3;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/*Cliente que envia una cadena que puede o no corresponderse con el id de un objeto Alumno, se lo envia a un server y este
* devuelve un objeto alumno si el id existe y si no, inicializa uno con campos que indican que no existe ese alumno
* ademas, este cliente mustra los datos de dicho cliente y su curso
* si se inserta un *, se acaba el programa*/

public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;
        final int BUFFER_SIZE = 1024;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner entrada = new Scanner(System.in)) {

            while (true) {
                System.out.println("Introduzca el ID del alumno a consultar (* para salir):");
                String idAlumno = entrada.nextLine();

                if (idAlumno.equals("*")) {
                    System.out.println("Saliendo del programa.");
                    break;
                }

                // Enviar consulta al servidor
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(idAlumno);

                byte[] datosEnvio = byteOut.toByteArray();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, InetAddress.getByName(SERVIDOR), PUERTO);
                socket.send(paqueteEnvio);

                System.out.println("Consulta enviada al servidor: ID Alumno = " + idAlumno);

                // Recibir respuesta del servidor
                byte[] bufferRespuesta = new byte[BUFFER_SIZE];
                DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                socket.receive(paqueteRespuesta);

                // Deserializar respuesta
                ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                ObjectInputStream in = new ObjectInputStream(byteIn);
                Alumno alumnoRecibido = (Alumno) in.readObject();

                // Mostrar información del alumno recibido
                System.out.println("Datos del alumno recibido:");
                System.out.println("ID: " + alumnoRecibido.getIdAlumno());
                System.out.println("Nombre: " + alumnoRecibido.getNombre());
                System.out.println("Curso: " + alumnoRecibido.getCurso().getDescripcion());
                System.out.println("Nota: " + alumnoRecibido.getNota());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
