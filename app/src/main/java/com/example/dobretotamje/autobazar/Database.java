package com.example.dobretotamje.autobazar;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class Database extends AsyncTask<String, Void, Void> {

    String url = "jdbc:jtds:sqlserver://dbsys.cs.vsb.cz:1521;instance=STUDENT;user=kry0051;password=cxStxvXEnp";
    Connection connection;

    public Database()
    {
        this.execute(url);

        //Wait till task isnt done
        try {
            this.get();
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
        }
        return null;
    }


    public void Close()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int ExecuteNonQuery(PreparedStatement command)
    {
        int rowNumber = 0;
        try
        {
            command.execute();
        }
        catch (Exception e)
        {

        }
        return rowNumber;
    }

    public PreparedStatement CreateCommand(String strCommand)
    {
        try {
            PreparedStatement command = connection.prepareStatement(strCommand);
            return command;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet Select(PreparedStatement command)
    {
        try {
            ResultSet sqlReader = command.executeQuery();
            return sqlReader;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
