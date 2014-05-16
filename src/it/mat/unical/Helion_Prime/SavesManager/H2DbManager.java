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
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public static void createDB() {
		
		String dbString ="CREATE USER IF NOT EXISTS INSERTER SALT \'bfaf9df3363b9ad0\' HASH \'ad847a2a11932a09d6b1d08bd8b4ba45308267e546802aa158ec5bb80bd0ba63\'; \n"+
				"CREATE USER IF NOT EXISTS SA SALT \'3b3627323eaa0e2b\' HASH \'f0d682116e343bc817c5a864d27dfeecf67bec41cca47f780c8d1786ff236aee\' ADMIN; \n"+
				"CREATE CACHED TABLE PUBLIC.RECORD(\n"+
				" USERNAME VARCHAR(255) NOT NULL,\n"+
				" TIME TIMESTAMP NOT NULL,\n"+
				" GUN1_BULLETS INT,\n"+
				" GUN2_BULLETS INT,\n"+
				" GUN3_BULLETS INT,\n"+
				" GUN4_BULLETS INT,\n"+
				" LASTLEVEL INT,\n"+
				" SCORE INT\n"+
				"); \n"+
				"ALTER TABLE PUBLIC.RECORD ADD CONSTRAINT PUBLIC.CONSTRAINT_8 PRIMARY KEY(USERNAME, TIME); \n"+
				"-- 1 +/- SELECT COUNT(*) FROM PUBLIC.RECORD; \n"+
				"GRANT SELECT, INSERT, UPDATE ON PUBLIC.RECORD TO INSERTER; ";
		
		try {
			Statement createStatement = instance.connection.createStatement();
			createStatement.execute(dbString);
			
			
			System.out.println("db created");
		} catch (SQLException e) {
		}
		
	}
	
	
	
	
}
