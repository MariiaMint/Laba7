package org.example.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.tools.Printer.print;

public class DataBaseConnection {
    private static Connection connection;
    private final String URL;
    private final String username;
    private final String password;

    public DataBaseConnection(String url, String username, String password) {
        URL = url;
        this.username = username;
        this.password = password;
    }
    public void connectDB(){
        try {
            connection = DriverManager.getConnection(URL, username, password);
            print("подключение к ДБ установлено");
        } catch (SQLException e) {
            print(e.getMessage()+"не удалось подключиться к ДБ");
            System.exit(-1);
        }
    }

    public static Connection getConnection(){return connection;}
}
