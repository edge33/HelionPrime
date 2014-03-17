package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	public boolean saveGame() {
		
		savesManager.H2engage();
		
		connection = savesManager.getConnection();
		
		String statement = "INSERT INTO Record(Username,Time,Gun1_Bullets,Gun2_Bullets,Gun3_Bullets,Gun4_Bullets,LastLevel,Score,Credits) values(?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		try {
			 preparedStatement = connection.prepareStatement(statement);
	
			 //per ora sono hardCoded, li cambieremo a tempo debito
			 preparedStatement.setString(1, "edge33");
			 preparedStatement.setTimestamp(2, new Timestamp( new Date().getTime() ));
			 preparedStatement.setInt(3, 100);
			 preparedStatement.setInt(4, 100);
			 preparedStatement.setInt(5, 100);
			 preparedStatement.setInt(6, 100);
			 preparedStatement.setInt(7, 1);
			 preparedStatement.setInt(8, 1000);
			 preparedStatement.setInt(9, 50000);
			 
			 
			 
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
	public boolean loadGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSavedGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fetchSaves() {
		// TODO Auto-generated method stub
		
	}

}
