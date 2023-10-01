package org.example.DBTools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBTools.DataBaseConnection.getConnection;

public class TableExistChecker {
    public static boolean tableExist(String tableName){
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        try{
            PreparedStatement existStatement = getConnection().prepareStatement(sql);
            existStatement.setString(1, tableName);
            ResultSet resultSet = existStatement.executeQuery();
            if(resultSet.next()){return resultSet.getBoolean(1);}
        }catch (SQLException e){}

        return false;
    }
}
