package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import net.salesianos.shared.models.Messages;

public class ClientHandler extends Thread{
  private DataInputStream clientDataInStream;
  private DataOutputStream clientDataOutStream;
  private ArrayList<DataOutputStream> connectedDataOutputStreamList;
  private Messages messages = new Messages();
  public ClientHandler(DataInputStream clientDataInStream, DataOutputStream clientDataOutStream,
      ArrayList<DataOutputStream> connectedDataOutputStreamList) {
    this.clientDataInStream = clientDataInStream;
    this.clientDataOutStream = clientDataOutStream;
    this.connectedDataOutputStreamList = connectedDataOutputStreamList;
  }

  @Override
  public void run() {
    String username = "";
    try {

      username = this.clientDataInStream.readUTF();

      while (true) {
        // Recibimos el mensaje y lo mostramos en el servidor
        String messageReceived = (String) this.clientDataInStream.readUTF();
        messages.addMessage(messageReceived);
        System.out.println(username + " envía: " + messageReceived);
        // Enviamos el mensaje a todos los demás clientes
        for (DataOutputStream otherDataOutputStream : connectedDataOutputStreamList) {
          otherDataOutputStream.writeUTF(messageReceived);
        }
      }

    } catch (EOFException eofException) {
      this.connectedDataOutputStreamList.remove(this.clientDataOutStream);
      System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
