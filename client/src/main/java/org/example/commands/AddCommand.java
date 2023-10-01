package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class AddCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public AddCommand(String name){this.name = name;};
    public void execute(String arg){executor.add();};
    public void execute(){
        executor.add();
    };
}
