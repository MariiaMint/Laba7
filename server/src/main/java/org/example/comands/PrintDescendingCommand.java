package org.example.comands;

import org.example.managers.CommandExecutor;

public class PrintDescendingCommand implements Command{
    CommandExecutor commandExecutor;
    String description;
    String name;

    public PrintDescendingCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    @Override
    public String execute(String par, Integer UID) {
        return commandExecutor.printDescending(UID);
    }
    public String description(){
        return(name + ": " + description);
    }
}
