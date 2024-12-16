package com.example.actividad3_8.actividad3;

import java.io.*;
import java.net.*;
import java.util.*;

/* Clase principal del servidor que inicializa unos cursos y unos alumnos, espera que un cliente le pase un ID de alumno,
* si existe envia dicho alumno, sino, envia un nuevo alumno con los campos indicando que no existe dicho alumno */
public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 12345;
        List<Alumno> alumnos = new ArrayList<>();

        // Inicialización de alumnos y cursos
        Curso curso1 = new Curso("C01", "Matemáticas");
        Curso curso2 = new Curso("C02", "Historia");
        Curso curso3 = new Curso("C03", "Ciencias");

        alumnos.add(new Alumno("A01", "Juan Pérez", curso1, 85));
        alumnos.add(new Alumno("A02", "María López", curso2, 90));
        alumnos.add(new Alumno("A03", "Carlos Ruiz", curso3, 78));
        alumnos.add(new Alumno("A04", "Ana Gómez", curso1, 88));
        alumnos.add(new Alumno("A05", "Luis Martínez", curso2, 92));

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP iniciado en el puerto " + PUERTO);

            byte[] bufferEntrada = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

            while (true) {
                // Recibir consulta del cliente
                socket.receive(paqueteEntrada);
                ByteArrayInputStream byteIn = new ByteArrayInputStream(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                ObjectInputStream in = new ObjectInputStream(byteIn);
                String idAlumnoSolicitado = (String) in.readObject();

                System.out.println("ID Alumno solicitado: " + idAlumnoSolicitado);

                // Buscar al alumno
                Alumno alumnoEncontrado = alumnos.stream()
                        .filter(alumno -> alumno.getIdAlumno().equals(idAlumnoSolicitado))
                        .findFirst()
                        .orElse(new Alumno("No existe", "Alumno no encontrado", new Curso("N/A", "N/A"), 0));

                // Enviar respuesta al cliente
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(alumnoEncontrado);

                byte[] datosRespuesta = byteOut.toByteArray();
                DatagramPacket paqueteSalida = new DatagramPacket(datosRespuesta, datosRespuesta.length, paqueteEntrada.getAddress(), paqueteEntrada.getPort());
                socket.send(paqueteSalida);

                System.out.println("Respuesta enviada al cliente: " + alumnoEncontrado);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}