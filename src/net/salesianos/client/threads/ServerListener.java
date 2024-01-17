package net.salesianos.client.threads;

import java.io.DataInputStream;
import java.io.IOException;

import net.salesianos.shared.utils.Utils;

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
      System.out.println("\u001B[32m[" + Utils.getCurrentTime() + "] " +"Has salido del chat <:( "+ "\u001B[0m");
    }
  }
}