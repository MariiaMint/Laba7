package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class ExitCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public ExitCommand(String name){this.name = name;};
    public void execute(String arg){throw new NullPointerException();};

    public void execute(){
        executor.exit();
    }
}
