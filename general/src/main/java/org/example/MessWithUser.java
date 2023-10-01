package org.example;

public class MessWithUser extends Message{
    User user;

    public MessWithUser(String messageName, User user, Integer UID) {
        super(messageName, UID);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
