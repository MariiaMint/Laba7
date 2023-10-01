package org.example.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static org.example.tools.Printer.print;

public class ClientConnect {
    static Socket clientSocket;
    static InetAddress serverAddress;
    static int serverPort;

    public static void connect() {
        try {
            serverAddress = InetAddress.getByName("localhost");
            serverPort = 9991;
            clientSocket = new Socket(serverAddress, serverPort);
            print("Connection accepted");
        } catch (IOException e) {
            System.out.println("Не удалось установить соединение с сервером");
            throw new RuntimeException();
        }
    }

    public static void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть соединение");
        }
    }

    public static OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }
}
