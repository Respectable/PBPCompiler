package nba.timeStamps;

import nba.Team;
import nba.TimeStamp;
import nba.playActions.Foul;
import nba.playActions.foul.DoublePersonalFoul;

public class DPTimeStamp extends TimeStamp{

	private Team team;
	private DoublePersonalFoul doublePersonal;
	
	public DPTimeStamp(String time, Team team, Foul doublePersonal)
	{
		super(time);
		this.team = team;
		this.doublePersonal = (DoublePersonalFoul)doublePersonal;
	}
}
