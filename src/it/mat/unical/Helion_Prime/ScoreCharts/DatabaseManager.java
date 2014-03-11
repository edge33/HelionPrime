package it.mat.unical.Helion_Prime.ScoreCharts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

private static DatabaseManager instance;
	
	private final String user = new String("user");
	private final String pwd = new String("userpasswd");

	private Connection connection;
	
	public static DatabaseManager getInstance() {
		if ( instance == null ) 
			instance = new DatabaseManager();
		return instance;
	}
	
	private DatabaseManager() {
		
	}
	
	
	public Connection getConnection() {
		return connection;
	}
	
	public void DbConnect() {
		
		// carico il driver JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		// mi connetto al database con i dati inseriti nel main
        // la password può non essere necessaria
        try {
			connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/helionprime",user,pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
	}
	
	public void DbClose() {
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
