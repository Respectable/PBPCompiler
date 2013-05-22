package nba.playActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import visitors.Visitor;

import nba.PlayAction;
import nba.Player;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.playActions.foul.FoulType;
import nba.timeStamps.PlayerTimeStamp;

public class Foul extends PlayAction{

	protected FoulType foulType;
	protected int foulID;
	
	public Foul()
	{
		this.foulType = null;
	}
	
	public Foul(FoulType foulType)
	{
		this.foulType = foulType;
	}
	
	@Override
	public String debug()
	{
		return "Foul";
	}
	
	public FoulType getFoulType()
	{
		return foulType;
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
		    
		  
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`foul` (`foul_type`,`team_foul`) VALUES (?,?);");
		    stmt.setString(1, this.foulType.getFoulType());
		    stmt.setBoolean(2, this.foulType.teamFoul());
		    stmt.executeUpdate();
		    
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.foulID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`foul_possession` (`foul_id`,`possession_id`" +
		    								",`time_of_foul`) VALUES (?,?,?);");
		    stmt.setInt(1, this.foulID);
		    stmt.setInt(2, possession.GetPossessionID());
		    stmt.setDouble(3, timeStamp.GetTimeDouble());
		    stmt.executeUpdate();
		    
//		    if (timeStamp instanceof PlayerTimeStamp)
//		    {
//		    	PlayerTimeStamp ts = (PlayerTimeStamp)timeStamp;
//		    	Player player;
//		    	if (ts.GetTeam().equals(away))
//		    	{
//		    		player = away.GetPlayer(ts.GetPlayer());
//		    	}
//		    	else
//		    	{
//		    		player = home.GetPlayer(ts.GetPlayer());
//		    	}
//		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`technical_foul_player` (`technical_foul_id`,`player_id`" +
//						") VALUES (?,?);");
//		    	stmt.setInt(1, this.technicalID);
//		    	stmt.setInt(2, player.getID());
//		    	stmt.executeUpdate();
//		    }
		    
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
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
