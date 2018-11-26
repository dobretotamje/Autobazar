package com.example.dobretotamje.autobazar.ORM;

import com.example.dobretotamje.autobazar.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class autoTable {

    private static String SQL_INSERT = "INSERT INTO auto (nazev, zn_id, spotreba, vykon) VALUES (?, ?, ?, ?)";
    private static String SQL_SELECT = "SELECT au_id, nazev, zn_id, spotreba, vykon FROM auto";
    private static String SQL_SELECT_ZN_ID = "SELECT au_id, nazev, zn_id, spotreba, vykon FROM auto WHERE zn_ID = ?";
    private static String SQL_DELETE_ID = "DELETE FROM auto WHERE au_ID = ?";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static LinkedList<auto> Select() {
        Database db = new Database();
        PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT);

        List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
        return proccessResultSet(tableWithValues);
    }

    public static boolean Insert(auto a) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        fillUpParams(command, a);
        return db.ExecuteNonQuery(command);
    }

    public static LinkedList<auto> Select_Zn_Id(int zn_ID) {

        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_ZN_ID);
            preparedStatement.setInt(1, zn_ID);
            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            return proccessResultSet(tableWithValues);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_Zn_Id!", e);
        }
        return new LinkedList<>();
    }

    public static boolean Delete(int au_ID) {
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_DELETE_ID);
            command.setInt(1, au_ID);
            return db.ExecuteNonQuery(command);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Delete!", e);
        }
        return false;
    }

    static private LinkedList<auto> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<auto> autoList = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            auto a = new auto();
            a.Au_id = (int) ResultSetRow.columnToValue(row.get(0));
            a.Nazev = (String) ResultSetRow.columnToValue(row.get(1));
            a.Zn_id = (int) ResultSetRow.columnToValue(row.get(2));
            a.Spotreba = (double) ResultSetRow.columnToValue(row.get(3));
            a.Vykon = (double) ResultSetRow.columnToValue(row.get(4));
            autoList.add(a);
        }
        return autoList;
    }

    private static void fillUpParams(PreparedStatement command, auto a) {
        try {
            if (a.Nazev == null) {
                command.setNull(1, Types.NULL);
            } else {
                command.setString(1, a.Nazev);
            }
            command.setInt(2, a.Zn_id);
            command.setDouble(3, a.Spotreba);
            command.setDouble(4, a.Vykon);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }
}
