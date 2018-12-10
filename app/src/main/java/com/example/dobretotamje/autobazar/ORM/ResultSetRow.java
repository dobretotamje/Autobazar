package com.example.dobretotamje.autobazar.ORM;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultSetRow {
    public static Map<String, Class> TYPE;
    private static Logger LOGGER = Logger.getLogger(ResultSetRow.class.getName());

    static {
        TYPE = new HashMap<>();
        TYPE.put("INTEGER", Integer.class);
        TYPE.put("INT", Integer.class);
        TYPE.put("FLOAT", Double.class);
        TYPE.put("CHAR", String.class);
        TYPE.put("VARCHAR", String.class);
        TYPE.put("DATE", Date.class);
        TYPE.put("TIME", Time.class);
        TYPE.put("INT IDENTITY", Integer.class);
    }

    public List<Map.Entry<Object, Class>> row;

    public ResultSetRow() {
        row = new ArrayList<>();
    }

    public static void formTable(ResultSet rs, List<ResultSetRow> table) {
        if (rs == null)
            return;

        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
            int NumOfCol = rsmd.getColumnCount();

            while (rs.next()) {
                ResultSetRow current_row = new ResultSetRow();
                for (int i = 1; i <= NumOfCol; i++) {
                    current_row.add(rs.getObject(i), rsmd.getColumnTypeName(i));
                }
                table.add(current_row);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage() + "Probably nothing was added to collection!", e);
        }
    }

    public static Object columnToValue(Map.Entry<Object, Class> data){
        if(data.getKey() == null && data.getValue() == Integer.class){
            return 0;
        }
        return data.getValue().cast(data.getKey());
    }

    public <T> void add(T data, Class castType) {
        row.add(new AbstractMap.SimpleImmutableEntry<Object, Class>(data, castType));
    }

    public void add(Object data, String sqlType) {
        Class castType = ResultSetRow.TYPE.get(sqlType.toUpperCase());
        try {
            this.add(castType.cast(data), castType);
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Add the type " + sqlType + " to the TYPE hash map in the Row class.", e);
            throw e;
        }
    }
}
