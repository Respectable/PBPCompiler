package nba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import visitors.Visitable;
import visitors.Visitor;

public class Period implements Visitable {

	private PeriodIdentification periodIdentification;
	private ArrayList<Play> plays;
	private ArrayList<Possession> possessions;
	private int periodID;
	
	public Period()
	{
		
	}
	
	public Period(PeriodIdentification periodIdentification, ArrayList<Play> plays)
	{
		this.periodIdentification = periodIdentification;
		this.plays = plays;
		this.possessions = new ArrayList<Possession>();
		this.periodID = -1;
	}
	
	public void debugPossessions(String indent, Team homeTeam, Team awayTeam)
	{
		System.out.println(indent + this.periodIdentification.GetPeriodNumber() + " " 
				+ this.periodIdentification.GetPeriodType());
		indent = indent+ "\t";
		
		for (Possession p : this.possessions)
		{
			p.debugPossession(indent, homeTeam, awayTeam);
		}
	}
	
	public ArrayList<Play> getPlays()
	{
		return plays;
	}
	
	public void addPossession(Possession possession)
	{
		possessions.add(possession);
	}
	
	public int getPeriodInt()
	{
		return this.periodIdentification.GetPeriodInt();
	}
	
	public boolean isInRegulation()
	{
		return this.periodIdentification.PeriodIsRegulation();
	}
	
	public void Compile(int gameID, Team homeTeam, Team awayTeam)
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
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`period` (`period_identifier`,`regulation`) VALUES (?,?);");
		    stmt.setInt(1, this.periodIdentification.GetPeriodInt());
		    stmt.setBoolean(2, this.periodIdentification.PeriodIsRegulation());
		    stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.periodID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_periods` (`game_id`,`period_id`) VALUES (?,?);");
		    stmt.setInt(1, gameID);
		    stmt.setInt(2, this.periodID);
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
		
		for (Possession p : possessions)
		{
			p.compile(this.periodID, homeTeam, awayTeam);
		}
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
