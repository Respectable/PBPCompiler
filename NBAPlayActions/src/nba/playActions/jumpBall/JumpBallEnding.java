package nba.playActions.jumpBall;

import nba.Player;

public class JumpBallEnding {

	private Player player;
	
	public JumpBallEnding()
	{
		this.player = null;
	}
	
	public JumpBallEnding(Player player)
	{
		this.player = player;
	}
	
	public boolean IdentifiesOffense()
	{
		if (player != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Player GetPlayer()
	{
		return player;
	}
}
