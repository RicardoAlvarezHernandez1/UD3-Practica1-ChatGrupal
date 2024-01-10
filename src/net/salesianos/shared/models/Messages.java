package net.salesianos.shared.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Messages implements Serializable{
    private ArrayList<String> allMessages = new ArrayList<>();

    public synchronized void showAllMessages(){
        for (String message : allMessages) {
            System.out.println( "-->" + message);
        }

    }

    public synchronized void addMessage( String message) {
        allMessages.add(message);
    }

    public ArrayList<String> getAllMessages() {
        return allMessages;
    }

}
