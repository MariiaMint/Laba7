package org.example.managers.serverTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnect {
    public static ServerSocket serverSocket;
    //public static Socket socket;

    public static void connect() {
        try {
            serverSocket = new ServerSocket(9991);
            System.out.println("Сервер запущен и ожидает подключений");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть соединение");
        }
    }
}

