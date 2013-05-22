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
import nba.timeStamps.PlayerTimeStamp;

public class Technical extends PlayAction {

	protected int technicalID;
	protected String technicalType;
	
	public Technical()
	{
		this.technicalID = -1;
		this.technicalType = "";
	}
	
	@Override
	public String debug()
	{
		return "Technical";
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
		    
		    if (technicalType.equals(""))
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`technical_foul` VALUES (DEFAULT);");
		    	stmt.executeUpdate();
		    }
		    else
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`technical_foul` (`technical_type`) VALUES (?);");
		    	stmt.setString(1, this.technicalType);
		    	stmt.executeUpdate();
		    }
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.technicalID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`technical_foul_possession` (`technical_foul_id`,`possession_id`" +
		    								",`time_of_technical_foul`) VALUES (?,?,?);");
		    stmt.setInt(1, this.technicalID);
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
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`technical_foul_player` (`technical_foul_id`,`player_id`" +
						") VALUES (?,?);");
		    	stmt.setInt(1, this.technicalID);
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
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	
}
