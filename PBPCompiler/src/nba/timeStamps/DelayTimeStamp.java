package nba.timeStamps;


import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.playActions.Technical;
import nba.playActions.technical.DelayTechnical;

public class DelayTimeStamp extends TimeStamp{

	private Team team;
	private DelayTechnical delay;
	
	public DelayTimeStamp(String time, Team team, Technical delay)
	{
		super(time);
		this.team = team;
		this.delay = (DelayTechnical)delay;
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
	
	@Override
	public void compile(Possession possession, Team home, Team away)
	{
		delay.compile(possession, home, away, this);
	}
}
