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

public class nahradni_dilTable {
    private static String SQL_INSERT = "INSERT INTO nahradni_dil (au_id, nazev, znacka, orig) VALUES (?, ?, ?, ?)";
    private static String SQL_SELECT_ZN_ID = "SELECT nd.nd_id, nd.au_id, nd.nazev, nd.znacka, nd.orig FROM nahradni_dil nd JOIN auto au ON au.au_ID = nd.au_ID WHERE zn_ID = ?";
    private static String SQL_DELETE = "DELETE FROM nahradni_dil WHERE nd_ID = ?";
    private static String SQL_SELECT_ND_ID = "SELECT nd.au_id, nd.nazev, nd.znacka, nd.orig FROM nahradni_dil nd WHERE nd_ID = ?";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static nahradni_dil Select_Nd_Id(int nd_ID) {
        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_ND_ID);
            preparedStatement.setInt(1, nd_ID);

            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            return proccessResultSet(tableWithValues).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_Nd_Id!", e);
        }
        return new nahradni_dil();
    }

    public static LinkedList<nahradni_dil> Select_Zn_Id(int zn_ID) {
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

    public static boolean Insert(nahradni_dil nd) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        fillUpParams(command, nd);
        return db.ExecuteNonQuery(command);
    }

    public static boolean Delete(int nd_ID) {
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_DELETE);
            command.setInt(1, nd_ID);
            return db.ExecuteNonQuery(command);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Delete!", e);
        }
        return false;
    }

    private static void fillUpParams(PreparedStatement command, nahradni_dil nd) {
        try {
            command.setInt(1, nd.Au_id);
            if (nd.Nazev == null) {
                command.setNull(2, Types.NULL);
            } else {
                command.setString(2, nd.Nazev);
            }
            if (nd.Znacka == null) {
                command.setNull(3, Types.NULL);
            } else {
                command.setString(3, nd.Znacka);
            }
            if (nd.Orig == null) {
                command.setNull(4, Types.NULL);
            } else {
                command.setString(4, nd.Orig);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }

    static private LinkedList<nahradni_dil> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<nahradni_dil> nahradni_dils = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            nahradni_dil nd = new nahradni_dil();
            nd.Nd_id = (int) ResultSetRow.columnToValue(row.get(0));
            nd.Au_id = (int) ResultSetRow.columnToValue(row.get(1));
            nd.Nazev = (String) ResultSetRow.columnToValue(row.get(2));
            nd.Znacka = (String) ResultSetRow.columnToValue(row.get(3));
            nd.Orig = (String) ResultSetRow.columnToValue(row.get(4));
            nahradni_dils.add(nd);
        }
        return nahradni_dils;
    }
}
