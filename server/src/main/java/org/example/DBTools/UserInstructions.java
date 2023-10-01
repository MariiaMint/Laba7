package org.example.DBTools;

import org.example.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBTools.DataBaseConnection.getConnection;
import static org.example.tools.Printer.print;

public class UserInstructions {
    private static final Connection connection = getConnection();

    // шаблон запроса на создание user
    private static final String ADD_USER_REQUEST = "INSERT INTO users (username, pswd) VALUES (?, ?) RETURNING id";


    // добавление пользователя
    public static int addUser(User user) {
        int generatedId = -1;
        try {
            if (userExist(user) >= 0) {
                print("Пользователь с такими данными уже существует");
            }
            else {
                PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
                addStatement.setString(1, user.getName());
                addStatement.setString(2, user.getPswd());

                try (ResultSet resultSet = addStatement.executeQuery()) {
                    if (resultSet.next()) {
                        generatedId = resultSet.getInt("id");
                        print("Пользователь успешно добавлен. Сгенерированный ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            print("Произошла ошибка в бд: " + e.getMessage());
        }
        return generatedId;
    }


    // проверка на существование пользователя и возврат его Id, или -1 если пользователь не найден
    public static int userExist(User user) {
        int uId = -1;
        try {
            String query = "SELECT id FROM Users WHERE username = ? AND pswd = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPswd());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        uId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            print("Произошла ошибка в бд: " + e.getMessage());
        }
        return uId;
    }

}
