package nba;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Game {

	private ArrayList<Period> periods;
	private Team homeTeam;
	private Team awayTeam;
	private Date gameDate;
	//private Time tipOffTime;
	private int gameID;
	private int seasonID;
	
	public Game(Team home, Team away, Date gameDate)
	{
		periods = new ArrayList<Period>();
		this.homeTeam = home;
		this.awayTeam = away;
		this.gameDate = gameDate;
		//this.tipOffTime = tipOffTime;
	}
	
	public Game(ArrayList<Period> periods)
	{
		this.periods = periods;
	}
	
	public ArrayList<Period> GetPeriods()
	{
		return periods;
	}
	
	public void setGameAttributes(String fileData)
	{
		String gameData = fileData.substring(fileData.lastIndexOf('/') + 1);
		try {
			gameDate = new SimpleDateFormat("yyyyMMdd").parse(gameData.substring(0,9));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		java.sql.Date sqlDate = ConvertDate(gameDate);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/nba",
                        "root","******");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM season WHERE " +
		                            "start_date <= '" + sqlDate +
		                            "' AND end_date >= '" + sqlDate + "'");
		    rs.next();
		    this.seasonID = rs.getInt("season_id");
		    
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
		
		this.awayTeam = new Team(gameData.substring(8, 11), gameDate, seasonID);
		this.homeTeam = new Team(gameData.substring(11, 14), gameDate, seasonID);
		
	}
	
	public void CompileGame(String fileName)
	{
		 
		 try
		 {
			 Compile();
			 AssignPossessions();
			 debugPossessions();
			 
			 //compile sql statements
			 
			 for (Period p : periods)
			 {
				 p.Compile(this.gameID, this.homeTeam, this.awayTeam);
			 }
			 
			 assignUnits();
			 
		 }
		 catch (Exception e)
		 {
			 
		 }
	}
	
	public void Compile()
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
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game` (`date_played`) VALUES (?);");
		    stmt.setDate(1, ConvertDate(gameDate));
		    stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.gameID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_season` (`game_id`,`season_id`) VALUES (?,?);");
		    stmt.setInt(1, this.gameID);
		    stmt.setInt(2, this.seasonID);
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
	}
	
	private void AssignPossessions()
	{
		for (Period p : periods)
		{
			p.AssignPossessions(this.gameID, this.homeTeam, this.awayTeam);
		}
	}
	
	private void assignUnits()
	{
		
	}
	
	private java.sql.Date ConvertDate(Date date)
	{
		return new java.sql.Date(gameDate.getTime());
	}
	
	public void debugPossessions()
	{
		String indent="\t";
		
		System.out.println("Game - " + this.awayTeam.GetTeamName() +
				" vs " + this.homeTeam.GetTeamName() + this.gameDate);
		for (Period p : periods)
		{
			p.debugPossessions(indent, this.homeTeam, this.awayTeam);
		}
	}
}
