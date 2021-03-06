package nba.timeStamps;

import visitors.Visitor;
import nba.InstantReplay;
import nba.Possession;
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
	
	public InstantReplay getInstantReplay()
	{
		return instantReplay;
	}
	
	@Override
	public Team GetTeam()
	{
		return team;
	}
	
	@Override
	public String debug()
	{
		return time + " " + team.GetTeamName() + " Instant Replay";
	}
	
	@Override
	public void compile(Possession possession, Team home, Team away)
	{
		//instant replay needs to be added
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
