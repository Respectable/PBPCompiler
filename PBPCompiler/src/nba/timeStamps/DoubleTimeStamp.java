package nba.timeStamps;

import nba.Team;
import nba.TimeStamp;

public class DoubleTimeStamp extends TimeStamp{

	private Team team;
	
	public DoubleTimeStamp(String time, Team team)
	{
		super(time);
		this.team = team;
	}
}
