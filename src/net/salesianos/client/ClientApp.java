package net.salesianos.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
//import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.shared.constants.Constants;
import net.salesianos.shared.models.Messages;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", Constants.SERVER_PORT);
        ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
        DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInStream = new DataInputStream(socket.getInputStream());
        Messages messages = (Messages)serverInputStream.readObject();

        String userInput = "";
        final String LOGGOUT_OPTION = "bye";
        final Scanner SCANNER = new Scanner(System.in);
        // Pregunta el nombre
        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();
        dataOutStream.writeUTF(username);

        // Mostrar todos los mensajes guardados
        System.out.println("HAS ENTRADO EN NUESTRO SUPERCHAT, envia todos los mensajes que quieras (si quieres salirte, escribe 'bye'):\n");
        if (messages.getAllMessages().size() != 0){
            messages.showAllMessages();
        }

        // Creamos y ejecutamos el hilo
        ServerListener serverListener = new ServerListener(dataInStream);
        serverListener.start();

        while (!userInput.equals(LOGGOUT_OPTION)) {
            userInput = SCANNER.nextLine();
            dataOutStream.writeUTF(userInput);
        }
        
        SCANNER.close();
        socket.close();
    }
}


