package org.example.commands;

import org.example.managers.CommandExecutorClient;
import org.example.beginningClasses.HumanBeing;

public class UpdateCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public UpdateCommand(String name){this.name = name;};
    public void execute(String arg){executor.update(arg);};
    public void execute(HumanBeing arg){executor.updater(arg);};

    public void execute(){
        executor.update("");
    }
}
