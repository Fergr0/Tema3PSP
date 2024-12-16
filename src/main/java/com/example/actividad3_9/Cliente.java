package com.example.actividad3_9;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/*Cliente que se conecta a un servidor y le envia una cadena. La recibe en mayusculas, y puede finalizar los porcesos con un boton*/

public class Cliente {
    private static Socket socket;
    private static PrintWriter salida;
    private static BufferedReader entrada;
    private static JTextArea areaRespuesta;
    private static JTextField entradaTexto;

    public static void main(String[] args) {
        // Conexión al servidor
        try {
            socket = new Socket("127.0.0.1", 44444);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear la interfaz gráfica
        JFrame ventana = new JFrame("Ventana del Cliente - Ejercicio 5");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        JLabel etiqueta = new JLabel("Escribe texto:");
        entradaTexto = new JTextField(20);

        JButton btnEnviar = new JButton("Enviar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnSalir = new JButton("Salir");

        areaRespuesta = new JTextArea(5, 20);
        areaRespuesta.setEditable(false);

        ventana.add(etiqueta);
        ventana.add(entradaTexto);
        ventana.add(btnEnviar);
        ventana.add(btnLimpiar);
        ventana.add(btnSalir);
        ventana.add(new JScrollPane(areaRespuesta));

        ventana.setSize(300, 300);
        ventana.setVisible(true);

        // Acción de enviar
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = entradaTexto.getText();
                if (mensaje.equals("*")) {
                    salida.println(mensaje);
                    cerrarVentana();
                } else {
                    salida.println(mensaje);
                    try {
                        String respuesta = entrada.readLine();
                        areaRespuesta.append(respuesta + "\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Acción de limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entradaTexto.setText("");
                areaRespuesta.setText("");
            }
        });

        // Acción de salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salida.println("*");
                cerrarVentana();
            }
        });
    }

    // Cerrar la ventana y cerrar el socket
    private static void cerrarVentana() {
        try {
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

