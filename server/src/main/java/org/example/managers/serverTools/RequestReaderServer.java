package org.example.managers.serverTools;

import org.example.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import static org.example.tools.Printer.print;

public class RequestReaderServer {
    public static Message read(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Message clientMessage = (Message) in.readObject();
            print("Получено сообщение от клиента: " + clientMessage.getMessageName());
            return clientMessage;
        } catch (IOException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
