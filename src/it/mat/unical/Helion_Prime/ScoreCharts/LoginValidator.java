package it.mat.unical.Helion_Prime.ScoreCharts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginValidator implements LoginInterface{

	@Override
	public boolean doLogin(String username, String password) {
	
			DatabaseManager db = DatabaseManager.getInstance();
			
			db.DbConnect();
			
			 try {
					Statement statement = DatabaseManager.getInstance().getConnection().createStatement();
					
					statement.executeQuery("SELECT * FROM User WHERE Username = '" + username + "' AND Password = '" + password + "' LIMIT 1");
					
					ResultSet resultSet = statement.getResultSet();
					
					if ( resultSet.next() ) {
						System.err.println("User Found");
					} 
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					db.DbClose();
				}
		
			 return false;
	}

}
