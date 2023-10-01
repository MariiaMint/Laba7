package org.example.managers.serverTools;

import org.example.MessWithHuman;
import org.example.Message;
import org.example.beginningClasses.HumanBeing;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.example.tools.Printer.print;

public class ResponseSenderServer {

    public static void send(String response, Integer UID, Socket socket) {
        Pools.getResponseSenderPool().execute(() -> {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message resp = new Message(response, UID);
                oos.writeObject(resp);
                oos.flush();

                print("response was sent");
            } catch (IOException e) {
                print("ioex responseSender");
            }
        });
    }

    public static void sendHuman(HumanBeing human, Integer UID, Socket socket) {
        Pools.getResponseSenderPool().execute(() -> {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                MessWithHuman resp = new MessWithHuman("updater", human, UID);
                oos.writeObject(resp);
                oos.flush();

                print("human was sent" + human.getId());
            } catch (IOException e) {
                print("ioex responseSender");
            }
        });
    }

}

