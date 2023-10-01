package org.example;

import java.io.Serializable;

public class Message implements Serializable {
    String messageName;
    Integer UID;

    public Message(String commandName, Integer UID) {
        this.messageName = commandName;
        this.UID = UID;
    }

    public String getMessageName() {
        return messageName;
    }
    public Integer getUID(){return UID;}
}
