package it.mat.unical.Helion_Prime.SavesManager;


import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class SaveManagerImpl implements SaveManager {

	
	private H2DbManager savesManager = H2DbManager.getInstance();
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
		
		savesManager.H2engage();
		
		connection = savesManager.getConnection();
		
		String statement = "INSERT INTO Record(Username,Time,Gun1_Bullets,Gun2_Bullets,Gun3_Bullets,Gun4_Bullets,LastLevel,Score) values(?,?,?,?,?,?,?,?)";
		
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
			 
			 
			 
			 if ( preparedStatement.execute() )
				 return true;
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			savesManager.H2disengange();
		}
		
		return false;
	}

	@Override
	public void loadGame(String username,Timestamp timestamp,PlayerState playerState) {
		
		savesManager.H2engage();
		
		connection = savesManager.getConnection();
		
		String statement = "SELECT * FROM RECORD WHERE Username = ? AND TIME = ? LIMIT 1";
		
		PreparedStatement preparedStatement = null;
		try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			savesManager.H2disengange();
		}
		
	}

	@Override
	public boolean deleteSavedGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Timestamp> fetchSaves(String username) {
		savesManager.H2engage();
		
		connection = savesManager.getConnection();
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			savesManager.H2disengange();
		}
		
		return null;
		
		
	}

	@Override
	public boolean overrideSave(PlayerState playerState) {

		savesManager.H2engage();
		
		connection = savesManager.getConnection();
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			savesManager.H2disengange();
		}
		
		return false;
	
	}


}
