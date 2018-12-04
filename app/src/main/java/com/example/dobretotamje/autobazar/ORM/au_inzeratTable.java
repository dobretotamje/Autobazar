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

public class au_inzeratTable {

    private static String SQL_SELECT_ID = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat WHERE in_ID = ?";
    private static String SQL_SELECT_CENA = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat WHERE cena >= ? AND cena <= ?";
    private static String SQL_INSERT = "INSERT INTO au_inzerat (au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static String SQL_DELETE_ID = "DELETE FROM au_inzerat WHERE in_ID=?";
    private static String SQL_SELECT_AU_ID = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat WHERE au_ID = ?";
    private static String SQL_SELECT = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static LinkedList<au_inzerat> Select() {
        Database db = new Database();
        PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT);

        List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
        return proccessResultSet(tableWithValues);
    }

    public static LinkedList<au_inzerat> Select_Au_Id(int au_ID) {
        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_AU_ID);
            preparedStatement.setInt(1, au_ID);

            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            return proccessResultSet(tableWithValues);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_Au_Id!", e);
        }
        return new LinkedList<>();
    }

    public static LinkedList<au_inzerat> Select_Cena(int cenaOd, int cenaDo) {
        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_CENA);
            preparedStatement.setInt(1, cenaOd);
            preparedStatement.setInt(2, cenaDo);

            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            return proccessResultSet(tableWithValues);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_Cena!", e);
        }
        return new LinkedList<>();
    }

    public static boolean Insert(au_inzerat inzerat) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        fillUpParams(command, inzerat);
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

    public static au_inzerat Select_ID(int in_ID) {
        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_ID);
            preparedStatement.setInt(1, in_ID);

            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            List<au_inzerat> au_inzeratList = proccessResultSet(tableWithValues);
            return au_inzeratList.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_ID!", e);
        }
        return new au_inzerat();
    }

    private static void fillUpParams(PreparedStatement command, au_inzerat inzerat) {
        try {
            command.setInt(1, inzerat.Au_id);
            command.setInt(2, inzerat.U_id);
            if (inzerat.Popis == null) {
                command.setNull(3, Types.NULL);
            } else {
                command.setString(3, inzerat.Popis);
            }
            command.setInt(4, inzerat.Cena);
            if (inzerat.Misto == null) {
                command.setNull(5, Types.NULL);
            } else {
                command.setString(5, inzerat.Misto);
            }
            command.setInt(6, inzerat.Rok_vyroby);
            command.setInt(7, inzerat.Rozvody);
            command.setInt(8, inzerat.Stav_kilometru);
            command.setInt(9, inzerat.Vzorek_pneu);
            if (inzerat.Bourane == null) {
                command.setNull(10, Types.NULL);
            } else {
                command.setString(10, inzerat.Bourane);
            }
            if (inzerat.Vybava == null) {
                command.setNull(11, Types.NULL);
            } else {
                command.setString(11, inzerat.Vybava);
            }
            command.setInt(12, inzerat.Vymena_spojky);
            command.setInt(13, inzerat.Majitel);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }

    static private LinkedList<au_inzerat> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<au_inzerat> au_inzeratList = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            au_inzerat inzerat = new au_inzerat();
            inzerat.In_id = (int) ResultSetRow.columnToValue(row.get(0));
            inzerat.Au_id = (int) ResultSetRow.columnToValue(row.get(1));
            inzerat.U_id = (int) ResultSetRow.columnToValue(row.get(2));
            inzerat.Popis = (String) ResultSetRow.columnToValue(row.get(3));
            inzerat.Cena = (int) ResultSetRow.columnToValue(row.get(4));
            inzerat.Misto = (String) ResultSetRow.columnToValue(row.get(5));
            inzerat.Rok_vyroby = (int) ResultSetRow.columnToValue(row.get(6));
            inzerat.Rozvody = (int) ResultSetRow.columnToValue(row.get(7));
            inzerat.Stav_kilometru = (int) ResultSetRow.columnToValue(row.get(8));
            inzerat.Vzorek_pneu = (int) ResultSetRow.columnToValue(row.get(9));
            inzerat.Bourane = (String) ResultSetRow.columnToValue(row.get(10));
            inzerat.Vybava = (String) ResultSetRow.columnToValue(row.get(11));
            inzerat.Vymena_spojky = (int) ResultSetRow.columnToValue(row.get(12));
            inzerat.Majitel = (int) ResultSetRow.columnToValue(row.get(13));
            au_inzeratList.add(inzerat);
        }
        return au_inzeratList;
    }
}
