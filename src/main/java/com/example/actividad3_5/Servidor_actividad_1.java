package com.example.actividad3_5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*Servidor que espera a que un cliente se conecte, y le vaya pasando cadenas, el servidor le pasa al cliente un numero que
* indica el numero de caracteres de la cadena que el cliente envio, si la cadena contiene un * el porgrama finaliza*/

public class Servidor_actividad_1 {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor

        try (ServerSocket servidor = new ServerSocket(PUERTO)) { // Crear el servidor
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                System.out.println("Esperando cliente...");
                try (Socket cliente = servidor.accept(); // Aceptar conexión
                     DataInputStream in = new DataInputStream(cliente.getInputStream());
                     DataOutputStream out = new DataOutputStream(cliente.getOutputStream())) {

                    System.out.println("Cliente conectado.");

                    String cadena;
                    while (true) {
                        // Recibir cadena del cliente
                        cadena = in.readUTF();
                        System.out.println("Cadena recibida del cliente: " + cadena);

                        // Verificar si la cadena es un asterisco para finalizar
                        if (cadena.contains("*")) {
                            System.out.println("El cliente ha enviado un asterisco. Cerrando conexión.");
                            break;
                        }

                        // Calcular la longitud de la cadena
                        int longitud = cadena.length();
                        System.out.println("Enviando longitud al cliente: " + longitud);

                        // Enviar longitud al cliente
                        out.writeInt(longitud);
                    }
                } catch (IOException e) {
                    System.err.println("Error en la conexión con el cliente: " + e.getMessage());
                }
                System.out.println("Cliente desconectado.");
                break; // Termina el servidor tras procesar el cliente
            }

            System.out.println("Servidor finalizado.");
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
