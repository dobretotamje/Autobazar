package com.example.dobretotamje.autobazar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url = "jdbc:oracle:thin:@dbsys.cs.vsb.cz\\STUDENT:1521:oracle";
    String userName = "kry0051";
    String password = "cxStxvXEnp";
    Connection cnn;
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnn = DriverManager.getConnection(url, userName, password);
            String s =  "test";
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
