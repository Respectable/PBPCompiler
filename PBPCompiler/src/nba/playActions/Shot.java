package nba.playActions;

import nba.PlayAction;
import nba.playActions.shot.ShotOutcome;
import nba.playActions.shot.ShotType;

public class Shot extends PlayAction{

	private ShotType shotType;
	private ShotOutcome shotOutcome;
	
	public Shot(ShotType shotType, ShotOutcome shotOutcome)
	{
		this.shotType = shotType;
		this.shotOutcome = shotOutcome;
	}
}
