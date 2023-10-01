package org.example.comands;

import org.example.beginningClasses.HumanBeing;
import org.example.managers.CommandExecutor;

public class UpdateCommand implements Command{
    CommandExecutor commandExecutor;
    String description;
    String name;

    public UpdateCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    @Override
    public String execute(String par, Integer UID) {
        return commandExecutor.update(par, UID);
    }
    public String execute(HumanBeing par, Integer UID) {
        return commandExecutor.updater(par, UID);
    }
    public String description(){
        return(name + ": " + description);
    }
}
