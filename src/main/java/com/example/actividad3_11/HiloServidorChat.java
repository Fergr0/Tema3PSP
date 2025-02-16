package com.example.actividad3_11;

import java.io.*;
import java.net.*;

public class HiloServidorChat extends Thread {
    // Entrada de datos desde el cliente
    DataInputStream fentrada;
    // Socket de la conexión actual
    Socket socket = null;
    // Objeto que gestiona las conexiones y mensajes compartidos
    ComunHilos comun;

    // Constructor de la clase, recibe un socket y un objeto ComunHilos para manejar las conexiones
    public HiloServidorChat(Socket s, ComunHilos comun) {
        this.socket = s;
        this.comun = comun;
        try {
            // Inicializamos el flujo de entrada del socket
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }

    // Método que se ejecuta cuando se inicia el hilo
    public void run() {
        // Muestra el número de conexiones actuales
        System.out.println("NÚMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
        // Obtiene los mensajes actuales y los envía a todos los clientes
        String texto = comun.getMensajes();
        EnviarMensajesaTodos(texto);

        // Bucle que escucha los mensajes de los clientes
        while (true) {
            String cadena = "";
            try {
                // Lee el mensaje del cliente
                cadena = fentrada.readUTF();
                // Si el mensaje es "*", indica que el cliente quiere desconectarse
                if (cadena.trim().equals("*")) {
                    // Decrementa el número de conexiones activas
                    comun.setACTUALES(comun.getACTUALES() - 1);
                    System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
                    break; // Sale del bucle
                }

                // Agrega el mensaje recibido a los mensajes generales
                comun.setMensajes(comun.getMensajes() + cadena + "\n");
                // Envía los mensajes a todos los clientes
                EnviarMensajesaTodos(comun.getMensajes());
            } catch (Exception e) {
                e.printStackTrace();
                break; // En caso de error, sale del bucle
            }
        }

        try {
            // Cierra el socket cuando el cliente se desconecta
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método que envía los mensajes a todos los clientes conectados
    private void EnviarMensajesaTodos(String texto) {
        int i;
        // Itera sobre todos los sockets almacenados en la tabla de conexiones
        for (i = 0; i < comun.getCONEXIONES(); i++) {
            Socket s1 = comun.getElementoTabla(i);
            // Solo envía el mensaje si el socket no está cerrado
            if (!s1.isClosed()) {
                try {
                    // Envía el mensaje a través del flujo de salida del socket
                    DataOutputStream fsalida2 = new DataOutputStream(s1.getOutputStream());
                    fsalida2.writeUTF(texto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
