package org.example.comands;
import org.example.managers.CommandExecutor;

public class ExecuteScriptCommand implements Command{
    CommandExecutor commandExecutor;
    String description;
    String name;

    public ExecuteScriptCommand(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    @Override
    public String execute(String par, Integer UID) {
        commandExecutor.execute_script();
        return "no script";
    }
    public String description(){
        return(name + ": " + description);
    }
}
