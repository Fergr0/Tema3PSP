package com.example.actividad3_1;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*Esta clase coge el nombre de un servidor por linea de comandos y muestra su info haciendo uso de los
* metodos de la clase InetAddress*/

public class main3_1 {
    public static void main(String[] args) {
        // Verificar que el usuario ha proporcionado un argumento
        if (args.length != 1) {
            System.out.println("Agrega parámetros porfa");
            return;
        }

        String host = args[0]; //Cojo el parametro de entrada y se lo meto a la variable host

        try {
            // Obtener el objeto InetAddress
            InetAddress address = InetAddress.getByName(host);

            // Mostrar información
            System.out.println("Información sobre: " + host);
            System.out.println("Nombre: " + address.getHostName());
            System.out.println("Nombre canónico: " + address.getCanonicalHostName()); //Direccion real del host
            System.out.println("Dirección IP: " + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("No se pudo encontrar la dirección para el host proporcionado.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
