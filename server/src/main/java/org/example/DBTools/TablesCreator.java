package org.example.DBTools;

import java.sql.SQLException;
import java.sql.Statement;

import static org.example.DBTools.DataBaseConnection.getConnection;
import static org.example.DBTools.TableExistChecker.tableExist;
import static org.example.tools.Printer.print;

public class TablesCreator {

    private static boolean executeSQLScript(String createTableSQL) throws SQLException {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.execute(createTableSQL);
            return true;
        } catch (SQLException e) {
            print(e.getMessage());
            print("не смог создать таблицу");
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return false;
    }

    public static void createDbHumanBeingTable() throws SQLException {

        if (!tableExist("users")) {
            try {
                createDbUserTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу users");
            }
        }
        String createTableSQL = "CREATE TABLE IF NOT EXISTS humans (\n" +
                "    id SERIAL PRIMARY KEY, -- Это автоинкрементное поле для id\n" +
                "    user_id INT,\n" +
                "    name VARCHAR(255),\n" +
                "    coord_x DOUBLE PRECISION,\n" +
                "    coord_y DOUBLE PRECISION,\n" +
                "    creation_time VARCHAR(50),\n" +
                "    realHero BOOLEAN,\n" +
                "    hasToothpick BOOLEAN,\n" +
                "    impactSpeed DOUBLE PRECISION,\n" +
                "    weaponType VARCHAR(20),\n" +
                "    Mood VARCHAR(20),\n" +
                "    carName VARCHAR(255),\n" +
                "    carCool BOOLEAN\n" +
                ");\n";

        if (executeSQLScript(createTableSQL)) {
            print("Таблица humans была создана");
        }
    }


    // создание таблицы с User'ами
    public static void createDbUserTable() throws SQLException {

        String createTableSQL = "create table users" +
                "(id serial primary key not null," +
                "username varchar(50) not null," +
                "pswd text not null)";

        if (executeSQLScript(createTableSQL)) {
            print("Таблица users была создана");
        }
    }
}
