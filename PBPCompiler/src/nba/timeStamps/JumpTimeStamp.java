package nba.timeStamps;

import nba.Team;
import nba.TimeStamp;

public class JumpTimeStamp extends TimeStamp{

	private Team team;
	
	public JumpTimeStamp(String time, Team team)
	{
		super(time);
		this.team = team;
	}
	
	@Override
	public Team GetTeam()
	{
		return team;
	}
	
	@Override
	public String debug()
	{
		return time + " " + team.GetTeamName();
	}
}
