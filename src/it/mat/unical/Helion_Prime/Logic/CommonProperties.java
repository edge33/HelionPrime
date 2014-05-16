package it.mat.unical.Helion_Prime.Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CommonProperties {

	
	private String database;
	private String dbUser;
	private String dbPassword;
	
	public static CommonProperties instance;
	
	public static CommonProperties getInstance() {
		if ( instance == null ) {
			instance = new CommonProperties();
		}
		return instance;
	}
	
	private CommonProperties() {
	}
	
	public void loadProperties(File file) throws FileNotFoundException {
		
		Properties properties = new Properties();
		
		FileInputStream input = new FileInputStream(file);
		
		try {
			properties.load(input);
		} catch (IOException e) {
		}
 
		// get the property value and print it out
		database = properties.getProperty("database");
		dbUser = properties.getProperty("dbuser");
		dbPassword = properties.getProperty("dbpassword");
		
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getDbUser() {
		return dbUser;
	}
	
	public String getDbPassword() {
		return dbPassword;
	}

	
}
