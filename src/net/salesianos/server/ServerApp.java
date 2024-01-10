package net.salesianos.server;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;

import net.salesianos.shared.constants.Constants;
import net.salesianos.shared.models.Messages;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
        Messages messages = new Messages();
        //ArrayList<ObjectOutputStream> connectedObjOutputStream = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONEXION ESTABLECIDA");
            ObjectOutputStream clientObjectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientObjectOutputStream.writeObject(messages);
            DataInputStream dataInStream = new DataInputStream(clientSocket.getInputStream());
            String messageReceived = "";
            while (!messageReceived.equals("bye")) {
                messageReceived = dataInStream.readUTF();
                messages.addMessage(messageReceived);
                //System.out.println("El mensaje recibido fue: " + messageReceived);
            }

            System.out.println("CERRANDO CONEXIÓN!");

        }

        // serverSocket.close();
    }
}
