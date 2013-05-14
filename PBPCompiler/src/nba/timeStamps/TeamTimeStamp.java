package nba.timeStamps;

import nba.Possession;
import nba.Team;
import nba.TimeStamp;

public class TeamTimeStamp extends TimeStamp{

	private Team team;
	
	public TeamTimeStamp(String time, Team team)
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
