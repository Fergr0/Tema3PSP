package com.example.actividad3_7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*Esta clase servidor se encarga de esperar conexiones de clientes y recibir un objeto de tipo número.
* Calcula y setea x atributos y devuelve otra instancia de ese objeto a el cliente*/

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 12345; // Puerto del servidor

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Cliente conectado.");

                    // Manejo de la conexión con el cliente
                    try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                        while (true) {
                            // Recibir objeto Numeros del cliente
                            Numeros recibido = (Numeros) in.readObject();
                            System.out.println("Objeto recibido del cliente: " + recibido);

                            // Obtener el número del objeto
                            int numero = recibido.getNumero();

                            // Finalizar si el número es 0 o menor
                            if (numero <= 0) {
                                System.out.println("El cliente ha enviado un número 0 o menor. Cerrando conexión.");
                                break;
                            }

                            // Calcular cuadrado y cubo
                            long cuadrado = (long) numero * numero;
                            long cubo = (long) numero * numero * numero;

                            // Crear un nuevo objeto con los cálculos
                            Numeros respuesta = new Numeros(numero, cuadrado, cubo);
                            System.out.println("Enviando respuesta al cliente: " + respuesta);

                            // Enviar el objeto de respuesta al cliente
                            out.writeObject(respuesta);
                            out.flush();
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println("Error al leer el objeto: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.err.println("Error al manejar al cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}

