package it.mat.unical.Helion_Prime.SavesManager;

import it.mat.unical.Helion_Prime.Logic.CommonProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2DbManager {

	private static H2DbManager instance;
	private Connection connection;
	private boolean engaged;
	
	public static H2DbManager getInstance() {
		if ( instance == null ) {
			instance = new H2DbManager();
		}
		return instance;
	}
	
	private H2DbManager() {
		engaged = false;
	}
	
	public void H2engage() {
	
		if ( !engaged ) {
			try {
				Class.forName("org.h2.Driver");
			} catch (ClassNotFoundException e1) {
			}
			try {
				
				String db = CommonProperties.getInstance().getDatabase();
				String dbUser = CommonProperties.getInstance().getDbUser();
				String dbPassword = CommonProperties.getInstance().getDbPassword();
				
				connection = DriverManager.getConnection(db,dbUser,dbPassword);
				System.err.println("Local DB up and running");
				engaged = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    
	}
	
	
	public void H2disengange() {
		
		if ( engaged ) {
			try {
				connection.close();
				engaged = false;
				System.err.println("Local DB down");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
	
	
}
