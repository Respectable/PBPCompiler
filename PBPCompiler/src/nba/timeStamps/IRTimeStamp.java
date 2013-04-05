package nba.timeStamps;

import nba.InstantReplay;
import nba.Team;
import nba.TimeStamp;

public class IRTimeStamp extends TimeStamp{

	private Team team;
	private InstantReplay instantReplay;
	
	public IRTimeStamp(String time, Team team, InstantReplay instantReplay)
	{
		super(time);
		this.team = team;
		this.instantReplay = instantReplay;
	}
}
