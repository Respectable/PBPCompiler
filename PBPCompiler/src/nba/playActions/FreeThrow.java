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
import nba.playActions.freeThrow.FreeThrowPredicate;
import nba.timeStamps.PlayerTimeStamp;

public class FreeThrow extends PlayAction{
	
	private String freeThrowType;
	private FreeThrowPredicate outcome;
	private int freeThrowID;
	
	public FreeThrow(String freeThrowType, FreeThrowPredicate outcome)
	{
		this.freeThrowType = freeThrowType;
		this.outcome = outcome;
	}
	
	public boolean TerminatingFreeThrowType()
	{
		return !this.freeThrowType.equals("Technical") && 
				!this.freeThrowType.equals("Clear Path");
	}
	
	@Override
	public boolean TerminatesPossession()
	{
		return outcome.LastFreeThrow() && 
				this.TerminatingFreeThrowType() &&
				outcome.made();
	}
	
	public boolean lastFreeThrow()
	{
		return outcome.LastFreeThrow();
	}
	
	public boolean made()
	{
		return outcome.made();
	}
	
	public int freeThrowID()
	{
		return freeThrowID;
	}
	
	@Override
	public boolean IdentifiesOffense()
	{
		return this.freeThrowType.equals("") || 
				this.freeThrowType.equals("Clear Path");
	}
	
	@Override
	public String debug()
	{
		return "Free Throw";
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
		    
		  
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`free_throw` (`made`,`current_number`,`total_number`) VALUES (?,?,?);");
		    stmt.setBoolean(1, this.outcome.made());
		    stmt.setInt(2, this.outcome.currentShotNumber());
		    stmt.setInt(2, this.outcome.outOfNumber());
		    stmt.executeUpdate();
		    
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.freeThrowID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`free_throw_possession` (`free_throw_id`,`possession_id`" +
		    								",`time_of_foul`) VALUES (?,?,?);");
		    stmt.setInt(1, this.freeThrowID);
		    stmt.setInt(2, possession.GetPossessionID());
		    stmt.setDouble(3, timeStamp.GetTimeDouble());
		    stmt.executeUpdate();
		    
		    if (timeStamp instanceof PlayerTimeStamp)
		    {
		    	PlayerTimeStamp ts = (PlayerTimeStamp)timeStamp;
		    	Player player;
		    	if (ts.GetTeam().equals(away))
		    	{
		    		player = away.GetPlayer(ts.GetPlayer());
		    	}
		    	else
		    	{
		    		player = home.GetPlayer(ts.GetPlayer());
		    	}
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`free_throw_player` (`free_throw_id`,`player_id`" +
						") VALUES (?,?);");
		    	stmt.setInt(1, this.freeThrowID);
		    	stmt.setInt(2, player.getID());
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
