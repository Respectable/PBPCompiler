package nba.playActions;

import nba.PlayAction;

public class Rebound extends PlayAction{

	private int offensiveRebounds, defensiveRebounds;
	
	public Rebound()
	{
		this.offensiveRebounds = 0;
		this.defensiveRebounds = 0;
	}
	
	public Rebound(int OffRebound, int DefRebound)
	{
		this.offensiveRebounds = OffRebound;
		this.defensiveRebounds = DefRebound;
	}
}
