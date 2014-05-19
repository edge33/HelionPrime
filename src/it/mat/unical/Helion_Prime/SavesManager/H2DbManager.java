package it.mat.unical.Helion_Prime.SavesManager;

import it.mat.unical.Helion_Prime.Logic.CommonProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.h2.tools.RunScript;

public class H2DbManager {

	private static H2DbManager instance;
	private Connection connection;
	
	public static H2DbManager getInstance() {
		if ( instance == null ) {
			instance = new H2DbManager();
		}
		return instance;
	}
	
	private H2DbManager() {
	}
	
	public void H2engage() {
	
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e1) {
		}
		try {
			
			String db = CommonProperties.getInstance().getDatabase();
			String dbUser = CommonProperties.getInstance().getDbUser();
			String dbPassword = CommonProperties.getInstance().getDbPassword();
			
			connection = DriverManager.getConnection(db,dbUser,dbPassword);
		} catch (SQLException e) {
		}
    
	}
	
	
	public void H2disengange() {
		
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	
	
	
	
	
}
