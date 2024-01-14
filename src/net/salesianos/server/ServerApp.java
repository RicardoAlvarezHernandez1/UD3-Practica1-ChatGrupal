package net.salesianos.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import net.salesianos.server.threads.ClientHandler;
import net.salesianos.shared.constants.Constants;
import net.salesianos.shared.models.Messages;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
        Messages messages = new Messages();
        ArrayList<DataOutputStream> connectedDataOutputStreamList = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONEXION ESTABLECIDA");
            System.out.println(messages.getAllMessages().size());

            ObjectOutputStream clientObjectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientObjectOutputStream.writeObject(messages);
            
            DataOutputStream clientDataOutStream = new DataOutputStream(clientSocket.getOutputStream());
            connectedDataOutputStreamList.add(clientDataOutStream);
            DataInputStream dataInStream = new DataInputStream(clientSocket.getInputStream());

            ClientHandler clientHandler = new ClientHandler(clientSocket , dataInStream, clientDataOutStream, connectedDataOutputStreamList, messages);
            clientHandler.start();
        }

        // serverSocket.close();
    }
}
