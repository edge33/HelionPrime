package it.mat.unical.Helion_Prime.ScoreCharts;

import it.mat.unical.Helion_Prime.Logic.CommonProperties;
import it.mat.unical.Helion_Prime.SavesManager.AbstractDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class RemoteDatabaseManager extends AbstractDbManager implements LoginInterface, ScoresUploader  {

	private static RemoteDatabaseManager instance;
	
	private boolean loggedIn;
	
	public static RemoteDatabaseManager getInstance() {
		if ( instance == null ) 
			instance = new RemoteDatabaseManager();
		return instance;
	}
	
	private RemoteDatabaseManager() {
		loadDBproperties();
	}
	
	
	@Override
	protected void loadDBproperties() {
		
		driver = CommonProperties.getInstance().getremoteDriver();
		dburl = CommonProperties.getInstance().getRemoteDb();
		user =  CommonProperties.getInstance().getRemoteUser();
		pwd = CommonProperties.getInstance().getRemotePwd();
		
		loggedIn = false;
		
		super.loadDBproperties();
	}

	@Override
	public boolean doLogin(String username, String password) {
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			String selectStatement = "SELECT * FROM User WHERE Username =  ? AND Password = ? LIMIT 1";
			
			preparedStatement = connection.prepareStatement(selectStatement);
			
			preparedStatement.setString(1,username);
			preparedStatement.setString(2,password);
			
			resultSet = preparedStatement.executeQuery();
			
			if ( resultSet.next() ) {
				loggedIn = true;
				return true;
			} 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			closeStatement(preparedStatement);
			closeResultSet(resultSet);
		}

	 return false;
	}

	@Override
	public boolean isLoggedIn() {
		return loggedIn;
	}

	@Override
	public boolean uploadScore(String username,int score,String level) {
		
		if ( !loggedIn ) {
			return false;
		}
		Connection connection = getConnection();
		
		String statement = new String( " INSERT INTO Scores(user,score,data,levelname) values(?,?,?,?) " );
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(statement);
			
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, score);
			preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()) );
			preparedStatement.setString(4, level);
			
			if ( preparedStatement.executeUpdate() != 0 ) {
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			closeStatement(preparedStatement);
		}
		
		
		return false;
	}
	
}
