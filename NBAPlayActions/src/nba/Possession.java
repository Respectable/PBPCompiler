package nba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import visitors.Visitable;
import visitors.Visitor;

public class Possession implements Visitable {

	private ArrayList<Play> possessionPlays;
	private ArrayList<Player> homePlayers;
	private ArrayList<Player> awayPlayers;
	private int possessionID;
	private int offenseTeamID, defenseTeamID;
	
	
	public Possession()
	{
		this.possessionPlays = new ArrayList<Play>();
		this.homePlayers = new ArrayList<Player>();
		this.awayPlayers = new ArrayList<Player>();
		this.possessionID = -1;
		this.offenseTeamID = -1;
		this.defenseTeamID = -1;
	}
	
	public void AddPlay(Play play)
	{
		possessionPlays.add(play);
	}
	
	public void SetTeamRoles(int offTeamID, int defTeamID)
	{
		this.offenseTeamID = offTeamID;
		this.defenseTeamID = defTeamID;
	}
	
	public boolean TeamsSet()
	{
		return this.offenseTeamID != -1 &&
				this.defenseTeamID != -1;
	}
	
	public int GetOffenseID()
	{
		return this.offenseTeamID;
	}
	
	public int GetDefenseID()
	{
		return this.defenseTeamID;
	}
	
	public void debugPossession(String indent, Team homeTeam, Team awayTeam)
	{
		String offense, defense;
		
		if (this.offenseTeamID == homeTeam.GetTeamID())
		{
			offense = homeTeam.GetTeamName();
			defense = awayTeam.GetTeamName();
		}
		else
		{
			offense = awayTeam.GetTeamName();
			defense = homeTeam.GetTeamName();
		}
		
		System.out.println(indent + "Possession " + possessionID + " (Offense: " + 
		offense + " Defense: " + defense + ")");
		indent = indent + "\t";
		
		for (Play p : this.possessionPlays)
		{
			p.debugPlay(indent);
		}
	}
	
	public void compile(int periodID, Team homeTeam, Team awayTeam)
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
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`possession` VALUES (DEFAULT);");
		    stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.possessionID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_possessions` (`possession_id`,`period_id`) VALUES (?,?);");
		    stmt.setInt(1, this.possessionID);
		    stmt.setInt(2, periodID);
		    stmt.executeUpdate();
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`team_possessions` (`possession_id`,`offensive_team_id`,`defensive_team_id`) VALUES (?,?,?);");
		    stmt.setInt(1, this.possessionID);
		    stmt.setInt(2, this.offenseTeamID);
		    stmt.setInt(3, this.defenseTeamID);
		    stmt.executeUpdate();
		    
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
		
		for (Play p : possessionPlays)
		{
			p.compile(this, homeTeam, awayTeam);
		}
	}
	
	public int GetPossessionID()
	{
		return this.possessionID;
	}
	
	public ArrayList<Play> getPossessionPlays()
	{
		return this.possessionPlays;
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	
}
