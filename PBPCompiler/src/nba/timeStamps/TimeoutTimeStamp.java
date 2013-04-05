package nba.timeStamps;

import nba.Team;
import nba.TimeStamp;
import nba.playActions.Timeout;

public class TimeoutTimeStamp extends TimeStamp{

	private Team team;
	private Timeout timeout;
	
	public TimeoutTimeStamp(String time, Team team, Timeout timeout)
	{
		super(time);
		this.team = team;
		this.timeout = timeout;
	}
}
