//package org.example.comands;
//
//import org.example.managers.CommandExecutor;
//
//public class ExitCommand implements Command{
//    CommandExecutor commandExecutor;
//    String description;
//    String name;
//    public ExitCommand(CommandExecutor commandExecutor, String description, String name) {
//        this.commandExecutor = commandExecutor;
//        this.description = description;
//        this.name = name;
//    }
//
//    @Override
//    public void execute(String par, Integer UID) {
//        commandExecutor.exit();
//    }
//    public String description(){
//        return(name + ": " + description);
//    }
//}
