package org.example;

import org.example.commands.Command;
import org.example.managers.CommandManagerClient;
import org.example.tools.ReadConsole;
import org.example.tools.RequestReaderClient;
import org.example.tools.ResponseSenderClient;

import java.util.LinkedHashMap;

import static org.example.tools.ClientConnect.connect;
import static org.example.tools.EntryHandler.authenticity;
import static org.example.tools.EntryHandler.getUID;
import static org.example.tools.Inputer.scanner;
import static org.example.tools.Printer.print;

/**
 * Главный класс клиента.
 */
public class ClientMain {
    /**
     * Главный метод, который запускает клиентское приложение.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CommandManagerClient commandManager = new CommandManagerClient();
        LinkedHashMap<String, Command> commands = commandManager.getCommands();
        try {
            connect();
            authenticity();
            int userId = getUID();

            do {
                String command = ReadConsole.read(scanner, "Введите команду (чтобы увидеть справку по командам введите help)");
                String[] command_list;

                if (command != null) {
                    command_list = command.split(" ");

                    if (commands.containsKey(command_list[0])) {
                        if (command_list.length == 1) {
                            commandManager.execute(command_list[0]);
                        } else if (command_list.length == 2) {
                            commandManager.execute(command_list[0], command_list[1]);

                        }
                    } else {
                        if (command_list.length == 1) {
                            ResponseSenderClient.sendMessage(command, userId);
                        } else if (command_list.length == 2) {
                            ResponseSenderClient.sendMessWithArg(command_list[0], command_list[1], userId);
                        }
                        Message message = RequestReaderClient.serverRead();
                        print(message.getMessageName());
                    }
                }

            } while (true);
        } catch (RuntimeException e) {
            print("попробуйте позже");
            System.exit(0);
        }
    }
}

