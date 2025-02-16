package com.example.Actividad3_10;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Jugador {
    // Atributos para la conexión y la interfaz gráfica
    private Socket socket;
    private PrintWriter escritor;
    private BufferedReader lector;
    private JTextField campoNumero;
    private JTextArea areaTexto;
    private int idJugador = 2; // ID ficticio del jugador
    private int intentos = 0; // Contador de intentos

    public Jugador(String host, int puerto) {
        try {
            // me conecto al servidor
            socket = new Socket(host, puerto);
            escritor = new PrintWriter(socket.getOutputStream(), true);
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Creo la ventana principal
            JFrame frame = new JFrame("JUGADOR - ADIVINA UN NÚMERO");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            frame.add(panel);
            panel.setLayout(null);

            // etiqueta para mostrar el ID del jugador
            JLabel labelID = new JLabel("ID: " + idJugador);
            labelID.setBounds(10, 10, 150, 25);
            panel.add(labelID);

            // Etiqueta para mostrar el número de intentos
            JLabel labelIntentos = new JLabel("INTENTOS: " + intentos);
            labelIntentos.setBounds(10, 40, 150, 25);
            panel.add(labelIntentos);

            // Etiqueta para mostrar el rango de números
            JLabel labelRango = new JLabel("Número entre 1 y 25");
            labelRango.setBounds(10, 70, 150, 25);
            panel.add(labelRango);

            // etiqueta y campo para ingresar el número
            JLabel labelNumero = new JLabel("NÚMERO A ADIVINAR:");
            labelNumero.setBounds(10, 100, 150, 25);
            panel.add(labelNumero);

            campoNumero = new JTextField();
            campoNumero.setBounds(170, 100, 100, 25);
            panel.add(campoNumero);

            // Botón para enviar el número al servidor
            JButton btnEnviar = new JButton("Enviar");
            btnEnviar.setBounds(100, 130, 100, 30);
            panel.add(btnEnviar);

            // Botón para salir del juego
            JButton btnSalir = new JButton("Salir");
            btnSalir.setBounds(210, 130, 80, 30);
            panel.add(btnSalir);

            // Texxt area para mostrar los mensajes del servidor
            areaTexto = new JTextArea();
            areaTexto.setBounds(10, 170, 360, 100);
            areaTexto.setEditable(false);
            panel.add(areaTexto);

            // Acción cuando se pulsa el botón "Enviar"
            btnEnviar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String numeroStr = campoNumero.getText(); // Obtengo el número del campo de texto
                    if (!numeroStr.isEmpty()) {
                        try {
                            int numero = Integer.parseInt(numeroStr); // Intento convertirlo a entero
                            intentos++; // Incremento los intentos
                            escritor.println(numero); // Envío el número al servidor

                            String respuesta = lector.readLine(); // Leo la respuesta del servidor
                            areaTexto.append("Intento " + intentos + ": " + respuesta + "\n"); // La muestro en la interfaz

                            labelIntentos.setText("INTENTOS: " + intentos); // Actualizo la etiqueta de intentos
                            campoNumero.setText(""); // Limpio el campo de texto

                            // Si el número es correcto, muestro un mensaje y cierro el juego
                            if (respuesta.equals("¡Correcto!")) {
                                JOptionPane.showMessageDialog(frame, "¡Has adivinado el número!", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                                socket.close();
                                System.exit(0);
                            }
                        } catch (NumberFormatException ex) {
                            // Manejo si el usuario ingresa algo que no sea un número
                            JOptionPane.showMessageDialog(frame, "Introduce un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            // Acción cuando se ulsa el botón "Salir"
            btnSalir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        escritor.println("*"); // Envío un mensaje especial para finalizar
                        socket.close(); // Cierro el socket
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0); // Salgo de la aplicación
                }
            });

            frame.setVisible(true); // Muestro la ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Inicio el cliente
        new Jugador("127.0.0.1", 55555);
    }
}
