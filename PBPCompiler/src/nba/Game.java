package nba;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Game {

	private ArrayList<Period> periods;
	private Team homeTeam;
	private Team awayTeam;
	private Date gameDate;
	private Time tipOffTime;
	private int gameID;
	
	public Game(Team home, Team away, Date gameDate, Time tipOffTime)
	{
		periods = new ArrayList<Period>();
		this.homeTeam = home;
		this.awayTeam = away;
		this.gameDate = gameDate;
		this.tipOffTime = tipOffTime;
	}
	
	public Game(ArrayList<Period> periods)
	{
		this.periods = periods;
	}
}
