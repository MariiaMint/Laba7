package org.example.commands;

import org.example.managers.CommandExecutorClient;

public class ExecuteScriptCommand implements Command{
    String name;
    CommandExecutorClient executor = new CommandExecutorClient();
    public ExecuteScriptCommand(String name){this.name = name;};
    public void execute(String arg){executor.executeScript(arg);};
    public void execute(){throw new NullPointerException();}
}
