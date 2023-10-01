package org.example;

import org.example.beginningClasses.HumanBeing;

public class MessWithHuman extends Message {
    HumanBeing human;

    public MessWithHuman(String messageName, HumanBeing human, Integer UID) {
        super(messageName, UID);
        this.human = human;
    }

    public HumanBeing getHuman() {
        return human;
    }
}
