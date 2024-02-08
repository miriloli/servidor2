package tarea5psp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLSocket;

class ConexionCliente extends Thread {

    SSLSocket cliente;
    DataInputStream in;
    DataOutputStream out;
    int stock;

    public ConexionCliente(SSLSocket cliente) throws IOException {
        this.cliente = cliente;
        System.out.println("Conexión establecida correctamente");
        this.stock = Servidor.stockOrdenadores;
    }

    public void run() {
        System.out.println("EMPIESA EL RUN");
        try {

            // canal entrada
            in = new DataInputStream(cliente.getInputStream());

            // canal de salida
            out = new DataOutputStream(cliente.getOutputStream());
            
            while (true) {
                // Recibimos la opción elegida por parte del cliente
                int opcionElegidaCliente = in.readInt();

               if (opcionElegidaCliente == 0) {

                    stock = Servidor.consultarStock();
                    out.writeInt(stock);
                    System.out.println("HE LLEGADO A CONSULTAR EL STOCK");

                } else if (opcionElegidaCliente < 0) {

                    Servidor.disminuirStock(opcionElegidaCliente);
                    out.writeInt(stock);
                    System.out.println("HE LLEGADO A DISMINUIR EL STOCK");

                } else {

                    Servidor.aumentarStock(opcionElegidaCliente);
                    out.writeInt(stock);
                    System.out.println("HE LLEGADO A AUMENTAR EL STOCK");
                }
            }

        } catch (Exception exception) {

            System.out.println(exception.getMessage());
        }

        System.out.println("Saliendo del hilo");
    }

}
