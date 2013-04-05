package nba.timeStamps;

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
}
