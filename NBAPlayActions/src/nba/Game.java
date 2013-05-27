package nba;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import visitors.Visitable;
import visitors.Visitor;

public class Game implements Visitable {

	private ArrayList<Period> periods;
	private Team homeTeam;
	private Team awayTeam;
	private Date gameDate;
	//private Time tipOffTime;
	private int gameID;
	private int seasonID;
	
	
	public Game(ArrayList<Period> periods)
	{
		this.periods = periods;
	}
	
	public ArrayList<Period> GetPeriods()
	{
		return periods;
	}
	
	public void setHomeTeam(Team homeTeam)
	{
		this.homeTeam = homeTeam;
	}
	
	public void setAwayTeam(Team awayTeam)
	{
		this.awayTeam = awayTeam;
	}
	
	public void setGameDate(Date gameDate)
	{
		this.gameDate = gameDate;
	}
	
	public Team getHomeTeam()
	{
		return this.homeTeam;
	}
	
	public Team getAwayTeam()
	{
		return this.awayTeam;
	}
	
	public Date getGameDate()
	{
		return this.gameDate;
	}
	
	public ArrayList<Period> getPeriods()
	{
		return periods;
	}
	
	public void CompileGame(String fileName)
	{
		 
		 try
		 {
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

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	public boolean homeTeamHasDuplicatePlayers()
	{
		return homeTeam.HasDuplicatePlayers();
	}
	
	public boolean awayTeamHasDuplicatePlayers()
	{
		return awayTeam.HasDuplicatePlayers();
	}
}
