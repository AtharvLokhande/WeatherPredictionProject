package org.aatech.repository;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBConfig {

	protected static Connection conn;
	protected static PreparedStatement stmt;
	protected static ResultSet rs;
	protected static CallableStatement cstmt;
	private static DBConfig db = null;
	

	protected DBConfig() {
		try {
			File f =new File("");
			String Path = f.getAbsolutePath()+"/src/main/resources/dbconfig.properties";
	    	FileInputStream inputStream = new FileInputStream(Path);
			
			Properties p = new Properties();
			
			p.load(inputStream);
			
			String driverClassName = p.getProperty("driver");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			String url = p.getProperty("url");
			
			Class.forName(driverClassName);
			
			conn = DriverManager.getConnection(url, username, password);
			
			

		} catch (Exception ex) {
			System.out.println("Error is" + ex);

		}
	}
	
	public static DBConfig getInstance() {
		if (db == null) {
			db = new DBConfig();
		}
		return db;
	}

	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getStatement() {
		return stmt;
	}

	public static ResultSet getResultSet() {
		return rs;
	}
	
	public static CallableStatement getCallStatement() {
		return cstmt;
	}

}
