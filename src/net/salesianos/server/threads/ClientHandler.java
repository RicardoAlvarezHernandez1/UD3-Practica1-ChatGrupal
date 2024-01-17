package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import net.salesianos.shared.models.Messages;

public class ClientHandler extends Thread{
  private DataInputStream clientDataInStream;
  private DataOutputStream clientDataOutStream;
  private ArrayList<DataOutputStream> connectedDataOutputStreamList;
  private Messages messages;

  public ClientHandler(DataInputStream clientDataInStream, DataOutputStream clientDataOutStream,
      ArrayList<DataOutputStream> connectedDataOutputStreamList, Messages messages) {
      this.clientDataInStream = clientDataInStream;
      this.clientDataOutStream = clientDataOutStream;
      this.connectedDataOutputStreamList = connectedDataOutputStreamList;
      this.messages = messages;
  }

  @Override
  public void run() {
    String username = "";
    String messageFormated = "";
    try {

      username = this.clientDataInStream.readUTF();


      while (true) {
        // Recibimos el mensaje y lo mostramos en el servidor
        String messageReceived = (String) this.clientDataInStream.readUTF();
        if (messageReceived.startsWith("msg:")) {
          LocalDateTime dateTime = LocalDateTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
          String time = dateTime.format(formatter);
          messageFormated = "\u001B[36m[" + time + "]" + " <" + username + "> : " + messageReceived.substring(4).trim() + "\u001B[0m";
          messages.addMessage(messageFormated);
        }
        

        // Enviamos el mensaje a todos los demás clientes
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

    } catch (EOFException eofException) {
      this.connectedDataOutputStreamList.remove(this.clientDataOutStream);
      System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
    } catch (IOException e) {
      
    }

  }
}
