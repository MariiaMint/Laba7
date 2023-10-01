package org.example.tools;

import org.example.Message;
import org.example.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.example.tools.Inputer.scanner;
import static org.example.tools.Printer.print;
import static org.example.tools.ReadConsole.getPswd;
import static org.example.tools.ReadConsole.read;
import static org.example.tools.RequestReaderClient.serverRead;
import static org.example.tools.ResponseSenderClient.sendMessWithUser;
import static org.example.tools.Validator.yesNo;

public class EntryHandler {
    private static int UID = -1;
    public static void authenticity(){

        while(UID < 0){
            print("У вас уже есть аккаунт? [yes/no]");
            if(yesNo(scanner)){
                UID=signIn();
            }else{UID=logIn();}
        }
    }
    public static User generateUser() {
        User user = new User();
        user.setName(read(scanner, "Введите логин"));
        user.setPswd(hashPassword(getPswd()));
        return user;
    }


    public static int logIn() {
        try {
            User user = generateUser();
            user.setId(-1);
            sendMessWithUser(user, getUID());

            Integer UID = null;
            Message message = null;
            while (UID == null) {
                message = serverRead();
                UID = message.getUID();
            }
            print(message.getMessageName());
            return UID;

        } catch (NumberFormatException e) {
            print("Сервер временно недоступен. Попробуйте позже.");
        }
        return -1;
    }


    public static int signIn() {
        try {
            User user = generateUser();
            user.setId(-2);
            sendMessWithUser(user, getUID());

            Integer UID = null;
            Message message = null;
            while (UID == null) {
                message = serverRead();
                UID = message.getUID();
            }
            print(message.getMessageName());
            return UID;
        } catch (NumberFormatException e) {
            print("Сервер временно недоступен. Попробуйте позже.");
        }
        return -1;
    }

    public static int getUID() {
        return UID;
    }


    private static String hashPassword(String password) {
        try {
            // Создаем объект MessageDigest с алгоритмом MD2
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // Преобразуем пароль в массив байтов
            byte[] passwordBytes = password.getBytes();

            // Вычисляем хэш пароля
            byte[] hashBytes = md.digest(passwordBytes);

            // Преобразуем хэш в строку шестнадцатеричного представления
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
