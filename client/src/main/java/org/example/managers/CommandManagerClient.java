package org.example.managers;


import org.example.commands.*;

import java.util.LinkedHashMap;

import static org.example.tools.Printer.print;

public class CommandManagerClient {
    private static LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    public CommandManagerClient(){
        commands.put("remove_by_id", new RemoveByIdCommand("remove_by_id"));
        commands.put("add", new AddCommand("add"));
        commands.put("update", new UpdateCommand("update"));
        commands.put("exit", new ExitCommand("exit"));
        commands.put("add_if_max", new AddIfMaxCommand("add_if_max"));
        commands.put("execute_script", new ExecuteScriptCommand("execute_script"));
        commands.put("count_less_than_impact_speed", new CountLessThanImpactSpeedCommand("count_less_than_impact_speed"));
    }
    public LinkedHashMap<String, Command> getCommands(){return commands;}

    public void execute(String name){
        try {
            commands.get(name).execute();
        }catch (NullPointerException e){print("Данной команды не найдено");}
    }
    public void execute(String name, String arg){
        try {
            commands.get(name).execute(arg);
        }catch (NullPointerException e){print("Данной команды не найдено");}
    }
}
