package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Return Connection to Database (Using Connection Pool)
 * @author PC1
 *
 */
public class Connect {
	private static Connection con;
	public static Connection getConnection() throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajaxdb","root","");
		return con;
	}
}
