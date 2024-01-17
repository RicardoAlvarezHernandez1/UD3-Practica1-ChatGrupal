package net.salesianos.shared.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

  private static String userInput = "";
  public final static String LOGOUT_OPTION = "bye";


    public static String getCurrentTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = dateTime.format(formatter);
        return time;
    }

    public static String formatMessage(String messageReceived, String username){
        String messageFormated = "";
        if (messageReceived.startsWith("msg:")) {     
            messageFormated = "\u001B[36m[" + getCurrentTime() + "]" + " <" + username + "> : " + messageReceived.substring(4).trim() + "\u001B[0m";
        }
        return messageFormated;
    }

    public static void sendMessagesToClients(String messageReceived, String messageFormated, String username, ArrayList<DataOutputStream> connectedDataOutputStreamList, DataOutputStream clientDataOutStream) throws IOException{
        if (!messageReceived.startsWith("msg:")){
            clientDataOutStream.writeUTF("\u001B[33mError al enviar el mensaje, vuelva a escrbirlo usando el comando msg: o para salir del chat con el comando bye\u001B[0m");
          }
  
  
          for (DataOutputStream otherDataOutputStream : connectedDataOutputStreamList) {
            
            if (messageReceived.equals(LOGOUT_OPTION)) {
              otherDataOutputStream.writeUTF("\u001B[32m["+ getCurrentTime() + "] " + username + " se fue del chat :(\u001B[0m");
            }else if (messageReceived.startsWith("msg:")){
              otherDataOutputStream.writeUTF(messageFormated);
            }
  
          }
    }
    
    public static void askName(Scanner scanner , DataOutputStream dataOutStream) throws IOException{
        System.out.println("¿Cómo te llamas?");
        String username = scanner.nextLine();
        dataOutStream.writeUTF(username);
    }

    public static void sendClientMessageToServer(Socket socket , Scanner scanner , DataOutputStream dataOutStream) throws IOException{
      while (!userInput.equals(LOGOUT_OPTION) && !socket.isClosed()) {
        userInput = scanner.nextLine();
        dataOutStream.writeUTF(userInput);
      }
    }

}