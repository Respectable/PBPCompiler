package nba.timeStamps;


import visitors.Visitor;
import nba.Possession;
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
		timeout.compile(possession, home, away, this);
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
