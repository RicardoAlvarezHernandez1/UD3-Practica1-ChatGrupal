package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import net.salesianos.shared.models.Messages;
import net.salesianos.shared.utils.Utils;

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

        String messageReceived = (String) this.clientDataInStream.readUTF();
        messageFormated = Utils.formatMessage(messageReceived, username);

        if (!messageFormated.equals("")) messages.addMessage(messageFormated);
        
       Utils.sendMessagesToClients(messageReceived, messageFormated, username, connectedDataOutputStreamList, clientDataOutStream);
      }

    } catch (IOException e) {
      this.connectedDataOutputStreamList.remove(this.clientDataOutStream);
      System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
    }

  }
}
