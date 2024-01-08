package net.salesianos.client;

import java.io.DataOutputStream;
//import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 55000);
        String userInput = "";
        final String LOGGOUT_OPTION = "bye";
        final Scanner SCANNER = new Scanner(System.in);

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


