package net.salesianos.server;

import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(55000);
        //ArrayList<ObjectOutputStream> connectedObjOutputStream = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONEXION ESTABLECIDA");
        }

        // serverSocket.close();
    }
}
