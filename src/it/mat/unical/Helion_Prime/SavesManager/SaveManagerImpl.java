package it.mat.unical.Helion_Prime.SavesManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SaveManagerImpl implements SaveManager {

	
	private H2DbManager database = H2DbManager.getInstance();
	private Connection connection;
	
	private static SaveManagerImpl instance;
	
	public static SaveManagerImpl getInstance() {
		if ( instance == null )
			instance = new SaveManagerImpl();
		return instance;
	}
	
	private SaveManagerImpl() {
	}
	
	@Override
	public boolean saveNewGame(PlayerState playerState) {
		
		database.H2engage();
		connection = database.getConnection();
		
		String statement = "INSERT INTO Record(Username,Time,Gun1_Bullets,Gun2_Bullets,Gun3_Bullets,Gun4_Bullets,LastLevel,Score) values(?,?,?,?,?,?,?,?)";
		
		Timestamp newTimeStamp = new Timestamp( new java.util.Date().getTime());
		playerState.setTimestamp(newTimeStamp);
		
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = connection.prepareStatement(statement);
	
			 preparedStatement.setString(1, playerState.getUsername());
			 preparedStatement.setTimestamp(2, playerState.getTimeStamp());
			 preparedStatement.setInt(3, playerState.getGunBullets1());
			 preparedStatement.setInt(4, playerState.getGunBullets2());
			 preparedStatement.setInt(5, playerState.getGunBullets3());
			 preparedStatement.setInt(6, playerState.getGunBullets3());
			 preparedStatement.setInt(7, playerState.getLastLevelCleared());
			 preparedStatement.setInt(8, playerState.getScore());
			 
			 
			 
			 if ( preparedStatement.executeUpdate() != 0 )
				 return true;
			 
			 
		} catch (SQLException e) {
			e.printStackTrace();
			testException(e);
			return false;
		} finally {
			database.H2disengange();
		}
		
		return false;
	}

	@Override
	public void loadGame(String username,Timestamp timestamp,PlayerState playerState) {
		
		database.H2engage();
		connection = database.getConnection();
		
		String statement = "SELECT * FROM RECORD WHERE Username = ? AND TIME = ? LIMIT 1";
		
		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(statement);
		 
		 preparedStatement.setString(1, username);
		 preparedStatement.setTimestamp(2, timestamp);
		 
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 rs.first();
		 
		 playerState.setGunBullets1(rs.getInt("GUN1_BULLETS"));
		 playerState.setGunBullets2(rs.getInt("GUN2_BULLETS"));
		 playerState.setGunBullets3(rs.getInt("GUN3_BULLETS"));
		 playerState.setGunBullets4(rs.getInt("GUN4_BULLETS"));
		 playerState.setLastLevelCleared(rs.getInt("LastLevel"));
		 playerState.setScore(rs.getInt("Score"));
			 
		} catch (SQLException e) {
			testException(e);
		} finally {
			database.H2disengange();
		}
	}

	@Override
	public boolean deleteSavedGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Timestamp> fetchSaves(String username) {
		
		database.H2engage();
		connection = database.getConnection();
		
		String statement = "SELECT TIME FROM Record WHERE Username = ?";
		
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = connection.prepareStatement(statement);
	
			 preparedStatement.setString(1, username);
			 
			 ResultSet rs = preparedStatement.executeQuery();
			 
	
			 ArrayList<Timestamp> profiles = new ArrayList<>();
			 
				while (rs.next()) {
	 
					Timestamp timeStamp = rs.getTimestamp("TIME");
					profiles.add(timeStamp);
	 
				}
				
				return profiles;
			 
		} catch (SQLException e) {
			testException(e);
		} finally {
			database.H2disengange();
		}
		return null;
		
	}

	@Override
	public boolean overrideSave(PlayerState playerState) {

		
		database.H2engage();
		connection = database.getConnection();
		
		String statement = "UPDATE  Record SET Username = ? ,Time = ? ,Gun1_Bullets = ? ,Gun2_Bullets = ? , Gun3_Bullets = ? , Gun4_Bullets = ? , LastLevel = ? , Score = ? WHERE Username = ? AND Time = ? ";
		
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = connection.prepareStatement(statement);
	
			 Timestamp newTimeStamp = new Timestamp( new java.util.Date().getTime());
			 
			 preparedStatement.setString(1, playerState.getUsername());
			 preparedStatement.setTimestamp(2, newTimeStamp);
			 preparedStatement.setInt(3, playerState.getGunBullets1());
			 preparedStatement.setInt(4, playerState.getGunBullets2());
			 preparedStatement.setInt(5, playerState.getGunBullets3());
			 preparedStatement.setInt(6, playerState.getGunBullets3());
			 preparedStatement.setInt(7, playerState.getLastLevelCleared());
			 preparedStatement.setInt(8, playerState.getScore());
			 
			 preparedStatement.setString(9, playerState.getUsername());
			 preparedStatement.setTimestamp(10, playerState.getTimeStamp());
			 
			 playerState.setTimestamp(newTimeStamp);
			 
			 
			 if ( preparedStatement.executeUpdate() != 0  )
				 return true;
			 return false;
			 
			 
		} catch (SQLException e) {
			testException(e);
			return false;
		} finally {
			database.H2disengange();
		}
		
		
	}

	private void testException(SQLException e) {
		if ( e.getSQLState() == "42S02" ) {
			System.out.println("creating from scratch");
			createDB();
		}
	}

	private void createDB() {
			
		database.H2engage();
			
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
		} finally {
			database.H2disengange();
		}
		
	}
		


}
