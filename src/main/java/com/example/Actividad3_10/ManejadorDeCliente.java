package com.example.Actividad3_10;

import java.io.*;
import java.net.*;

public class ManejadorDeCliente implements Runnable {
    private Socket socket; // Socket para la comunicación con el cliente
    private BufferedReader lector; // Para leer los mensajes del cliente
    private PrintWriter escritor; // Para enviar mensajes al cliente
    private static final int NUMERO_A_ADIVINAR = 15; // Número fijo que los jugadores deben adivinar

    public ManejadorDeCliente(Socket socket) {
        this.socket = socket; // Recibo el socket del cliente
        try {
            // Inicializo los streams de entrada y salida
            this.lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.escritor = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo errores que puedan haber de entrada/salida
        }
    }

    @Override
    public void run() {
        try {
            String mensaje;
            // Leo los mensajes enviados por el cliente mientras no sean nulos
            while ((mensaje = lector.readLine()) != null) {
                if (mensaje.equals("*")) { // Si el cliente envía "*", termina el juego
                    escritor.println("Juego terminado. ¡Gracias por jugar!");
                    socket.close(); // Cierro el socket
                    return;
                }

                try {
                    int numeroAdivinado = Integer.parseInt(mensaje); // Convierto el mensaje a un número entero

                    // Comparo el número adivinado con el número fijo
                    if (numeroAdivinado == NUMERO_A_ADIVINAR) {
                        escritor.println("¡Correcto!"); // Respuesta correcta
                        socket.close(); // Cierro la conexión al acertar
                        return;
                    } else if (numeroAdivinado < NUMERO_A_ADIVINAR) {
                        escritor.println("Número demasiado pequeño."); // Número menor
                    } else {
                        escritor.println("Número demasiado grande."); // Número mayor
                    }
                } catch (NumberFormatException e) {
                    // Manejo el caso en que el cliente no envíe un número válido
                    escritor.println("Por favor, ingresa un número válido.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // manejo los errores que puedan haber de entrada o salida
        }
    }
}
