package org.example;

public class MessWithArg extends Message {
    String arg;

    public MessWithArg(String messageName, String arg, Integer UID) {
        super(messageName, UID);
        this.arg = arg;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getArg() {
        return arg;
    }

}
