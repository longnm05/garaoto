package com.garaoto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/garaoto?useUnicode=true&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            throw new SQLException("Không tìm thấy MySQL Connector/J trong WEB-INF/lib.", error);
        }
        return DriverManager.getConnection(URL, "root", "");
    }
}
