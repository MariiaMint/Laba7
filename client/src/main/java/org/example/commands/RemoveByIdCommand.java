package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class RemoveByIdCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public RemoveByIdCommand(String name){this.name = name;};
    public void execute(String arg){executor.remove_by_id(arg);};

    public void execute(){
        executor.remove_by_id(null);
    }
}
