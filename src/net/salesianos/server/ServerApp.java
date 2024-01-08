package net.salesianos.server;

import java.io.DataInputStream;
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

            DataInputStream dataInStream = new DataInputStream(clientSocket.getInputStream());
            String messageReceived = "";
            while (!messageReceived.equals("bye")) {
                messageReceived = dataInStream.readUTF();
                System.out.println("El mensaje recibido fue: " + messageReceived);
            }

            System.out.println("CERRANDO CONEXIÓN!");

        }

        // serverSocket.close();
    }
}
