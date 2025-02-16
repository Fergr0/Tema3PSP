package com.example.actividad3_11;

import java.io.*;
import java.net.*;

public class ServidorChat {
    // Defino el número máximo de conexiones
    static final int MAXIMO = 10;

    public static void main(String args[]) throws IOException {
        // Defino el puerto del servidor
        int PUERTO = 44444;

        // Creo un servidor de sockets que escucha en el puerto definido
        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        // Creo un arreglo de sockets para manejar múltiples clientes
        Socket tabla[] = new Socket[MAXIMO];

        // Creo el objeto ComunHilos para gestionar las conexiones y mensajes compartidos
        ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla);

        // El servidor acepta conexiones mientras haya espacio para más clientes
        while (comun.getCONEXIONES() < MAXIMO) {
            // Espero una conexión de un cliente
            Socket socket = servidor.accept();

            // Añado el nuevo socket a la tabla de conexiones
            comun.addTabla(socket, comun.getCONEXIONES());

            // Incremento el número de conexiones activas y el total de conexiones
            comun.setACTUALES(comun.getACTUALES() + 1);
            comun.setCONEXIONES(comun.getCONEXIONES() + 1);

            // Creo un hilo para manejar la comunicación con este cliente
            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            hilo.start();
        }

        // Cierro el servidor cuando se alcanza el máximo de conexiones
        servidor.close();
    }
}
