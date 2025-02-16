package com.example.actividad3_11;

import java.net.Socket;

public class ComunHilos {
    // Variables para controlar las conexiones y el almacenamiento de sockets
    int CONEXIONES;  // Número total de conexiones permitidas
    int ACTUALES;    // Número de conexiones actuales activas
    int MAXIMO;      // Número máximo de conexiones que se pueden tener
    Socket tabla[] = new Socket[MAXIMO]; // Array para almacenar los sockets de los clientes
    String mensajes; // Mensajes compartidos entre los hilos

    // Constructor que inicializa la clase con los parámetros proporcionados
    public ComunHilos(int maximo, int actuales, int conexiones, Socket[] tabla) {
        MAXIMO = maximo;
        ACTUALES = actuales;
        CONEXIONES = conexiones;
        this.tabla = tabla;
        mensajes = "";
    }

    // Constructor por defecto
    public ComunHilos() { super(); }

    // Getter para el valor máximo de conexiones
    public int getMAXIMO() { return MAXIMO; }

    // Setter para el valor máximo de conexiones
    public void setMAXIMO(int maximo) { MAXIMO = maximo; }

    // Getter para el número de conexiones
    public int getCONEXIONES() { return CONEXIONES; }

    // Setter sincronizado para el número de conexiones
    public synchronized void setCONEXIONES(int conexiones) {
        CONEXIONES = conexiones;
    }

    // Getter para los mensajes
    public String getMensajes() { return mensajes; }

    // Setter sincronizado para los mensajes
    public synchronized void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    // Getter para el número de conexiones actuales
    public int getACTUALES() { return ACTUALES; }

    // Setter sincronizado para el número de conexiones actuales
    public synchronized void setACTUALES(int actuales) {
        ACTUALES = actuales;
    }

    // Método sincronizado para agregar un socket al array
    public synchronized void addTabla(Socket s, int i) {
        tabla[i] = s;
    }

    // Getter para un socket específico en la tabla
    public Socket getElementoTabla(int i) { return tabla[i]; }
}
