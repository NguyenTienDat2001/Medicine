/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Asus
 */
public class ConnectDB {
    private static String username = "root";
    private static String password = "123456789";
    private static String url = "jdbc:mysql://localhost:3306/medicine";
    
    public static Connection getConnect() throws SQLException{
        return DriverManager.getConnection(url,username,password);
    }
}
