package nba.timeStamps;

import visitors.Visitor;
import nba.Player;
import nba.Team;
import nba.TimeStamp;

public class PlayerTimeStamp extends TimeStamp{

	private Team team;
	private Player player;
	
	public PlayerTimeStamp(String time, Team team, Player player)
	{
		super(time);
		this.team = team;
		this.player = player;
	}
	
	@Override
	public Team GetTeam()
	{
		return team;
	}
	
	public Player GetPlayer()
	{
		return player;
	}
	
	@Override
	public String debug()
	{
		return time + " " + team.GetTeamName();
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
