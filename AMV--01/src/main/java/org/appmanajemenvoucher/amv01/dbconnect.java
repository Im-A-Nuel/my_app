package org.appmanajemenvoucher.amv01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {

    private Connection connection;
    private  static dbconnect dbconnect;

    private dbconnect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:amvdb");
    }

    public static Connection getConnection() throws SQLException{

        if (dbconnect == null) {
            dbconnect = new dbconnect();
        }
        return dbconnect.connection;
    }


}
