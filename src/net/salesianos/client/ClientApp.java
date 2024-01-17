package net.salesianos.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.shared.constants.Constants;
import net.salesianos.shared.models.Messages;
import net.salesianos.shared.utils.Utils;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", Constants.SERVER_PORT);
        ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
        DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInStream = new DataInputStream(socket.getInputStream());
        Messages messages = (Messages)serverInputStream.readObject();
        final Scanner SCANNER = new Scanner(System.in);
        
        Utils.askName(SCANNER, dataOutStream);

        System.out.println("\u001B[32mHAS ENTRADO EN NUESTRO SUPERCHAT, envia todos los mensajes que quieras (si quieres salirte, escribe 'bye'):\u001B[0m\n");
        if (messages.getAllMessages().size() != 0) messages.showAllMessages();

        ServerListener serverListener = new ServerListener(dataInStream);
        serverListener.start();

        Utils.sendClientMessageToServer(socket, SCANNER, dataOutStream);
        
        SCANNER.close();
        socket.close();
    }
}


