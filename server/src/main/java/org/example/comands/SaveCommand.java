//package org.example.comands;
//
//import org.example.managers.CommandExecutor;
//
//public class SaveCommand implements Command{
//    CommandExecutor commandExecutor;
//    String description;
//    String name;
//
//    public SaveCommand(CommandExecutor commandExecutor, String description, String name) {
//        this.commandExecutor = commandExecutor;
//        this.description = description;
//        this.name = name;
//    }
//
//    @Override
//    public void execute(String par) {
//        commandExecutor.save();
//    }
//    public String description(){
//        return(name + ": " + description);
//    }
//}
