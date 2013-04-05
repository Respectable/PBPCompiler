package nba.timeStamps;

import nba.Team;
import nba.TimeStamp;

public class TeamTimeStamp extends TimeStamp{

	private Team team;
	
	public TeamTimeStamp(String time, Team team)
	{
		super(time);
		this.team = team;
	}
}
