package nba.playActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nba.Play;
import nba.PlayAction;
import nba.Player;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.timeStamps.PlayerTimeStamp;

public class Rebound extends PlayAction{

	private int offensiveRebounds, defensiveRebounds;
	private boolean defensiveRebound;
	private int reboundID;
	
	public Rebound()
	{
		this.offensiveRebounds = 0;
		this.defensiveRebounds = 0;
		defensiveRebound = true;
	}
	
	public Rebound(int OffRebound, int DefRebound)
	{
		this.offensiveRebounds = OffRebound;
		this.defensiveRebounds = DefRebound;
		defensiveRebound = true;
	}
	
	@Override
	public boolean TerminatesPossession()
	{
		//if show was missed possession is over, otherwise
		//need to check if foul occurred after made shot
		return true;
	}
	
	@Override
	public String debug()
	{
		return "Rebound";
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
		    
		    int timeStampTeamID;
		    
		    if (home.equals(timeStamp.GetTeam()))
		    	timeStampTeamID = home.GetTeamID();
		    else
		    	timeStampTeamID = away.GetTeamID();
		  
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`rebound` (`defensive_rebound`) VALUES (?);");
		    stmt.setBoolean(1, possession.GetDefenseID() == timeStampTeamID);
		    stmt.executeUpdate();
		    
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.reboundID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    for (Play p : possession.getPossessionPlays())
		    {
		    	if (p.GetPlayAction() instanceof Shot)
		    	{
		    		Shot s = (Shot)p.GetPlayAction();
		    		if (!s.ShotMade())
		    		{
		    			stmt = conn.prepareStatement("INSERT INTO `nba`.`rebound_shot` (`rebound_id`,`shot_id`" +
								") VALUES (?,?);");
		    			stmt.setInt(1, this.reboundID);
		    		    stmt.setInt(2, s.getShotID());
		    		    stmt.executeUpdate();
		    		}
		    	}
		    	
		    	if (p.GetPlayAction() instanceof FreeThrow)
		    	{
		    		FreeThrow ft = (FreeThrow)p.GetPlayAction();
		    		if (!ft.made() && timeStamp instanceof PlayerTimeStamp)
		    		{
		    			stmt = conn.prepareStatement("INSERT INTO `nba`.`rebound_free_throw` (`rebound_id`,`free_throw_id`" +
								") VALUES (?,?);");
		    			stmt.setInt(1, this.reboundID);
		    		    stmt.setInt(2, ft.freeThrowID());
		    		    stmt.executeUpdate();
		    		}
		    	}
		    }
		    
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
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`rebound_player` (`rebound_id`,`player_id`" +
						") VALUES (?,?);");
		    	stmt.setInt(1, this.reboundID);
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
