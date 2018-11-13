package com.example.dobretotamje.autobazar.ORM;

import com.example.dobretotamje.autobazar.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

public class au_inzeratTable {

    public static String SQL_SELECT_ID = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat WHERE in_ID = ?";
    public static String SQL_INSERT = "INSERT INTO au_inzerat (au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static String SQL_DELETE_ID = "DELETE FROM au_inzerat WHERE in_ID=?";
    public static String SQL_SELECT_AU_ID = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat WHERE au_ID = ?";
    public static String SQL_SELECT = "SELECT in_id, au_id, u_id, popis, cena, misto, rok_vyroby, rozvody, stav_kilometru, vzorek_pneu, bourane, vybava, vymena_spojky, majitel FROM au_inzerat";

    public static List<au_inzerat> Select_Au_Id(int in_ID) {
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_SELECT_AU_ID);
            command.setInt(1, in_ID);

            ResultSet resultSet = db.Select(command);
            List<au_inzerat> au_inzeratList = proccessResultSet(resultSet);
            db.Close();

            return au_inzeratList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static List<au_inzerat> Select() {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_SELECT_AU_ID);

        ResultSet resultSet = db.Select(command);
        List<au_inzerat> au_inzeratList = proccessResultSet(resultSet);
        db.Close();

        return au_inzeratList;
    }

    public static int Insert(au_inzerat inzerat) {
        Database db = new Database();
        PreparedStatement command = db.CreateCommand(SQL_INSERT);
        PrepareCommand(command, inzerat);
        int ret = db.ExecuteNonQuery(command);

        db.Close();

        return ret;
    }

    public static int Delete(int in_ID) {
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_DELETE_ID);
            command.setInt(1, in_ID);
            int ret = db.ExecuteNonQuery(command);

            db.Close();

            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static au_inzerat Select_ID(int in_ID) {
        au_inzerat inzerat = new au_inzerat();
        try {
            Database db = new Database();
            PreparedStatement command = db.CreateCommand(SQL_SELECT_ID);
            command.setInt(1, in_ID);

            ResultSet resultSet = db.Select(command);
            resultSet.next();
            inzerat.In_id = resultSet.getInt(1);
            inzerat.Au_id = resultSet.getInt(2);
            inzerat.U_id = resultSet.getInt(3);
            inzerat.Popis = resultSet.getString(4);
            inzerat.Cena = resultSet.getInt(5);
            inzerat.Misto = resultSet.getString(6);
            inzerat.Rok_vyroby = resultSet.getInt(7);
            inzerat.Rozvody = resultSet.getInt(8);
            inzerat.Stav_kilometru = resultSet.getInt(9);
            inzerat.Vzorek_pneu = resultSet.getInt(10);
            inzerat.Bourane = resultSet.getString(11);
            inzerat.Vybava = resultSet.getString(12);
            inzerat.Vymena_spojky = resultSet.getInt(13);
            inzerat.Majitel = resultSet.getInt(14);

            db.Close();

            return inzerat;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inzerat;
    }

    private static void PrepareCommand(PreparedStatement command, au_inzerat inzerat) {
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
                command.setString(3, inzerat.Misto);
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
            command.setInt(12, inzerat.Majitel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static private List<au_inzerat> proccessResultSet(ResultSet resultSet) {
        try {
            List<au_inzerat> au_inzeratList = new LinkedList<>();
            while (resultSet.next()) {
                au_inzerat inzerat = new au_inzerat();
                inzerat.In_id = resultSet.getInt(1);
                inzerat.Au_id = resultSet.getInt(2);
                inzerat.U_id = resultSet.getInt(3);
                inzerat.Popis = resultSet.getString(4);
                inzerat.Cena = resultSet.getInt(5);
                inzerat.Misto = resultSet.getString(6);
                inzerat.Rok_vyroby = resultSet.getInt(7);
                inzerat.Rozvody = resultSet.getInt(8);
                inzerat.Stav_kilometru = resultSet.getInt(9);
                inzerat.Vzorek_pneu = resultSet.getInt(10);
                inzerat.Bourane = resultSet.getString(11);
                inzerat.Vybava = resultSet.getString(12);
                inzerat.Vymena_spojky = resultSet.getInt(13);
                inzerat.Majitel = resultSet.getInt(14);
                au_inzeratList.add(inzerat);
            }
            return au_inzeratList;
        } catch (SQLException e) {
        }
        return new LinkedList<>();
    }
}
