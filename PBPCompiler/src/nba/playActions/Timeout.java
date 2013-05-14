package nba.playActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nba.PlayAction;
import nba.Player;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.timeStamps.PlayerTimeStamp;

public class Timeout extends PlayAction{

	private String timeoutType;
	private int timeoutID;
	
	public Timeout(String timeoutType)
	{
		this.timeoutType = timeoutType;
	}
	
	@Override
	public String debug()
	{
		return "Timeout";
	}
	
	@Override
	public void compile(Possession possession, Team home, Team away, TimeStamp timeStamp)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/nba",
		                                   "root","*******");
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`timeout` (`timeout_type`) VALUES (?);");
		    stmt.setString(1, this.timeoutType);
		    stmt.executeUpdate();
		    
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.timeoutID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`timeout_possession` (`timeout_id`,`possession_id`" +
		    								",`time_of_timeout`) VALUES (?,?,?);");
		    stmt.setInt(1, this.timeoutID);
		    stmt.setInt(2, possession.GetPossessionID());
		    stmt.setDouble(3, timeStamp.GetTimeDouble());
		    stmt.executeUpdate();
		    
		    if (timeStamp.GetTeam().equals(home))
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`timeout_possession` (`timeout_id`,`possession_id`" +
						",`time_of_timeout`) VALUES (?,?,?);");
		    	stmt.setInt(1, this.timeoutID);
		    	stmt.setInt(2, possession.GetPossessionID());
		    	stmt.setDouble(3, timeStamp.GetTimeDouble());
		    	stmt.executeUpdate();
		    }
		    else if (timeStamp.GetTeam().equals(away))
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`timeout_possession` (`timeout_id`,`possession_id`" +
						",`time_of_timeout`) VALUES (?,?,?);");
		    	stmt.setInt(1, this.timeoutID);
		    	stmt.setInt(2, possession.GetPossessionID());
		    	stmt.setDouble(3, timeStamp.GetTimeDouble());
		    	stmt.executeUpdate();
		    }
		    
		} 
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally
		{
			if (rs != null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null)
			{
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
