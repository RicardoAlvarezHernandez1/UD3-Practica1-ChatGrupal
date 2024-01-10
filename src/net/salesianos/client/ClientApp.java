package net.salesianos.client;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
//import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import net.salesianos.shared.constants.Constants;
import net.salesianos.shared.models.Messages;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", Constants.SERVER_PORT);
        ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
        Messages messages = (Messages)serverInputStream.readObject();
        String userInput = "";
        final String LOGGOUT_OPTION = "bye";
        final Scanner SCANNER = new Scanner(System.in);

        if (messages.getAllMessages().size() != 0){
            messages.showAllMessages();
        }
        DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());
        while (!userInput.equals(LOGGOUT_OPTION)) {
            System.out.println("Envía un mensaje  (escribe 'bye' pa salir): ");
            userInput = SCANNER.nextLine();
            dataOutStream.writeUTF(userInput);
        }

        SCANNER.close();
        socket.close();
    }
}


