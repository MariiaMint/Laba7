package org.example.tools;

import org.example.*;
import org.example.beginningClasses.HumanBeing;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.example.tools.ClientConnect.clientSocket;

public class ResponseSenderClient {
    public static void send(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
//            print("сервер не доступен");
        }
    }
    public static void sendMessage(String command, Integer UID){
        Message message = new Message(command, UID);
        send(message);
    }
    public static void sendMessWithArg(String command, String argument, Integer UID){
        MessWithArg message = new MessWithArg(command, argument, UID);
        send(message);
    }
    public static void sendMessWithHuman(String command, HumanBeing human, Integer UID){
        MessWithHuman message = new MessWithHuman(command, human, UID);
        send(message);
    }

    public static void sendMessWithUser(User user, Integer UID){
        MessWithUser messWithUser = new MessWithUser("user", user, UID);
        send(messWithUser);
    }
}
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            byte[] data = response.getBytes();
//            outputStream.write(data);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static void send(String response, String arg) {
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            byte[] data = response.getBytes();
//            outputStream.write(data);
//            outputStream.flush();
//            byte[] data2 = arg.getBytes();
//            outputStream.write(data2);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void sendObject(byte[] response) {
//        try {
//            OutputStream outputStream = ClientConnect.getOutputStream();
//            outputStream.write(response);
//            outputStream.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
