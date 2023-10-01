package org.example.managers.serverTools;

import org.example.MessWithArg;
import org.example.MessWithHuman;
import org.example.MessWithUser;
import org.example.Message;
import org.example.beginningClasses.HumanBeing;
import org.example.managers.CommandManager;

import java.net.Socket;

import static org.example.managers.serverTools.ServerConnect.close;
import static org.example.tools.Printer.print;

public class ClientHandler implements Runnable {
    private Integer clientUID;
    private final Socket socket;
    private final CommandManager commandManager;

    //private final ExecutorService requestProcessingPool;

    //private final ExecutorService responseSenderPool;
    //private final BlockingQueue<Runnable> requestQueue;
    private final Message receivedCommand;
    public ClientHandler(Socket socket, CommandManager commandManager, Message receivedCommand) {
        this.socket = socket;
        this.commandManager = commandManager;
        this.receivedCommand = receivedCommand;

    }

    @Override
    public void run() {
        try {
//            String threadInfo = "Thread info: " + Thread.currentThread().getName();
//            System.out.println(threadInfo);

            print("Обработка запроса: " + receivedCommand.getMessageName());
            Integer UID = receivedCommand.getUID();
            String argument = null;
            HumanBeing human = null;

            if (receivedCommand != null) {
                String commandName = receivedCommand.getMessageName();
                if (receivedCommand instanceof MessWithArg) {
                    argument = ((MessWithArg) receivedCommand).getArg();
                }
                if (receivedCommand instanceof MessWithHuman) {
                    human = ((MessWithHuman) receivedCommand).getHuman();
                }
                if (receivedCommand instanceof MessWithUser) {
                    EntryHandler entryHandler = new EntryHandler();
                    entryHandler.defineExistUser(((MessWithUser) receivedCommand).getUser(), socket);
                    clientUID = UID;
                }
                else if (argument == null && human == null) {
                    commandManager.execute(commandName, "", UID, socket);
                }
                if (argument != null) {
                    commandManager.execute(commandName, argument, UID, socket);
                }
                if (human != null) {
                    commandManager.execute(commandName, human, UID, socket);
                }
            }
            // return null;
            // }
            //});

            // Ждем завершения обработки запроса и отправляем ответ в тот же поток
            //future.get();
        } catch (RuntimeException e) {
            print("клиент отключился");
            close(socket);
            //} catch (InterruptedException | ExecutionException e) {
            // throw new RuntimeException(e);
        }
    }
}