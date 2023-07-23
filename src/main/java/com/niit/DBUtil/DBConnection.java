package com.niit.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public  Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection=null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/musicplayer";
        String user="root";
        String pass="admin";
        connection= DriverManager.getConnection(url,user,pass);
        return connection;
    }
}
