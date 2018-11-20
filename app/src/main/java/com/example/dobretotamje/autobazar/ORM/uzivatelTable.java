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

public class uzivatelTable {

    //public static String SQL_UPDATE = "UPDATE uzivatel SET jmeno = @jmeno, tel_cislo = @tel_cislo, email = @email, pohlavi = @pohlavi WHERE u_ID = ?";
    private static String SQL_SELECT = "SELECT u_id, jmeno, tel_cislo, email, pohlavi FROM uzivatel";
    private static String SQL_INSERT = "INSERT INTO uzivatel (jmeno, tel_cislo, email, pohlavi) VALUES (?, ?, ?, ?)";
    private static String SQL_DELETE_ID = "DELETE FROM uzivatel WHERE u_ID=?";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static LinkedList<uzivatel> Select() {
        Database db = new Database();
        PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT);

        List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
        return proccessResultSet(tableWithValues);
    }

    public static boolean Insert(uzivatel uziv) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        fillUpParams(command, uziv);
        return db.ExecuteNonQuery(command);
    }

    public static boolean Delete(int in_ID) {
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_DELETE_ID);
            command.setInt(1, in_ID);
            return db.ExecuteNonQuery(command);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Delete!", e);
        }
        return false;
    }

    private static void fillUpParams(PreparedStatement command, uzivatel uziv) {
        try {
            if (uziv.Jmeno == null) {
                command.setNull(1, Types.NULL);
            } else {
                command.setString(1, uziv.Jmeno);
            }
            if (uziv.Tel_cislo == null) {
                command.setNull(2, Types.NULL);
            } else {
                command.setString(2, uziv.Tel_cislo);
            }
            if (uziv.Email == null) {
                command.setNull(3, Types.NULL);
            } else {
                command.setString(3, uziv.Email);
            }
            if (uziv.Pohlavi == null) {
                command.setNull(4, Types.NULL);
            } else {
                command.setString(4, uziv.Pohlavi);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }
    static private LinkedList<uzivatel> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<uzivatel> uzivatelList = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            uzivatel uziv = new uzivatel();
            uziv.U_id = (int) ResultSetRow.columnToValue(row.get(0));
            uziv.Jmeno = (String) ResultSetRow.columnToValue(row.get(1));
            uziv.Tel_cislo = (String) ResultSetRow.columnToValue(row.get(2));
            uziv.Email = (String) ResultSetRow.columnToValue(row.get(3));
            uziv.Pohlavi = (String) ResultSetRow.columnToValue(row.get(4));
            uzivatelList.add(uziv);
        }
        return uzivatelList;
    }
}
