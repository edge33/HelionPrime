package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DbManager {

	private static H2DbManager instance;
	private Connection connection;
	
	public static H2DbManager getInstance() {
		if ( instance == null )
			instance = new H2DbManager();
		return instance;
	}
	
	private H2DbManager() {
		
	}
	
	public void H2engage() {
		
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			connection = DriverManager.getConnection("jdbc:h2:DataBase/helionPrime", "inserter", "afg98501fds2013");
			System.err.println("Local DB up and running");
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	
	public void H2disengange() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
