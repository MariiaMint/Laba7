package org.example.managers;

import org.example.beginningClasses.HumanBeing;
import org.example.comands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;

import static org.example.managers.serverTools.ResponseSenderServer.send;

public class CommandManager {
    private static LinkedHashMap<String, Command> commands;

    public CommandManager(CommandExecutor executor) {
        this.commands = new LinkedHashMap<>();
        commands.put("add", new AddCommand(executor, "добавить новый элемент в коллекцию", "add"));
        commands.put("help", new HelpCommand(executor, "вывести справку по доступным командам", "help"));
        //commands.put("exit",new ExitCommand(executor,"завершить программу (без сохранения в файл)", "exit"));
        //commands.put("save", new SaveCommand(executor, "сохранить коллекцию в файл","save"));
        commands.put("show", new ShowCommand(executor,"вывести в стандартный поток вывода все элементы коллекции в строковом представлении","show"));
        commands.put("clear", new ClearCommand(executor,"очистить коллекцию","clear"));
        commands.put("remove_first", new RemoveFirstCommand(executor,"удалить первый элемент из коллекции","remove_first"));
        commands.put("count_less_than_impact_speed", new CountLessThanImpactSpeedCommand(executor,"вывести количество элементов, значение поля impactSpeed которых меньше заданного", "count_less_than_impact_speed impactSpeed"));
        commands.put("print_field_descending_mood", new PrintFieldDescendingMoodCommand(executor, "вывести значения поля mood всех элементов в порядке убывания", "print_field_descending_mood"));
        commands.put("remove_by_id", new RemoveByIdCommand(executor, " удалить элемент из коллекции по его id","remove_by_id id"));
        commands.put("update", new UpdateCommand(executor,"обновить значение элемента коллекции, id которого равен заданному","update id"));
        commands.put("info", new InfoCommand(executor, "вывести информацию о коллекции", "info"));
        commands.put("print_descending", new PrintDescendingCommand(executor,"вывести элементы коллекции в порядке убывания","print_descending"));
        commands.put("add_if_max", new AddIfMaxCommand(executor,"добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции","add_if_max"));
        commands.put("execute_script", new ExecuteScriptCommand(executor,"считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме","execute_script"));
        commands.put("sort", new SortCommand(executor,"отсортировать коллекцию в естественном порядке","sort"));
    }
    public void execute(String name, Object par, Integer UID, Socket socket){
        try {
            if (par instanceof String) {
                String response = commands.get(name).execute((String) par, UID);
                if (response != null) {
                    send(response, UID, socket);
                }
            }
            else if (par instanceof HumanBeing){
                String response = commands.get(name).execute((HumanBeing) par, UID);
                if (response != null) {
                    send(response, UID, socket);
                }
            }
        } catch (IOException | NullPointerException e) {
            send("Данная команда не найдена", UID, socket);
        }
    }

    public static LinkedHashMap<String, Command> getCommands() {
        return commands;
    }
}
