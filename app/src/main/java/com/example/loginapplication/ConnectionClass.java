package com.example.loginapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String classs = "com.mysql.cj.jdbc.Driver";

    String url = "jdbc:mysql://192.168.0.17:3000/mydb";
    String un = "root";
    String password = "YourRootPassword";



    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);

            conn = DriverManager.getConnection(url, un, password);


            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERR", e.getMessage());
        }
        return conn;
    }
}
