package org.example.DBTools;

import org.example.beginningClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

import static org.example.DBTools.DataBaseConnection.getConnection;
import static org.example.DBTools.TableExistChecker.tableExist;
import static org.example.DBTools.TablesCreator.createDbHumanBeingTable;
import static org.example.tools.Printer.print;

public class HumanBeingInstructions {
    private static final Connection connection = getConnection();

    // шаблон запроса на добавление Human'a
    private static final String ADD_HUMAN_REQUEST = "INSERT INTO humans (user_id, name, coord_x, coord_y," +
            "creation_time, realHero, hasToothpick, impactSpeed," +
            "weaponType, Mood, carName, carCool)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    // проверка на существование human'a
    private static boolean humanExist(int id) {
        if (!tableExist("humans")) {
            try {
                createDbHumanBeingTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу humans");
            }
        }

        boolean flag = false;
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM humans WHERE id = ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        flag = resultSet.getBoolean(1);
                    }
                }
            }
        } catch (SQLException e) {
            print("Произошла ошибка в бд: " + e.getMessage());
        }
        return flag;
    }

    // получение всех human'ов с БД
    public static Vector<HumanBeing> getAllHumans() {
        Vector<HumanBeing> vect = new Vector<>();
        if (!tableExist("humans")) {
            try {
                createDbHumanBeingTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу humans");
            }
        }

        String query = "SELECT * FROM humans";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HumanBeing humanBeing = new HumanBeing();

                    humanBeing.setUserId(resultSet.getInt("user_id"));
                    humanBeing.setId(resultSet.getInt("id"));
                    humanBeing.setName(resultSet.getString("name").trim());
                    humanBeing.setCoordinates(
                            new Coordinates(
                                    resultSet.getDouble("coord_x"),
                                    resultSet.getDouble("coord_y"))
                    );
                    humanBeing.setCreationDate(LocalDateTime.parse(resultSet.getString("creation_time").trim()));
                    humanBeing.setRealHero(resultSet.getBoolean("realHero"));
                    String mood = resultSet.getString("Mood").trim();
                    if (mood.equals("null")) {
                        humanBeing.setMood(null);
                    } else {
                        humanBeing.setMood(Mood.valueOf(mood));
                    }
                    humanBeing.setImpactSpeed(resultSet.getDouble("impactSpeed"));
                    humanBeing.setCar(
                            new Car(
                                    resultSet.getString("carName"),
                                    resultSet.getBoolean("carCool")
                            )
                    );
                    String weapon = resultSet.getString("weaponType").trim();
                    if (weapon.equals("null")) {
                        humanBeing.setWeaponType(null);
                    } else {
                        humanBeing.setWeaponType(WeaponType.valueOf(weapon));
                    }


                    humanBeing.setHasToothpick(resultSet.getBoolean("hasToothpick"));

                    vect.add(humanBeing);
                }
            }
        } catch (SQLException e) {
            print("Произошла ошибка при чтении humanBeing" + e.getMessage());
        }
        return vect;
    }

    // добавление humana'a
    public static boolean addHuman(int userId, HumanBeing human) {
        if (!tableExist("humans")) {
            try {
                TablesCreator.createDbHumanBeingTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу humans");
            }
        }

        boolean flag = false;

        try {
            PreparedStatement statement = connection.prepareStatement(ADD_HUMAN_REQUEST);

            statement.setInt(1, userId);
            statement.setString(2, human.getName());
            statement.setDouble(3, human.getCoordinateX());
            statement.setDouble(4, human.getCoordinateY());
            statement.setString(5, String.valueOf(human.getCreationDate()));
            statement.setBoolean(6, human.isRealHero());
            statement.setString(10, String.valueOf(human.getMood()));
            statement.setDouble(8, human.getImpactSpeed());
            statement.setString(11, human.getCar().getName());
            statement.setBoolean(12, human.getCar().isCool());
            statement.setString(9, String.valueOf(human.getWeaponType()));
            statement.setBoolean(7, human.getHasToothpick());

            statement.executeUpdate();
            statement.close();
            print("human успешно добавлен в ДБ");
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }


    // метод для удаления определенного человека
    public static boolean deleteHuman(int userId, int humanId) {
        if (!tableExist("humans")) {
            try {
                TablesCreator.createDbHumanBeingTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу humans");
            }
        }

        boolean flag = false;

        try {
            if (!humanExist(humanId)) {
                print("Human с указанным ID не существует");
            } else {
                String query = "DELETE FROM humans WHERE user_id = ? AND id = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, userId);
                    statement.setInt(2, humanId);

                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) {
                        print("Human успешно удален из БД");
                        flag = true;
                    } else {
                        print("Не удалось удалить Human с указанным ID");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flag;
    }


    //метод для удаления какого-то количества человек
    public static boolean deleteHumansWithUserId(int userId, int count) {
        if (!tableExist("humans")) {
            try {
                TablesCreator.createDbHumanBeingTable();
            } catch (SQLException e) {
                print("Не удалось восстановить таблицу humans");
            }
        }

        int rowsDeleted = 0;

        try {
            String query = "DELETE FROM humans WHERE id IN " +
                    "(SELECT id FROM humans WHERE user_id = ? LIMIT ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, count);

                rowsDeleted = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rowsDeleted > 0;
    }

    public static boolean updateHuman(HumanBeing human, Integer UID) {
        boolean updated = false;
        String query = "UPDATE humans SET name = ?, coord_x = ?, coord_y = ?, creation_time = ?, realHero = ?, hasToothpick = ?, impactSpeed = ?, weaponType = ?, Mood = ?, carName = ?, carCool = ?, user_id = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(13, human.getId());
            statement.setString(1, human.getName());
            statement.setDouble(2, human.getCoordinateX());
            statement.setDouble(3, human.getCoordinateY());
            statement.setString(4, String.valueOf(human.getCreationDate()));
            statement.setBoolean(5, human.isRealHero());
            statement.setString(9, String.valueOf(human.getMood()));
            statement.setDouble(7, human.getImpactSpeed());
            statement.setString(10, human.getCar().getName());
            statement.setBoolean(11, human.getCar().isCool());
            statement.setString(8, String.valueOf(human.getWeaponType()));
            statement.setBoolean(6, human.getHasToothpick());
            statement.setInt(12, UID);

            statement.executeUpdate();
            statement.close();
            updated = true;
            print("human успешно обновлен в Базе");
        } catch (SQLException e) {
            print("человек не обновлен в БД");
        }
        return updated;
    }
}
