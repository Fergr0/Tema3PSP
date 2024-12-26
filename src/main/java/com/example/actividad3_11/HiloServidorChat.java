package com.example.actividad3_11;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorChat extends Thread {
    DataInputStream fentrada;
    Socket socket = null;
    ComunHilos comun;

    public HiloServidorChat(Socket s, ComunHilos comun){
        this.socket = s;
        this.comun = comun;
        try{
            fentrada = new DataInputStream(socket.getInputStream());
        }catch (IOException e){
            System.out.println("Error de E/S");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Número de conexiones actuales: " + comun.getActuales());
        String texto = comun.getMensajes();
        EnviarMensajesAtodos(texto);
        while (true){
            String cadena = "";
            try{
                cadena = fentrada.readUTF();
                if(cadena.trim().equals("*")){
                    comun.setActuales(comun.getActuales()-1);
                    System.out.println("Número de conexiones actuales: " + comun.getActuales());
                    break;
                }
                comun.setMensajes(comun.getMensajes() + cadena + "\n");
                EnviarMensajesAtodos(comun.getMensajes());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void EnviarMensajesAtodos(String texto){
        int i;
    }


}
