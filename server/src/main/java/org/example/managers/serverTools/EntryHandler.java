package org.example.managers.serverTools;

import org.example.User;

import java.net.Socket;
import java.sql.SQLException;

import static org.example.DBTools.TableExistChecker.tableExist;
import static org.example.DBTools.TablesCreator.createDbUserTable;
import static org.example.DBTools.UserInstructions.addUser;
import static org.example.DBTools.UserInstructions.userExist;
import static org.example.managers.serverTools.ResponseSenderServer.send;
import static org.example.tools.Printer.print;

public class EntryHandler {
    public void defineExistUser(User user, Socket socket) {
        switch (user.getId()) {
            case -1 -> logIn(user, socket);
            case -2 -> signIn(user, socket);
            default -> {send(null, 0, socket);
            }
        }
    }


    private void logIn(User user, Socket socket) {
        if (!tableExist("users")) {
            try {
                createDbUserTable();
            } catch (SQLException e) {
                print("Произошла ошибка создания несуществующей таблицы");
            }
        }

        int UID = addUser(user);
        if (UID != -1) {
            send("Вы зарегистрированы\n Ваш UID: " + UID, UID, socket);
        }else {
            send("Не верные логин или пароль", UID, socket);
        }
    }

    private void signIn(User user, Socket socket) {
        if (!tableExist("users")) {
            try {
                createDbUserTable();
            } catch (SQLException e) {
                print("Произошла ошибка создания несуществующей таблицы");
            }
        }

        int UID = userExist(user);
        if (UID != -1) {
            send("Вы авторизованы\n Ваш UID: " + UID, UID, socket);
        }else {
            send("Не верные логин или пароль", UID, socket);
        }
    }


}
