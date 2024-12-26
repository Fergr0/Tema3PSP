package com.example.actividad3_11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

    static final int MAXIMO = 10;

    public static void main(String[] args) throws IOException {
        int PUERTO = 44444;

        ServerSocket servidor = new ServerSocket(PUERTO);

        System.out.println("Servidor conectado....");
        Socket tabla[]= new Socket[MAXIMO];
        ComunHilos comun = new ComunHilos(MAXIMO,0,0,tabla);
        while (comun.getConexiones()<MAXIMO){
            Socket socket = new Socket();
            socket = servidor.accept();

            comun.addTabla(socket, comun.getConexiones());
            comun.setActuales(comun.getActuales()+1);
            comun.setConexiones(comun.getConexiones());
            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            hilo.start();
        }
        servidor.close();

    }

}
