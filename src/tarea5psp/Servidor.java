package tarea5psp;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Servidor extends Thread {

    static int stockOrdenadores;
    int puerto;
    static boolean apagado;

    public Servidor(int stockOrdenadores, int puerto) {
        this.stockOrdenadores = stockOrdenadores;
        this.puerto = puerto;
        this.apagado = false;
    }

    public void run() {
        try {
            // Creamos un objeto de tipo SSLServersocketFactory pasra arrancar un servidor
            SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(puerto);
            while (!apagado) {
                // Espera la conexión hasta que un cliente se conecta (siempre está escuchando)
                SSLSocket socketCliente = (SSLSocket)serverSocket.accept();
                ConexionCliente conexionCliente = new ConexionCliente(socketCliente);
                conexionCliente.start();
                System.out.println("CONEXION CON EL CLIENTE");
            }
            serverSocket.close();

        } catch (Exception exception) {
            
        }

    }

    public static void aumentarStock(Integer ordenadoresAñadidos) {
        stockOrdenadores = stockOrdenadores + ordenadoresAñadidos;
    }

    public static void disminuirStock(Integer ordenadoresDisminuidos) {
        stockOrdenadores = stockOrdenadores - ordenadoresDisminuidos;
    }

    public static Integer consultarStock() {
        return stockOrdenadores;

    }

    public static void apagar() {
        apagado = true;
    }
    
   
    

}
