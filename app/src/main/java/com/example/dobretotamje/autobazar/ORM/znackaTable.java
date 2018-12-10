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

public class znackaTable {

    private static String SQL_INSERT = "INSERT INTO znacka (nazev, zeme_vyroby, koncern, hodnoceni_prumer) VALUES (?, ?, ?, ?)";
    private static String SQL_SELECT = "SELECT zn_id, nazev, zeme_vyroby, koncern, hodnoceni_prumer FROM znacka";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static LinkedList<znacka> Select() {
        Database db = new Database();
        PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT);

        List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
        return proccessResultSet(tableWithValues);
    }

    public static boolean Insert(znacka znac) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        fillUpParams(command, znac);
        return db.ExecuteNonQuery(command);
    }

    private static void fillUpParams(PreparedStatement command, znacka znac) {
        try {
            if (znac.Nazev == null) {
                command.setNull(1, Types.NULL);
            } else {
                command.setString(1, znac.Nazev);
            }
            if (znac.Zeme_vyroby == null) {
                command.setNull(2, Types.NULL);
            } else {
                command.setString(2, znac.Zeme_vyroby);
            }
            if (znac.Koncern == null) {
                command.setNull(3, Types.NULL);
            } else {
                command.setString(3, znac.Koncern);
            }
            command.setInt(4, 1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }

    static private LinkedList<znacka> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<znacka> znackaList = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            znacka znac = new znacka();
            znac.Zn_id = (int) ResultSetRow.columnToValue(row.get(0));
            znac.Nazev = (String) ResultSetRow.columnToValue(row.get(1));
            znac.Zeme_vyroby = (String) ResultSetRow.columnToValue(row.get(2));
            znac.Koncern = (String) ResultSetRow.columnToValue(row.get(3));
            znac.Hodnoceni_prumer = (int) ResultSetRow.columnToValue(row.get(4));
            znackaList.add(znac);
        }
        return znackaList;
    }
}
