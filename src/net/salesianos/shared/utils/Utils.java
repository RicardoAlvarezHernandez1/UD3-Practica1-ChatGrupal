package net.salesianos.shared.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Utils {

    public static String formatMessage(String messageReceived, String username){
        String messageFormated = "";
        if (messageReceived.startsWith("msg:")) {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String time = dateTime.format(formatter);
            messageFormated = "\u001B[36m[" + time + "]" + " <" + username + "> : " + messageReceived.substring(4).trim() + "\u001B[0m";
        }
        return messageFormated;
    }

    public static void sendMessagesToClients(String messageReceived, String messageFormated, String username, ArrayList<DataOutputStream> connectedDataOutputStreamList, DataOutputStream clientDataOutStream) throws IOException{
        if (!messageReceived.startsWith("msg:")){
            clientDataOutStream.writeUTF("\u001B[33mError al enviar el mensaje, vuelva a escrbirlo usando el comando msg: o para salir del chat con el comando bye\u001B[0m");
          }
  
  
          for (DataOutputStream otherDataOutputStream : connectedDataOutputStreamList) {
            
            if (messageReceived.equals("bye")) {
              otherDataOutputStream.writeUTF( username + " se fue del chat :(");
            }else if (messageReceived.startsWith("msg:")){
              otherDataOutputStream.writeUTF(messageFormated);
            }
  
          }
    }
    
}