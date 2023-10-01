package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class CountLessThanImpactSpeedCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public CountLessThanImpactSpeedCommand(String name){this.name = name;};
    public void execute(String arg){executor.countLessThanImpactSpeed(arg);};

    public void execute(){
        executor.countLessThanImpactSpeed("");
    }
}
