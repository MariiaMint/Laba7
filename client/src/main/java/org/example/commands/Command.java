package org.example.commands;

import java.io.Serializable;

public interface Command extends Serializable {
    public void execute(String arg);
    public void execute();
}
