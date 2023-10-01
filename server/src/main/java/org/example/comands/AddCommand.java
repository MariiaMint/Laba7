package org.example.comands;

import org.example.beginningClasses.HumanBeing;
import org.example.managers.CommandExecutor;

public class AddCommand implements  Command{
    CommandExecutor commandExecutor;
    String description;
    String name;
    public AddCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    public String execute(HumanBeing human, Integer UID) {
        return commandExecutor.add(human, UID);
    }
    public String description(){
        return(name + ": " + description);
    }
}
