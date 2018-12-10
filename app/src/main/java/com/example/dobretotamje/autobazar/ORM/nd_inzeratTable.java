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

public class nd_inzeratTable {
    //public static String SQL_SELECT_AU_ID = "SELECT inz.nd_id, inz.u_id, inz.misto, inz.cena, inz.popis, inz.opotrebeni FROM nd_inzerat inz JOIN nahradni_dil nd ON nd.nd_ID = inz.nd_ID WHERE nd.au_ID = ?";
    private static String SQL_SELECT = "SELECT inz.in_id, dil.nazev, inz.nd_id, inz.u_id, inz.misto, inz.cena, inz.popis, inz.opotrebeni FROM nd_inzerat inz JOIN nahradni_dil dil ON dil.nd_id = inz.nd_id";
    private static String SQL_SELECT_ND_ID = "SELECT in_id, nd_id, u_id, misto, cena, popis, opotrebeni FROM nd_inzerat WHERE nd_id = ?";
    private static String SQL_INSERT = "INSERT INTO nd_inzerat (nd_id, u_id, misto, cena, popis, opotrebeni) VALUES (?, ?, ?, ?, ?, ?)";
    private static String SQL_DELETE_ID = "DELETE FROM nd_inzerat WHERE in_ID=?";
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    public static LinkedList<nd_inzerat> Select() {
        Database db = new Database();
        PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT);

        List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
        return proccessResultSet(tableWithValues);
    }

    public static LinkedList<nd_inzerat> Select_Nd_Id(int nd_ID) {

        try {
            Database db = new Database();
            PreparedStatement preparedStatement = db.CreateCommand(SQL_SELECT_ND_ID);
            preparedStatement.setInt(1, nd_ID);
            List<ResultSetRow> tableWithValues = db.Select(preparedStatement);
            return proccessResultSet(tableWithValues);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during Select_Nd_Id!", e);
        }
        return new LinkedList<>();
    }

    public static boolean Insert(nd_inzerat inzerat) {
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

    private static void fillUpParams(PreparedStatement command, nd_inzerat inzerat) {
        try {
            command.setInt(1, inzerat.Nd_id);
            command.setInt(2, inzerat.U_id);
            if (inzerat.Misto == null) {
                command.setNull(3, Types.NULL);
            } else {
                command.setString(3, inzerat.Misto);
            }
            command.setInt(4, inzerat.Cena);
            if (inzerat.Popis == null) {
                command.setNull(5, Types.NULL);
            } else {
                command.setString(5, inzerat.Popis);
            }
            if (inzerat.Opotrebeni == null) {
                command.setNull(6, Types.NULL);
            } else {
                command.setString(6, inzerat.Opotrebeni);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occured during fillUpParams!", e);
        }
    }

    //SELECT in_id, nd_id, u_id, misto, cena, popis, opotrebeni FROM nd_inzerat WHERE nd_id = ?";
    static private LinkedList<nd_inzerat> proccessResultSet(List<ResultSetRow> tableWithValues) {
        LinkedList<nd_inzerat> nd_inzeratList = new LinkedList<>();
        for (ResultSetRow resultSetRow : tableWithValues) {
            List<Map.Entry<Object, Class>> row = resultSetRow.row;
            nd_inzerat inzerat = new nd_inzerat();
            inzerat.In_id = (int) ResultSetRow.columnToValue(row.get(0));
            inzerat.Nd_id = (int) ResultSetRow.columnToValue(row.get(1));
            inzerat.U_id = (int) ResultSetRow.columnToValue(row.get(2));
            inzerat.Misto = (String) ResultSetRow.columnToValue(row.get(3));
            inzerat.Cena = (int) ResultSetRow.columnToValue(row.get(4));
            inzerat.Popis = (String) ResultSetRow.columnToValue(row.get(5));
            inzerat.Opotrebeni = (String) ResultSetRow.columnToValue(row.get(6));

            //inzerat.nazev_dilu = (String) ResultSetRow.columnToValue(row.get(1));

            nd_inzeratList.add(inzerat);
        }
        return nd_inzeratList;
    }
}
