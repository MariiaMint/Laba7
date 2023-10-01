package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class AddIfMaxCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public AddIfMaxCommand(String name){this.name = name;};
    public void execute(String arg){ executor.add();};
    public void execute(){
        executor.add();
    };
}

