package com.example.actividad3_9;
import java.io.*;
import java.net.*;

/*Servidor que espera conexiones y recibe una cadena, la envia en mayusculas, creando un hilo manualmente para cada conexion*/

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(44444);  // Servidor escucha en el puerto 44444
            System.out.println("Servidor iniciado...");

            while (true) {
                Socket cliente = servidor.accept();  // Esperar conexión de cliente
                InetAddress clienteIP = cliente.getInetAddress();
                int clientePuerto = cliente.getPort();
                System.out.println("=>Conecta IP " + clienteIP.getHostAddress() + ", Puerto remoto: " + clientePuerto);

                // Crear un hilo para manejar al cliente
                Thread hiloCliente = new Thread(new HiloCliente(cliente));
                hiloCliente.start();  // Iniciar el hilo para este cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HiloCliente implements Runnable {
    private Socket clienteSocket;

    public HiloCliente(Socket socket) {
        this.clienteSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.equals("*")) {  // Si el mensaje es "*", desconectar
                    InetAddress clienteIP = clienteSocket.getInetAddress();
                    int clientePuerto = clienteSocket.getPort();
                    System.out.println("=>Desconecta IP " + clienteIP.getHostAddress() + ", Puerto remoto: " + clientePuerto);
                    clienteSocket.close();
                    break;
                } else {
                    // Enviar el mensaje en mayúsculas
                    salida.println(mensaje.toUpperCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


