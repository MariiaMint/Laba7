package org.example;

import org.example.DBTools.DataBaseConnection;
import org.example.beginningClasses.HumanBeing;
import org.example.managers.CommandExecutor;
import org.example.managers.CommandManager;
import org.example.managers.serverTools.ClientHandler;
import org.example.managers.serverTools.Pools;
import org.example.managers.serverTools.RequestReaderServer;
import org.example.managers.serverTools.ServerConnect;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import static org.example.DBTools.HumanBeingInstructions.getAllHumans;
import static org.example.managers.serverTools.ServerConnect.close;
import static org.example.managers.serverTools.ServerConnect.connect;
import static org.example.tools.FileScanner.scan;
import static org.example.tools.Printer.print;

/**
 * Главный класс сервера
 */
public class ServerMain {
    /**
     * Главный метод, который запускает сервер.
     *
     * @param args Аргументы командной строки не используются
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        Vector<HumanBeing> collection = new Vector<>();

        BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>();
        ExecutorService requestProcessingPool = Pools.getRequestProcessingPool();

        String url = args[0];
        String filePath = args[1];
        //String url = "jdbc:postgresql://localhost:5432/studs";      // локаль
        // String url = "jdbc:postgresql://pg:5432/studs";           // хелиос
        ArrayList<String> loginAndPass = scan(filePath);
        try {
            DataBaseConnection connectionDB = new DataBaseConnection(url, loginAndPass.get(0), loginAndPass.get(1));
            connectionDB.connectDB();
            collection.addAll(getAllHumans());
        } catch (ArrayIndexOutOfBoundsException e) {
            print("в файле с логином и паролем есть ошибка");
            System.exit(-1);
        }

        connect();


        while (true) {
            try {
                Socket socket = ServerConnect.serverSocket.accept();
                print("Подключился клиент: " + socket);
                CommandExecutor commandExecutor = new CommandExecutor(collection, socket);
                CommandManager commandManager = new CommandManager(commandExecutor);
                Thread clientThread = new Thread(() -> {
                    while (!socket.isClosed()) {
                        try {
                            Message recievedCommand = RequestReaderServer.read(socket);
                            print(recievedCommand.getMessageName());
                            requestProcessingPool.execute(new ClientHandler(socket, commandManager, recievedCommand));
                        } catch (RuntimeException e) {
                            print("клиент отключился");
                            close(socket);
                        }
                    }
                });
                // Запускаем поток для чтения запросов
                clientThread.start();
            } catch (IOException e) {
                print("client missed");
            }
        }
    }
}