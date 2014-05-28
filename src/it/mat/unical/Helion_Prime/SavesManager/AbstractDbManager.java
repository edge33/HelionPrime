package it.mat.unical.Helion_Prime.SavesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDbManager {

	protected static String user;
	protected static String pwd;
	protected static String driver;
	protected static String dburl; 

	protected AbstractDbManager() {
	}
	
	protected  void loadDBproperties() {
	     loadDriver();
	}


	private static void loadDriver() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load the driver class", e);
		}		
	}


	protected Connection getConnection() {
        try {
			return DriverManager.getConnection(dburl,user,pwd);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create connection!",e);
		}
	}
	
	protected void closeConnection(final Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
	
	protected Statement getStatement(final Connection conn)
    {
        try
        {
            return conn.createStatement();
        }
        catch (final SQLException e)
        {
            throw new RuntimeException("Cannot create the statement", e);
        }
    }
	
	protected void closeStatement(final Statement statement)
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            }
            catch (final SQLException e)
            {
            }
        }
    }
	
	protected void closeResultSet(final ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (final SQLException e)
            {
            }
        }
    }
	
}

