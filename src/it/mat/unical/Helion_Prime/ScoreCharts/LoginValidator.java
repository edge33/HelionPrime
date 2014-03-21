package it.mat.unical.Helion_Prime.ScoreCharts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginValidator implements LoginInterface{

	private boolean loggedIn;
	
	private static LoginValidator instance;
	
	public static LoginValidator getInstance() {
		if ( instance == null ) 
			instance = new LoginValidator();
		return instance;
	}
	
	private LoginValidator() {
		this.loggedIn = false;
	}
	
	@Override
	public boolean doLogin(String username, String password) {
	
			DatabaseManager db = DatabaseManager.getInstance();
			
			db.DbConnect();
			
			 try {
					
					String selectStatement = "SELECT * FROM User WHERE Username =  ? AND Password = ? LIMIT 1";
					
					PreparedStatement preparedStatement = db.getConnection().prepareStatement(selectStatement);
					
					preparedStatement.setString(1,username);
					preparedStatement.setString(2,password);
					
					ResultSet resultSet = preparedStatement.executeQuery();
					
					if ( resultSet.next() ) {
						System.err.println("User Found");
						loggedIn = true;
					} else {
						System.err.println("User NOT Found!!!");
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					db.DbClose();
				}
		
			 return false;
	}

	@Override
	public boolean isLoggedIn() {
		return loggedIn;
	}

}
