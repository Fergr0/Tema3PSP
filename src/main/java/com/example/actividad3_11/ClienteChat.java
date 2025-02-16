package com.example.actividad3_11;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;
    Socket socket = null;

    // Streams para entrada y salida de datos
    DataInputStream fentrada;
    DataOutputStream fsalida;

    String nombre; // Nombre del usuario
    static JTextField mensaje = new JTextField();

    private JScrollPane scrollpanel; // Para manejar el desplazamiento del área de texto
    static JTextArea textareal; // Área de texto donde se muestran los mensajes
    JButton botonEnviar = new JButton("Enviar"); // Botón para enviar mensajes
    JButton botonSalir = new JButton("Salir"); // Botón para salir del chat
    boolean repetir = true; // Controla el ciclo de recepción de mensajes

    // Constructor
    public ClienteChat(Socket s, String nombre) {
        super(" CONEXIÓN DEL CLIENTE CHAT: " + nombre); // Título de la ventana
        setLayout(null); // Configuración del layout manual

        // Configuración de los componentes de la interfaz
        mensaje.setBounds(10, 10, 400, 30);
        add(mensaje);
        textareal = new JTextArea();
        scrollpanel = new JScrollPane(textareal);
        scrollpanel.setBounds(10, 50, 400, 300);
        add(scrollpanel);
        botonEnviar.setBounds(420, 10, 100, 30);
        add(botonEnviar);
        botonSalir.setBounds(420, 50, 100, 30);
        add(botonSalir);
        textareal.setEditable(false); // El text area pongo que no es editable

        // Configuro los eventos de los botones
        botonEnviar.addActionListener(this);
        botonSalir.addActionListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cerrar directamente la ventana

        // Inicializo el socket y los streams
        socket = s;
        this.nombre = nombre;
        try {
            fentrada = new DataInputStream(socket.getInputStream());
            fsalida = new DataOutputStream(socket.getOutputStream());

            // Envío un mensaje inicial informando que el usuario ha entrado al chat
            String texto = " > Entra en el Chat " + nombre;
            fsalida.writeUTF(texto);
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
            System.exit(0); // Salgo si ocurre un error
        }
    }

    // Manejo de eventos de los botones
    public void actionPerformed(ActionEvent e) {
        String texto = "";

        // Si el botón "Enviar" es presionado
        if (e.getSource() == botonEnviar) {
            if (mensaje.getText().trim().length() == 0) { // Verifico si el mensaje está vacío
                return;
            }
            texto = nombre + " > " + mensaje.getText(); // Formateo el mensaje
            try {
                mensaje.setText(""); // Limpio el campo de texto
                fsalida.writeUTF(texto); // Envío el mensaje al servidor
            } catch (IOException el) {
                el.printStackTrace();
            }
        }

        // Si el botón "Salir" es presionado
        if (e.getSource() == botonSalir) {
            texto = " > Abandona el Chat: " + nombre; // Mensaje de salida
            try {
                fsalida.writeUTF(texto); // Informo al servidor que salgo
                fsalida.writeUTF("*"); // Envío un carácter especial para cerrar conexión
                repetir = false; // Termino el ciclo de recepción de mensajes
            } catch (IOException el) {
                el.printStackTrace();
            }
        }
    }

    // Método para escuchar mensajes del servidor
    public void run() {
        String texto = "";
        while (repetir) {
            try {
                texto = fentrada.readUTF(); // Recibo un mensaje del servidor
                textareal.setText(texto); // Lo muestro en el área de texto
            } catch (IOException e) {
                // En caso de error, muestro un mensaje al usuario y termino el bucle
                JOptionPane.showMessageDialog(null,
                        "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" +
                                e.getMessage(), "<<MENSAJE DE ERROR:2>>",
                        JOptionPane.ERROR_MESSAGE);
                repetir = false;
            }
        }
        try {
            socket.close(); // Cierro el socket
            System.exit(0); // Salgo del programa
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal
    public static void main(String args[]) {
        int puerto = 44444; // Puerto de conexión
        Socket s = null; // Socket del cliente
        String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:"); // Pido el nombre del usuario
        if (nombre.trim().length() == 0) { // Verifico que el nombre no esté vacío
            System.out.println("El nombre está vacío....");
            return;
        }
        try {
            s = new Socket("172.16.3.107", puerto); // Intento conectar al servidor

            // Creo la ventana del cliente y lanzo su hilo
            ClienteChat cliente = new ClienteChat(s, nombre);
            cliente.setBounds(0, 0, 540, 400);
            cliente.setVisible(true);
            new Thread(cliente).start(); // Inicio el hilo del cliente
        } catch (IOException e) {
            // Muestro un mensaje de error si no puedo conectar al servidor
            JOptionPane.showMessageDialog(null,
                    "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" +
                            e.getMessage(), "<<MENSAJE DE ERROR:1>>",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
