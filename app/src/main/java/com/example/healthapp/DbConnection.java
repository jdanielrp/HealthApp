package com.example.healthapp;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection conn;
    private static String connectionUrl = "jdbc:jtds:sqlserver://cirubari-server.database.windows.net:1433;DatabaseName=cirubari-app;user=juandanr@cirubari-server;password=Ortega27;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    public static Connection connect() throws SQLException{
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        } catch(ClassNotFoundException cnfe){
            System.err.println("Error: " + cnfe.getMessage());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(connectionUrl);
        return conn;
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn != null && !conn.isClosed())
            return conn;
        connect();
        return conn;
    }
}