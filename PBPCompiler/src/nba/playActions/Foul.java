package nba.playActions;

import nba.PlayAction;
import nba.playActions.foul.FoulType;

public class Foul extends PlayAction{

	protected FoulType foulType;
	
	public Foul()
	{
		this.foulType = null;
	}
	
	public Foul(FoulType foulType)
	{
		this.foulType = foulType;
	}
}
