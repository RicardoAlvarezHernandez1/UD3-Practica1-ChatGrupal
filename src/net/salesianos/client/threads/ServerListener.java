package net.salesianos.client.threads;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener extends Thread {

  private DataInputStream objInStream;

  public ServerListener(DataInputStream socketObjectInputStream) {
    this.objInStream = socketObjectInputStream;
  }

  @Override
  public void run() {
    try {
      while (true) {
        String messageReceived = this.objInStream.readUTF();
        System.out.println(messageReceived);
      }

    } catch (IOException e2) {
      System.out.println("Has salido del chat <:( ");
    }
  }
}