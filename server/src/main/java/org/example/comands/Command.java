package org.example.comands;

import org.example.beginningClasses.HumanBeing;

import java.io.IOException;

public interface Command {
    default String execute(String par, Integer UID) throws IOException{
        return null;
    }
    default String execute(HumanBeing human, Integer UID) throws IOException{
        return null;
    }

    default void execute() throws IOException{}
    String description();
}
